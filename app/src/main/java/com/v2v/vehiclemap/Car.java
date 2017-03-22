package com.v2v.vehiclemap;

import org.json.JSONException;
import org.json.JSONObject;

public final class Car {
    public String id = "";
    public double lat = 0;
    public double lon = 0;
    public double speed = 0;
    public double bearing = 0;
    public double last_update = 0;

    public Car() {}

    public Car(String id, double l1, double l2, double s, double b) {
        this.id = id;
        this.lat = l1;
        this.lon = l2;
        this.speed = s;
        this.bearing = b;
    }

    public Car(JSONObject obj) throws JSONException {
        this.id = obj.getString("given_id");
        this.lat = obj.getDouble("lat");
        this.lon = obj.getDouble("long");
        this.speed = obj.getDouble("speed");
        this.bearing = obj.getDouble("bearing");
        this.last_update = obj.getDouble("last_update");
    }
}
