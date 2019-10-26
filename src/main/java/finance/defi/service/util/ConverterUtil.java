/*
 * Copyright 2019 Web3 Labs LTD.
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

import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

/** Ethereum unit conversion functions. */
public final class ConverterUtil {

    private ConverterUtil() {}

    public static BigInteger toWei(String number, Convert.Unit unit) {
        BigDecimal bg1 = Convert.toWei(new BigDecimal(number), unit);

        return bg1.toBigInteger();
    }

    public static BigInteger toWei(BigDecimal number, Convert.Unit unit) {
        BigDecimal bg1 = Convert.toWei(number, unit);

        return bg1.toBigInteger();
    }

    public static BigInteger fromWei(String number, Convert.Unit unit) {
        return fromWei(new BigInteger(number), unit);
    }

    public static BigInteger fromWei(BigInteger number, Convert.Unit unit) {
        return number.divide(unit.getWeiFactor().toBigInteger());
    }
}
