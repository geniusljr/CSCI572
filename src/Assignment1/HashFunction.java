package Assignment1;

import java.util.BitSet;

public abstract class HashFunction {
    public abstract BitSet hash(String text);
    public abstract String getName();
}
