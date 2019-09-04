/*
 * Copyright 2019 NEM
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
import io.nem.sdk.model.account.Address;
import io.nem.sdk.model.account.PublicAccount;
import io.nem.sdk.model.blockchain.NetworkType;
import org.apache.commons.lang.Validate;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountAddressRestrictionModificationTransaction extends Transaction {

  private final AccountRestrictionType restrictionType;
  private final List<AccountRestrictionModification<Address>> modifications;

  /** public constructor */
  public AccountAddressRestrictionModificationTransaction(
      final NetworkType networkType,
      final Short version,
      final Deadline deadline,
      final BigInteger maxFee,
      final AccountRestrictionType restrictionType,
      final List<AccountRestrictionModification<Address>> modifications,
      final String signature,
      final PublicAccount signer,
      final TransactionInfo transactionInfo) {
    this(
        networkType,
        version,
        deadline,
        maxFee,
        restrictionType,
        modifications,
        Optional.of(signature),
        Optional.of(signer),
        Optional.of(transactionInfo));
  }

  /** private constructor */
  private AccountAddressRestrictionModificationTransaction(
      final NetworkType networkType,
      final Short version,
      final Deadline deadline,
      final BigInteger maxFee,
      final AccountRestrictionType restrictionType,
      final List<AccountRestrictionModification<Address>> modifications) {
    this(
        networkType,
        version,
        deadline,
        maxFee,
        restrictionType,
        modifications,
        Optional.empty(),
        Optional.empty(),
        Optional.empty());
  }

  /** private constructor */
  private AccountAddressRestrictionModificationTransaction(
      final NetworkType networkType,
      final Short version,
      final Deadline deadline,
      final BigInteger maxFee,
      final AccountRestrictionType restrictionType,
      final List<AccountRestrictionModification<Address>> modifications,
      final Optional<String> signature,
      final Optional<PublicAccount> signer,
      final Optional<TransactionInfo> transactionInfo) {
    super(
        TransactionType.ACCOUNT_PROPERTIES_ADDRESS,
        networkType,
        version,
        deadline,
        maxFee,
        signature,
        signer,
        transactionInfo);
    Validate.notNull(restrictionType, "RestrictionType must not be null");
    Validate.notNull(modifications, "Modifications must not be null");
    this.restrictionType = restrictionType;
    this.modifications = modifications;
  }

  /**
   * Create account address restriction transaction object
   *
   * @return {@link AccountAddressRestrictionModificationTransaction}
   */
  public static AccountAddressRestrictionModificationTransaction create(
      Deadline deadline,
      AccountRestrictionType restrictionType,
      List<AccountRestrictionModification<Address>> modifications,
      NetworkType networkType) {
    return new AccountAddressRestrictionModificationTransaction(
        networkType,
        TransactionVersion.ACCOUNT_PROPERTIES_ADDRESS.getValue(),
        deadline,
        BigInteger.ZERO,
        restrictionType,
        modifications);
  }

  /**
   * Get account restriction type
   *
   * @return {@linke AccountRestrictionType}
   */
  public AccountRestrictionType getRestrictionType() {
    return this.restrictionType;
  }

  /**
   * Get account address restriction modifications
   *
   * @return {@link List<AccountRestrictionModification<Address>>}
   */
  public List<AccountRestrictionModification<Address>> getModifications() {
    return this.modifications;
  }

  /**
   * Serialized the transaction.
   *
   * @return bytes of the transaction.
   */
  byte[] generateBytes() {
    // Add place holders to the signer and signature until actually signed
    final ByteBuffer signerBuffer = ByteBuffer.allocate(32);
    final ByteBuffer signatureBuffer = ByteBuffer.allocate(64);

    AccountAddressRestrictionTransactionBuilder txBuilder =
        AccountAddressRestrictionTransactionBuilder.create(
            new SignatureDto(signatureBuffer),
            new KeyDto(signerBuffer),
            getNetworkVersion(),
            EntityTypeDto.ACCOUNT_ADDRESS_RESTRICTION_TRANSACTION,
            new AmountDto(getFee().longValue()),
            new TimestampDto(getDeadline().getInstant()),
            AccountRestrictionTypeDto.rawValueOf(this.restrictionType.getValue()),
            getModificationBuilder());
    return txBuilder.serialize();
  }

  /**
   * Gets the embedded tx bytes.
   *
   * @return Embedded tx bytes
   */
  byte[] generateEmbeddedBytes() {
    EmbeddedAccountAddressRestrictionTransactionBuilder txBuilder =
        EmbeddedAccountAddressRestrictionTransactionBuilder.create(
            new KeyDto(getSignerBytes().get()),
            getNetworkVersion(),
            EntityTypeDto.ACCOUNT_ADDRESS_RESTRICTION_TRANSACTION,
            AccountRestrictionTypeDto.rawValueOf(this.restrictionType.getValue()),
            getModificationBuilder());
    return txBuilder.serialize();
  }

  /**
   * Gets account restriction modification.
   *
   * @return account restriction modification.
   */
  private ArrayList<AccountAddressRestrictionModificationBuilder> getModificationBuilder() {
    final ArrayList<AccountAddressRestrictionModificationBuilder> modificationBuilder =
        new ArrayList<>(modifications.size());
    for (AccountRestrictionModification<Address> accountRestrictionModification : modifications) {
      final ByteBuffer addressByteBuffer =
          accountRestrictionModification.getValue().getByteBuffer();
      final AccountAddressRestrictionModificationBuilder builder =
          AccountAddressRestrictionModificationBuilder.create(
              AccountRestrictionModificationActionDto.rawValueOf(
                  accountRestrictionModification.getModificationType().getValue()),
              new UnresolvedAddressDto(addressByteBuffer));
      modificationBuilder.add(builder);
    }
    return modificationBuilder;
  }
}
