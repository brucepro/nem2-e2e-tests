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

import java.math.BigInteger;
import java.util.Optional;

/** UInt64Id Interface */
public interface UInt64Id {
  /**
   * Gets the UInt64Id id.
   *
   * @return BigInteger id.
   */
  BigInteger getId();

  /**
   * Gets the UInt64Id id as a long number.
   *
   * @return Long id.
   */
  long getIdAsLong();

  /**
   * Gets the UInt64Id id as a hexadecimal string.
   *
   * @return Hex id.
   */
  String getIdAsHex();

  /**
   * Get the optional UInt64Id full name.
   *
   * @return Optional<String> full name.
   */
  Optional<String> getFullName();

  /**
   * Compares UInt64Ids for equality.
   *
   * @return True if equal.
   */
  @Override
  boolean equals(Object o);
}
