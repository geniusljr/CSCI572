package Assignment1;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;

public class SimHasher {
    
    public static final int HASH_BITS = 64;
    public static final String CHARSET = "UTF-8";
    private HashFunction hashFunction = null;
    
    public SimHasher(String hashAlg) {
        if (hashAlg.equals("MD5")) {
            hashFunction = new MD5HashFunction(CHARSET);
        }
    }
    
    public BitSet simHash(final String text) {
        HashMap<String, Integer> tokenFrequency = new HashMap<String, Integer>();
        tokenFrequency = tokenize(text);
        int[] hashWithWeight = new int[HASH_BITS];
        Arrays.fill(hashWithWeight, 0);
        for (String token : tokenFrequency.keySet()) {
            int weight = tokenFrequency.get(token);
            BitSet tokenHash = this.hashForToken(token);
            for (int i = 0; i < HASH_BITS; i++) {
                hashWithWeight[i] += tokenHash.get(i) ? weight : -weight;
            }
        }
        BitSet simHash = new BitSet(HASH_BITS);
        for (int i = 0; i < HASH_BITS; i++) {
            simHash.set(i, hashWithWeight[i] > 0 ? true : false);
        }
        return simHash;
    }
    
    public HashMap<String, Integer> tokenize(final String text) {
        HashMap<String, Integer> tokenFrequency = new HashMap<String, Integer>();
        String[] strArr = text.split(" ");
        for (int i = 0; i < strArr.length; i++) {
            if (tokenFrequency.containsKey(strArr[i])) {
                tokenFrequency.put(strArr[i], tokenFrequency.get(strArr[i]) + 1);
            } else {
                tokenFrequency.put(strArr[i], 1);
            }
        }
        return tokenFrequency;
    }
    
    public BitSet hashForToken(final String token) {
        BitSet hash = hashFunction.hash(token);
        return hash;
    }
    
    public int hammingDistance(final BitSet b1, final BitSet b2) {
        BitSet newB1 = (BitSet) b1.clone();
        newB1.xor(b2);
        int diff = newB1.cardinality();
        return diff;
    }
    
    public static void main(String[] args) throws Exception {
        SimHasher sh = new SimHasher("MD5");
        String s1 = "Hello world Yishu Wu A B C D E F G";
        String s2 = "fuck shit lol Wu A B E D 1 F";
        BitSet b1 = sh.simHash(s1);
        BitSet b2 = sh.simHash(s2);
        for (int i = 0; i < HASH_BITS; i++) {
            System.out.print(b1.get(i) ? 1 : 0);
        }
        System.out.println();
        for (int i = 0; i < HASH_BITS; i++) {
            System.out.print(b2.get(i) ? 1 : 0);
        }
        System.out.println();
        System.out.println(sh.hammingDistance(b1, b2));
        for (int i = 0; i < HASH_BITS; i++) {
            System.out.print(b1.get(i) ? 1 : 0);
        }
    }
    
}
