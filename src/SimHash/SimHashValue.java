package SimHash;

import java.util.BitSet;

import Deduplication.DeduplicatorExact;

public class SimHashValue {
    
    BitSet bitSet = null;
    private static final int HAMMING_DISTANCE_THRESHOLD = 15;
    
    public SimHashValue(BitSet bitSet) {
        this.bitSet = bitSet;
    }
    
    public int hashCode() {
        return 1;
    }
    

}
