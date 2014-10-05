package SimHash;

import java.util.BitSet;

import Deduplication.DeduplicatorUtil;

public class SimHashValue {
    
    BitSet bitSet = null;
    private static final int HAMMING_DISTANCE_THRESHOLD = 15;
    
    public SimHashValue(BitSet bitSet) {
        this.bitSet = bitSet;
    }
    
    public int hashCode() {
        return 1;
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof SimHashValue)) {
            return false;
        }
        if ( DeduplicatorUtil.isSame(bitSet, ((SimHashValue) o).bitSet, HAMMING_DISTANCE_THRESHOLD) ) {
            //System.out.println("same");
            return true;
        } 
        return false;
    }

}
