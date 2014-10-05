package EmploymentJson;

import java.util.BitSet;

import SimHash.SimHasher;

import com.google.gson.JsonObject;

public class EmploymentJsonSimHasher extends SimHasher {
    public static final String[] keySet = { "location", "company", "Department", "title",
            "duration", "jobtype", "url" };
    
    public EmploymentJsonSimHasher() {
        super();
    }
    
    public EmploymentJsonSimHasher(String hashAlg) {
        super(hashAlg);
    }

    public BitSet simHash(final JsonObject o) {
        StringBuffer jsonString = new StringBuffer();
        for (int i = 0; i < keySet.length; i++) {
            jsonString.append(o.get(keySet[i]) + " ");
        }
        
        return simHash(jsonString.toString());
    }

}
