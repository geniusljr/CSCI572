package Deduplication;

import java.util.BitSet;

import EmploymentJsonUtil.EmploymentJsonSimHasher;

import com.google.gson.JsonObject;

public class DeduplicatorUtil {
    
    public static final String[] keySet = {"company", "Department", "title", "jobtype"};
    
    public static int hammingDistance(final BitSet b1, final BitSet b2) {
        BitSet newB1 = (BitSet) b1.clone();
        newB1.xor(b2);
        int diff = newB1.cardinality();
        return diff;
    }
    
    public static boolean isSame(BitSet b1, BitSet b2, int distThreshold) {
        int dist = hammingDistance(b1, b2);
        return dist <= distThreshold;
    }
    
    public static boolean isSame(JsonObject o1, JsonObject o2, int distThreshold, EmploymentJsonSimHasher sh) {
        BitSet b1 = sh.simHash(o1);
        BitSet b2 = sh.simHash(o2);
        int dist = hammingDistance(b1, b2);
        return dist <= distThreshold;
    }
    
}
