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

/**
 * Enum containing transaction type versions.
 *
 * <p>Transaction format versions are defined in catapult-server in each transaction's plugin source
 * code.
 *
 * <p>In [catapult-server](https://github.com/nemtech/catapult-server), the
 * `DEFINE_TRANSACTION_CONSTANTS` macro is used to define the `TYPE` and `VERSION` of the
 * transaction format.
 *
 * @see
 *     https://github.com/nemtech/catapult-server/blob/master/plugins/txes/transfer/src/model/TransferTransaction.h#L37
 * @since 1.0
 */
public enum TransactionVersion {

  // Mosaic
  /** Mosaic definition transaction type. */
  MOSAIC_DEFINITION((short) 1),

  /** Mosaic supply change transaction. */
  MOSAIC_SUPPLY_CHANGE((short) 1),

  // Namespace
  /** Register namespace transaction type. */
  REGISTER_NAMESPACE((short) 1),

  /** Address alias transaction type. */
  ADDRESS_ALIAS((short) 1),

  /** Mosaic alias transaction type. */
  MOSAIC_ALIAS((short) 1),

  // Transfer
  /** Transfer Transaction transaction type. */
  TRANSFER((short) 1),

  // Multisignature
  /** Modify multisig account transaction type. */
  MODIFY_MULTISIG_ACCOUNT((short) 1),

  /** Aggregate complete transaction type. */
  AGGREGATE_COMPLETE((short) 1),

  /** Aggregate bonded transaction type */
  AGGREGATE_BONDED((short) 1),

  /** Hash Lock transaction type */
  LOCK((short) 1),

  // Account filters
  /** Account properties address transaction type */
  ACCOUNT_PROPERTIES_ADDRESS((short) 1),

  /** Account properties mosaic transaction type */
  ACCOUNT_PROPERTIES_MOSAIC((short) 1),

  /** Account properties entity type transaction type */
  ACCOUNT_PROPERTIES_ENTITY_TYPE((short) 1),

  // Metadata
  /** Account metadata transaction type */
  ACCOUNT_METADATA((short) 1),

  /** Mosaic metadata transaction type */
  MOSAIC_METADATA((short) 1),

  /** Namespace metadata transaction type */
  NAMESPACE_METADATA((short) 1),

  // Cross-chain swaps
  /** Secret Lock Transaction type */
  SECRET_LOCK((short) 1),

  /** Secret Proof transaction type */
  SECRET_PROOF((short) 1),

  // Remote harvesting
  /** Account link transaction type */
  ACCOUNT_LINK((short) 1);

  private final Short value;

  TransactionVersion(final Short value) {
    this.value = value;
  }

  /**
   * Returns enum value.
   *
   * @return enum value
   */
  public Short getValue() {
    return this.value;
  }
}
