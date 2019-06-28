/**
*** Copyright (c) 2016-present,
*** Jaguar0625, gimre, BloodyRookie, Tech Bureau, Corp. All rights reserved.
***
*** This file is part of Catapult.
***
*** Catapult is free software: you can redistribute it and/or modify
*** it under the terms of the GNU Lesser General Public License as published by
*** the Free Software Foundation, either version 3 of the License, or
*** (at your option) any later version.
***
*** Catapult is distributed in the hope that it will be useful,
*** but WITHOUT ANY WARRANTY; without even the implied warranty of
*** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
*** GNU Lesser General Public License for more details.
***
*** You should have received a copy of the GNU Lesser General Public License
*** along with Catapult. If not, see <http://www.gnu.org/licenses/>.
**/

package io.nem.catapult.builders;

import java.io.DataInput;
import java.util.ArrayList;

/** Binary layout for a non-embedded modify multisig account transaction. */
public final class ModifyMultisigAccountTransactionBuilder extends TransactionBuilder {
    /** Modify multisig account transaction body. */
    private final ModifyMultisigAccountTransactionBodyBuilder modifyMultisigAccountTransactionBody;

    /**
     * Constructor - Creates an object from stream.
     *
     * @param stream Byte stream to use to serialize the object.
     */
    protected ModifyMultisigAccountTransactionBuilder(final DataInput stream) {
        super(stream);
        this.modifyMultisigAccountTransactionBody = ModifyMultisigAccountTransactionBodyBuilder.loadFromBinary(stream);
    }

    /**
     * Constructor.
     *
     * @param signature Entity signature.
     * @param signer Entity signer's public key.
     * @param version Entity version.
     * @param type Entity type.
     * @param fee Transaction fee.
     * @param deadline Transaction deadline.
     * @param minRemovalDelta Relative change of the minimal number of cosignatories required when removing an account.
     * @param minApprovalDelta Relative change of the minimal number of cosignatories required when approving a transaction.
     * @param modifications Attached cosignatory modifications.
     */
    protected ModifyMultisigAccountTransactionBuilder(final SignatureDto signature, final KeyDto signer, final short version, final EntityTypeDto type, final AmountDto fee, final TimestampDto deadline, final byte minRemovalDelta, final byte minApprovalDelta, final ArrayList<CosignatoryModificationBuilder> modifications) {
        super(signature, signer, version, type, fee, deadline);
        this.modifyMultisigAccountTransactionBody = ModifyMultisigAccountTransactionBodyBuilder.create(minRemovalDelta, minApprovalDelta, modifications);
    }

    /**
     * Creates an instance of ModifyMultisigAccountTransactionBuilder.
     *
     * @param signature Entity signature.
     * @param signer Entity signer's public key.
     * @param version Entity version.
     * @param type Entity type.
     * @param fee Transaction fee.
     * @param deadline Transaction deadline.
     * @param minRemovalDelta Relative change of the minimal number of cosignatories required when removing an account.
     * @param minApprovalDelta Relative change of the minimal number of cosignatories required when approving a transaction.
     * @param modifications Attached cosignatory modifications.
     * @return Instance of ModifyMultisigAccountTransactionBuilder.
     */
    public static ModifyMultisigAccountTransactionBuilder create(final SignatureDto signature, final KeyDto signer, final short version, final EntityTypeDto type, final AmountDto fee, final TimestampDto deadline, final byte minRemovalDelta, final byte minApprovalDelta, final ArrayList<CosignatoryModificationBuilder> modifications) {
        return new ModifyMultisigAccountTransactionBuilder(signature, signer, version, type, fee, deadline, minRemovalDelta, minApprovalDelta, modifications);
    }

    /**
     * Gets relative change of the minimal number of cosignatories required when removing an account.
     *
     * @return Relative change of the minimal number of cosignatories required when removing an account.
     */
    public byte getMinRemovalDelta() {
        return this.modifyMultisigAccountTransactionBody.getMinRemovalDelta();
    }

    /**
     * Gets relative change of the minimal number of cosignatories required when approving a transaction.
     *
     * @return Relative change of the minimal number of cosignatories required when approving a transaction.
     */
    public byte getMinApprovalDelta() {
        return this.modifyMultisigAccountTransactionBody.getMinApprovalDelta();
    }

    /**
     * Gets attached cosignatory modifications.
     *
     * @return Attached cosignatory modifications.
     */
    public ArrayList<CosignatoryModificationBuilder> getModifications() {
        return this.modifyMultisigAccountTransactionBody.getModifications();
    }

    /**
     * Gets the size of the object.
     *
     * @return Size in bytes.
     */
    @Override
    public int getSize() {
        int size = super.getSize();
        size += this.modifyMultisigAccountTransactionBody.getSize();
        return size;
    }

    /**
     * Creates an instance of ModifyMultisigAccountTransactionBuilder from a stream.
     *
     * @param stream Byte stream to use to serialize the object.
     * @return Instance of ModifyMultisigAccountTransactionBuilder.
     */
    public static ModifyMultisigAccountTransactionBuilder loadFromBinary(final DataInput stream) {
        return new ModifyMultisigAccountTransactionBuilder(stream);
    }

    /**
     * Serializes an object to bytes.
     *
     * @return Serialized bytes.
     */
    public byte[] serialize() {
        return GeneratorUtils.serialize(dataOutputStream -> {
            final byte[] superBytes = super.serialize();
            dataOutputStream.write(superBytes, 0, superBytes.length);
            final byte[] modifyMultisigAccountTransactionBodyBytes = this.modifyMultisigAccountTransactionBody.serialize();
            dataOutputStream.write(modifyMultisigAccountTransactionBodyBytes, 0, modifyMultisigAccountTransactionBodyBytes.length);
        });
    }
}
