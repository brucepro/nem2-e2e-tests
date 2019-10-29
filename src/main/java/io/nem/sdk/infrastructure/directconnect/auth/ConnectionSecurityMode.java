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

package io.nem.sdk.infrastructure.directconnect.auth;

/** Connection security mode. */
public enum ConnectionSecurityMode {
  /** No security. */
  NONE((byte) 1),
  /** Signed security. */
  SIGNED((byte) 2);

  /* Security mode value. */
  private final byte mode;

  /**
   * Constructor.
   *
   * @param mode Security mode.
   */
  ConnectionSecurityMode(final byte mode) {
    this.mode = mode;
  }

  /**
   * Gets the value of the enum.
   *
   * @return Value of the enum.
   */
  byte getValue() {
    return this.mode;
  }
}