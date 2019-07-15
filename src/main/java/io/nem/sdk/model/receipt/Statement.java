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

package io.nem.sdk.model.receipt;

import io.nem.sdk.model.account.Address;
import io.nem.sdk.model.mosaic.MosaicId;

import java.util.List;

public class Statement {
  private final List<TransactionStatement> transactionStatements;
  private final List<ResolutionStatement<Address>> addressResolutionStatements;
  private final List<ResolutionStatement<MosaicId>> mosaicResolutionStatement;
  /**
   * Constructor
   *
   * @param transactionStatements Array of transaction statements.
   * @param addressResolutionStatements Array of address resolution statements.
   * @param mosaicResolutionStatement Array of mosaic resolution statements.
   */
  public Statement(
      List<TransactionStatement> transactionStatements,
      List<ResolutionStatement<Address>> addressResolutionStatements,
      List<ResolutionStatement<MosaicId>> mosaicResolutionStatement) {
    this.addressResolutionStatements = addressResolutionStatements;
    this.mosaicResolutionStatement = mosaicResolutionStatement;
    this.transactionStatements = transactionStatements;
  }

  /**
   * Returns transaction statements
   *
   * @return transaction statements
   */
  public List<TransactionStatement> getTransactionStatements() {
    return this.transactionStatements;
  }

  /**
   * Returns address resolution statements.
   *
   * @return address resolution statements.
   */
  public List<ResolutionStatement<Address>> getAddressResolutionStatements() {
    return this.addressResolutionStatements;
  }

  /**
   * Returns mosaic resolution statements.
   *
   * @return mosaic resolution statements.
   */
  public List<ResolutionStatement<MosaicId>> getMosaicResolutionStatement() {
    return this.mosaicResolutionStatement;
  }
}
