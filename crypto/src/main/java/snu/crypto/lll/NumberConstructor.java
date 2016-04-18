package snu.crypto.lll;

import java.math.BigInteger;

public interface NumberConstructor<T extends Number>
{
   T parse(String value)
      throws NumberFormatException;
   
   public class ShortConstructor implements NumberConstructor<Short> {
      public Short parse(String value) {
         return Short.parseShort(value);
      }
   }
   public final ShortConstructor SHORT = new ShortConstructor();
   
   public class BigIntegerConstructor implements NumberConstructor<BigInteger> {
      public BigInteger parse(String value) {
         return new BigInteger(value);
      }
   }
   public final BigIntegerConstructor BIG_INTEGER = new BigIntegerConstructor();
}