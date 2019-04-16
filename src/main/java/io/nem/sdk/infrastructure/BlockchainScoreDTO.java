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

/*
 * NIS2 API
 * This document defines all the nis2 api routes and behaviour
 *
 * OpenAPI spec version: 1.0.0
 * Contact: guillemchain@gmail.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.nem.sdk.infrastructure;

import com.google.gson.annotations.SerializedName;
import io.nem.sdk.model.transaction.UInt64;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigInteger;
import java.util.Objects;

/**
 * BlockchainScoreDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2017-12-19T19:07:40.115Z")
class BlockchainScoreDTO {
    @SerializedName("scoreHigh")
    private UInt64DTO scoreHigh = null;

    @SerializedName("scoreLow")
    private UInt64DTO scoreLow = null;

    public BlockchainScoreDTO scoreHigh(UInt64DTO scoreHigh) {
        this.scoreHigh = scoreHigh;
        return this;
    }

    /**
     * Get scoreHigh
     *
     * @return scoreHigh
     **/
    @ApiModelProperty(required = true, value = "")
    public UInt64DTO getScoreHigh() {
        return scoreHigh;
    }

    public void setScoreHigh(UInt64DTO scoreHigh) {
        this.scoreHigh = scoreHigh;
    }

    public BlockchainScoreDTO scoreLow(UInt64DTO scoreLow) {
        this.scoreLow = scoreLow;
        return this;
    }

    /**
     * Get scoreLow
     *
     * @return scoreLow
     **/
    @ApiModelProperty(required = true, value = "")
    public UInt64DTO getScoreLow() {
        return scoreLow;
    }

    public void setScoreLow(UInt64DTO scoreLow) {
        this.scoreLow = scoreLow;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BlockchainScoreDTO blockchainScoreDTO = (BlockchainScoreDTO) o;
        return Objects.equals(this.scoreHigh, blockchainScoreDTO.scoreHigh) &&
                Objects.equals(this.scoreLow, blockchainScoreDTO.scoreLow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scoreHigh, scoreLow);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BlockchainScoreDTO {\n");

        sb.append("    scoreHigh: ").append(toIndentedString(scoreHigh)).append("\n");
        sb.append("    scoreLow: ").append(toIndentedString(scoreLow)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    BigInteger extractIntArray() {
        return UInt64.fromIntArray(new int[]{scoreLow.extractIntArray().intValue(), scoreHigh.extractIntArray().intValue()});
    }
}

