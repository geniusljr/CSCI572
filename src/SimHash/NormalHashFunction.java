package SimHash;

import java.math.BigInteger;
import java.util.BitSet;

public class NormalHashFunction extends HashFunction {

    @Override
    public BitSet hash(String text) {
        BitSet hash = new BitSet(64);
        if (text == null || text.length() == 0) {
            return hash;
        }
        char[] sourceArray = text.toCharArray();
        BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
        BigInteger m = new BigInteger("1000003");
        BigInteger mask = new BigInteger("2").pow(64).subtract(new BigInteger("1"));
        for (char item : sourceArray) {
            BigInteger temp = BigInteger.valueOf((long) item);
            x = x.multiply(m).xor(temp).and(mask);
        }
        x = x.xor(new BigInteger(String.valueOf(text.length())));
        if (x.equals(new BigInteger("-1"))) {
            x = new BigInteger("-2");
        }
        for (int i = 0; i < 64; i++) {
            BigInteger bitmask = new BigInteger("1").shiftLeft(i);
            hash.set(i, x.and(bitmask).signum() != 0);
        }
        return hash;

    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

}
