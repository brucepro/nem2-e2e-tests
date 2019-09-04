/**
 * ** Copyright (c) 2016-present,
 * ** Jaguar0625, gimre, BloodyRookie, Tech Bureau, Corp. All rights reserved.
 * **
 * ** This file is part of Catapult.
 * **
 * ** Catapult is free software: you can redistribute it and/or modify
 * ** it under the terms of the GNU Lesser General Public License as published by
 * ** the Free Software Foundation, either version 3 of the License, or
 * ** (at your option) any later version.
 * **
 * ** Catapult is distributed in the hope that it will be useful,
 * ** but WITHOUT ANY WARRANTY; without even the implied warranty of
 * ** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * ** GNU Lesser General Public License for more details.
 * **
 * ** You should have received a copy of the GNU Lesser General Public License
 * ** along with Catapult. If not, see <http://www.gnu.org/licenses/>.
 **/

package io.nem.sdk.infrastructure.directconnect.dataaccess.dao;

import io.nem.sdk.infrastructure.common.BlockchainRepository;
import io.nem.sdk.infrastructure.common.CatapultContext;
import io.nem.sdk.infrastructure.directconnect.dataaccess.database.mongoDb.BlocksCollection;
import io.nem.sdk.infrastructure.directconnect.dataaccess.database.mongoDb.ChainStatisticCollection;
import io.nem.sdk.infrastructure.directconnect.dataaccess.database.mongoDb.TransactionsCollection;
import io.nem.sdk.model.blockchain.BlockInfo;
import io.nem.sdk.model.blockchain.ChainStatisticInfo;
import io.nem.sdk.model.transaction.Transaction;
import io.reactivex.Observable;

import java.math.BigInteger;
import java.util.List;

/** Blockchain dao repository. */
public class BlockchainDao implements BlockchainRepository {
  /* Catapult context. */
  private final CatapultContext catapultContext;

  /**
   * Constructor.
   *
   * @param context Catapult context.
   */
  public BlockchainDao(final CatapultContext context) {
    this.catapultContext = context;
  }

  /**
   * Gets the block info at specific height.
   *
   * @param height Height of the block.
   * @return Block info.
   */
  @Override
  public Observable<BlockInfo> getBlockByHeight(final BigInteger height) {
    return Observable.fromCallable(
        () -> new BlocksCollection(catapultContext).find(height.longValue()).get());
  }

  /**
   * Gets a list of transactions for a specific block.
   *
   * @param height Height of the block.
   * @return List of transactions.
   */
  @Override
  public Observable<List<Transaction>> getBlockTransactions(final BigInteger height) {
    return Observable.fromCallable(
        () -> new TransactionsCollection(catapultContext).findByBlockHeight(height.longValue()));
  }

  /**
   * Gets the height of the blockchain.
   *
   * @return Height of the blockchain.
   */
  @Override
  public Observable<BigInteger> getBlockchainHeight() {
    return Observable.fromCallable(
        () -> new ChainStatisticCollection(catapultContext).get().getNumBlocks());
  }

  /**
   * Gets the score of the blockchain.
   *
   * @return Score of the blockchain.
   */
  public Observable<BigInteger> getBlockchainScore() {
    return Observable.fromCallable(
        () -> {
          final ChainStatisticInfo chainStatisticInfo =
              new ChainStatisticCollection(catapultContext).get();
          return chainStatisticInfo
              .getScoreHigh()
              .shiftLeft(64 /*sizeof(long)*/)
              .add(chainStatisticInfo.getScoreLow());
        });
  }
}
