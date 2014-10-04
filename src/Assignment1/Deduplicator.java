package Assignment1;

import java.util.BitSet;

import com.google.gson.JsonObject;

public class Deduplicator {
    
    public static final String[] keySet = {"location", "company", "Department", "title", "duration", "jobtype", "url"};
    private SimHasher simHasher;
    
    public Deduplicator(String hashAlg) {
        simHasher = new SimHasher(hashAlg);
    }
    
    public boolean isSame(JsonObject o1, JsonObject o2) {
        String s1 = getText(o1);
        String s2 = getText(o2);
        BitSet b1 = simHasher.simHash(s1);
        BitSet b2 = simHasher.simHash(s2);
        int dist = simHasher.hammingDistance(b1, b2);
        return dist <= 5;
    }
    
    private String getText(JsonObject object) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < keySet.length; i++) {
            result.append(object.get(keySet[i]) + " ");
        }
        return result.toString();
    }
}
