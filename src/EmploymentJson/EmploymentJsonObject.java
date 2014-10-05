package EmploymentJson;

import com.google.gson.JsonObject;

public class EmploymentJsonObject {

    private JsonObject           employmentJson = null;
    public static final String   CHARSET        = "UTF-8";
    public static final String[] PRIMARY_KEYS   = { "company", "department", "jobtype", "title" };

    public EmploymentJsonObject(JsonObject employmentJson) {
        this.employmentJson = employmentJson;
    }

    public String getPrimaryKeyName() {
        StringBuffer primaryKeysBuffer = new StringBuffer();
        for (int i = 0; i < PRIMARY_KEYS.length; i++) {
            primaryKeysBuffer.append(i == 0 ? "" : "_");
            primaryKeysBuffer.append(employmentJson.get(PRIMARY_KEYS[i]));
        }
        String primaryKeys = primaryKeysBuffer.toString();
        primaryKeys = primaryKeys.replace("/", "$");
        return primaryKeys; // to avoid invalid parse for folders.
    }
    
    public JsonObject getJsonObject() {
        return employmentJson;
    }

    public int hashCode() {
        StringBuffer primaryKeyStr = new StringBuffer();
        for (int i = 0; i < PRIMARY_KEYS.length; i++) {
            primaryKeyStr.append(employmentJson.get(PRIMARY_KEYS[i]) + " ");
        }
        return primaryKeyStr.toString().hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof EmploymentJsonObject)) {
            return false;
        }
        return this.hashCode() == ((EmploymentJsonObject) o).hashCode();
    }
}
