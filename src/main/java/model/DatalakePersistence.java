package model;

import kong.unirest.json.JSONObject;

public interface DatalakePersistence {

    void write(JSONObject datalake);
    Double getHeight(String s);
}
