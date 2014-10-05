package EmploymentJsonUtil;

import com.google.gson.JsonObject;

public class EmploymentJsonObject {

    private JsonObject           employmentJson = null;
    public static final String   CHARSET        = "UTF-8";
    public static final String[] PRIMARY_KEYS   = { "company", "department" };

    // company, department, title, jobtype, 26133
    // company, department, title, 26078
    // company, department, 7311
    // company, 5662

    public EmploymentJsonObject(JsonObject employmentJson) {
        this.employmentJson = employmentJson;
    }

    public String getPrimaryKeyName() {
        StringBuffer primaryKeys = new StringBuffer();
        for (int i = 0; i < PRIMARY_KEYS.length; i++) {
            primaryKeys.append(i == 0 ? "" : "_");
            primaryKeys.append(employmentJson.get(PRIMARY_KEYS[i]));
        }
        return primaryKeys.toString();
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
