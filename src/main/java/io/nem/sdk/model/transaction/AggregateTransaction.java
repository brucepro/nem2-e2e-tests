/*
 * Copyright 2018 NEM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.nem.sdk.model.transaction;

import io.nem.catapult.builders.*;
import io.nem.core.crypto.Signer;
import io.nem.core.utils.HexEncoder;
import io.nem.sdk.model.account.Account;
import io.nem.sdk.model.account.PublicAccount;
import io.nem.sdk.model.blockchain.NetworkType;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.Validate;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The aggregate innerTransactions contain multiple innerTransactions that can be initiated by
 * different accounts.
 *
 * @since 1.0
 */
public class AggregateTransaction extends Transaction {
  private final List<Transaction> innerTransactions;
  private final List<AggregateTransactionCosignature> cosignatures;

  public AggregateTransaction(
      NetworkType networkType,
      TransactionType transactionType,
      Short version,
      Deadline deadline,
      BigInteger maxFee,
      List<Transaction> innerTransactions,
      List<AggregateTransactionCosignature> cosignatures,
      String signature,
      PublicAccount signer,
      TransactionInfo transactionInfo) {
    this(
        networkType,
        transactionType,
        version,
        deadline,
        maxFee,
        innerTransactions,
        cosignatures,
        Optional.of(signature),
        Optional.of(signer),
        Optional.of(transactionInfo));
  }

  public AggregateTransaction(
      NetworkType networkType,
      TransactionType transactionType,
      Short version,
      Deadline deadline,
      BigInteger maxFee,
      List<Transaction> innerTransactions,
      List<AggregateTransactionCosignature> cosignatures) {
    this(
        networkType,
        transactionType,
        version,
        deadline,
        maxFee,
        innerTransactions,
        cosignatures,
        Optional.empty(),
        Optional.empty(),
        Optional.empty());
  }

  private AggregateTransaction(
      NetworkType networkType,
      TransactionType transactionType,
      Short version,
      Deadline deadline,
      BigInteger maxFee,
      List<Transaction> innerTransactions,
      List<AggregateTransactionCosignature> cosignatures,
      Optional<String> signature,
      Optional<PublicAccount> signer,
      Optional<TransactionInfo> transactionInfo) {
    super(
        transactionType,
        networkType,
        version,
        deadline,
        maxFee,
        signature,
        signer,
        transactionInfo);
    Validate.notNull(innerTransactions, "InnerTransactions must not be null");
    Validate.notNull(cosignatures, "Cosignatures must not be null");
    this.innerTransactions = innerTransactions;
    this.cosignatures = cosignatures;
  }

  /**
   * Create an aggregate complete transaction object
   *
   * @param deadline Deadline to include the transaction.
   * @param maxFee Max fee defined by the sender.
   * @param innerTransactions List of inner innerTransactions.
   * @param networkType Network type.
   * @return {@link AggregateTransaction}
   */
  public static AggregateTransaction createComplete(
      final Deadline deadline,
      final BigInteger maxFee,
      final List<Transaction> innerTransactions,
      final NetworkType networkType) {
    return new AggregateTransaction(
        networkType,
        TransactionType.AGGREGATE_COMPLETE,
        TransactionVersion.AGGREGATE_COMPLETE.getValue(),
        deadline,
        maxFee,
        innerTransactions,
        new ArrayList<>());
  }

  /**
   * Create an aggregate bonded transaction object
   *
   * @param deadline Deadline to include the transaction.
   * @param maxFee Max fee defined by the sender.
   * @param innerTransactions List of inner innerTransactions.
   * @param networkType Network type.
   * @return {@link AggregateTransaction}
   */
  public static AggregateTransaction createBonded(
      final Deadline deadline,
      final BigInteger maxFee,
      final List<Transaction> innerTransactions,
      final NetworkType networkType) {
    return new AggregateTransaction(
        networkType,
        TransactionType.AGGREGATE_BONDED,
        TransactionVersion.AGGREGATE_BONDED.getValue(),
        deadline,
        maxFee,
        innerTransactions,
        new ArrayList<>());
  }

  /**
   * Returns list of innerTransactions included in the aggregate transaction.
   *
   * @return List of innerTransactions included in the aggregate transaction.
   */
  public List<Transaction> getInnerTransactions() {
    return innerTransactions;
  }

  /**
   * Returns list of transaction cosigners signatures.
   *
   * @return List of transaction cosigners signatures.
   */
  public List<AggregateTransactionCosignature> getCosignatures() {
    return cosignatures;
  }

  /**
   * Serialized the transaction.
   *
   * @return bytes of the transaction.
   */
  @Override
  protected byte[] generateBytes() {
    byte[] transactionsBytes = new byte[0];
    for (Transaction innerTransaction : innerTransactions) {
      final byte[] transactionBytes = innerTransaction.toAggregateTransactionBytes();
      transactionsBytes = ArrayUtils.addAll(transactionsBytes, transactionBytes);
    }
    final ByteBuffer transactionsBuffer = ByteBuffer.wrap(transactionsBytes);

    byte[] cosignaturesBytes = new byte[0];
    for (AggregateTransactionCosignature cosignature : cosignatures) {
      final byte[] signerBytes = cosignature.getSigner().getPublicKey().getBytes();
      final byte[] signatureBytes = HexEncoder.getBytes(cosignature.getSignature());
      final ByteBuffer signerBuffer = ByteBuffer.wrap(signerBytes);
      final ByteBuffer signatureBuffer = ByteBuffer.wrap(signatureBytes);

      final CosignatureBuilder cosignatureBuilder =
          CosignatureBuilder.create(new KeyDto(signerBuffer), new SignatureDto(signatureBuffer));
      cosignaturesBytes = ArrayUtils.addAll(transactionsBytes, cosignatureBuilder.serialize());
    }
    final ByteBuffer cosignaturesBuffer = ByteBuffer.wrap(cosignaturesBytes);

    // Add place holders to the signer and signature until actually signed
    final ByteBuffer signerBuffer = ByteBuffer.allocate(32);
    final ByteBuffer signatureBuffer = ByteBuffer.allocate(64);

    AggregateTransactionBuilder txBuilder =
        AggregateTransactionBuilder.create(
            new SignatureDto(signatureBuffer),
            new KeyDto(signerBuffer),
            getNetworkVersion(),
            EntityTypeDto.rawValueOf(getType().getValue()),
            new AmountDto(getFee().longValue()),
            new TimestampDto(getDeadline().getInstant()),
            transactionsBuffer,
            cosignaturesBuffer);
    return txBuilder.serialize();
  }

  /**
   * Fail if this method is called.
   *
   * @return
   */
  @Override
  protected byte[] generateEmbeddedBytes() {
    throw new IllegalStateException(
        "Aggregate class cannot generate bytes for an embedded transaction.");
  }

  /**
   * Sign transaction with cosignatories creating a new SignedTransaction.
   *
   * @param initiatorAccount Initiator account
   * @param cosignatories The list of accounts that will cosign the transaction
   * @return {@link SignedTransaction}
   */
  public SignedTransaction signTransactionWithCosigners(
      final Account initiatorAccount,
      final List<Account> cosignatories,
      final String generationHash) {
    SignedTransaction signedTransaction = this.signWith(initiatorAccount, generationHash);
    String payload = signedTransaction.getPayload();

    for (Account cosignatory : cosignatories) {
      Signer signer = new Signer(cosignatory.getKeyPair());
      byte[] bytes = Hex.decode(signedTransaction.getHash());
      byte[] signatureBytes = signer.sign(bytes).getBytes();
      payload += cosignatory.getPublicKey() + Hex.toHexString(signatureBytes);
    }

    byte[] payloadBytes = Hex.decode(payload);

    byte[] size = BigInteger.valueOf(payloadBytes.length).toByteArray();
    ArrayUtils.reverse(size);

    System.arraycopy(size, 0, payloadBytes, 0, size.length);

    return new SignedTransaction(
        Hex.toHexString(payloadBytes), signedTransaction.getHash(), getType());
  }

  /**
   * Check if account has signed transaction.
   *
   * @param publicAccount - Signer public account
   * @return boolean
   */
  public boolean signedByAccount(PublicAccount publicAccount) {
    return this.getSigner().get().equals(publicAccount)
        || this.getCosignatures().stream().anyMatch(o -> o.getSigner().equals(publicAccount));
  }
}
