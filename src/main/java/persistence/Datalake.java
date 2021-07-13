package persistence;

import model.Tides;
import kong.unirest.json.JSONObject;

public class Datalake {

    public static JSONObject getData(Tides tide){
        JSONObject json = new JSONObject();
        json.put("timestamp", tide.getDate());
        json.put("height", tide.getTide());
        json.put("zone1", tide.getZone1());
        json.put("zone2", tide.getZone2());
        json.put("zone3", tide.getZone3());
        json.put("zone4", tide.getZone4());
        json.put("zone5", tide.getZone5());

        return json;
    }
}
