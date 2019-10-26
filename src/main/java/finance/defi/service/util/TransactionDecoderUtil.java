/*
 * Copyright 2019 Web3 Labs Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package finance.defi.service.util;

import java.math.BigInteger;

import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.*;
import org.web3j.crypto.SignedRawTransaction;
import org.web3j.rlp.RlpDecoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.utils.Numeric;

public class TransactionDecoderUtil {

    public static RawTransaction decode(final String hexTransaction) {

        byte[] transaction = Numeric.hexStringToByteArray(hexTransaction);
        RlpList rlpList = RlpDecoder.decode(transaction);
        RlpList values = (RlpList) rlpList.getValues().get(0);
        BigInteger nonce = ((RlpString) values.getValues().get(0)).asPositiveBigInteger();
        BigInteger gasPrice = ((RlpString) values.getValues().get(1)).asPositiveBigInteger();
        BigInteger gasLimit = ((RlpString) values.getValues().get(2)).asPositiveBigInteger();
        String to = ((RlpString) values.getValues().get(3)).asString();
        BigInteger value = ((RlpString) values.getValues().get(4)).asPositiveBigInteger();
        String data = ((RlpString) values.getValues().get(5)).asString();
        if (values.getValues().size() > 6) {
            byte v = ((RlpString) values.getValues().get(6)).getBytes()[0];
            byte[] r = Numeric.toBytesPadded(
                Numeric.toBigInt(((RlpString) values.getValues().get(7)).getBytes()), 32);
            byte[] s = Numeric.toBytesPadded(
                Numeric.toBigInt(((RlpString) values.getValues().get(8)).getBytes()), 32);
            Sign.SignatureData signatureData = new Sign.SignatureData(v, r, s);
            return new SignedRawTransaction(nonce, gasPrice, gasLimit,
                to, value, data, signatureData);
        } else {
            return RawTransaction.createTransaction(nonce,
                gasPrice, gasLimit, to, value, data);
        }

    }
}
