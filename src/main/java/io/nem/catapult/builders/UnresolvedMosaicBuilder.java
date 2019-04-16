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

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutputStream;

public class UnresolvedMosaicBuilder {
    public long getMosaicid()  {
        return this.mosaicId;
    }

    public void setMosaicid(long mosaicId)  {
        this.mosaicId = mosaicId;
    }

    public long getAmount()  {
        return this.amount;
    }

    public void setAmount(long amount)  {
        this.amount = amount;
    }

    public static UnresolvedMosaicBuilder loadFromBinary(DataInput stream) throws Exception {
        UnresolvedMosaicBuilder obj = new UnresolvedMosaicBuilder();
        obj.setMosaicid(Long.reverseBytes(stream.readLong()));
        obj.setAmount(Long.reverseBytes(stream.readLong()));
        return obj;
    }

    public byte[] serialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(bos);
        stream.writeLong(Long.reverseBytes(this.getMosaicid()));
        stream.writeLong(Long.reverseBytes(this.getAmount()));
        stream.close();
        return bos.toByteArray();
    }

    private long mosaicId;
    private long amount;

}
