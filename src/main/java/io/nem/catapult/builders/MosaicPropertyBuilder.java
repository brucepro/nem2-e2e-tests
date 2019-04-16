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

public class MosaicPropertyBuilder {
    public MosaicPropertyIdBuilder getId()  {
        return this.id;
    }

    public void setId(MosaicPropertyIdBuilder id)  {
        this.id = id;
    }

    public long getValue()  {
        return this.value;
    }

    public void setValue(long value)  {
        this.value = value;
    }

    public static MosaicPropertyBuilder loadFromBinary(DataInput stream) throws Exception {
        MosaicPropertyBuilder obj = new MosaicPropertyBuilder();
        obj.setId(MosaicPropertyIdBuilder.loadFromBinary(stream));
        obj.setValue(Long.reverseBytes(stream.readLong()));
        return obj;
    }

    public byte[] serialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(bos);
        byte[] id = this.getId().serialize();
        stream.write(id, 0, id.length);
        stream.writeLong(Long.reverseBytes(this.getValue()));
        stream.close();
        return bos.toByteArray();
    }

    private MosaicPropertyIdBuilder id;
    private long value;

}
