package SimHash;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;

import Deduplication.DeduplicatorExact;

public class SimHasher {

    public static final String CHARSET                   = "UTF-8";
    public static final int    HASH_BITS                 = 64;
    private HashFunction       hashFunction              = null;

    public SimHasher() {
        hashFunction = new NormalHashFunction();
    }

    public SimHasher(String hashAlg) {
        if (hashAlg.equals("MD5")) {
            hashFunction = new MD5HashFunction(CHARSET);
        } else if (hashAlg.equals("MurMur")) {
            hashFunction = new MurMurHashFunction(CHARSET);
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



}
