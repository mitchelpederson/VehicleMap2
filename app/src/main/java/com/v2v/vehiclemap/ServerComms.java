package com.v2v.vehiclemap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// TODO: implement
public class ServerComms {
    public static ArrayList<Car> getAllCars() throws JSONException {
        // send GET req with payload { "given_id": "thiscarid" }
        CarGetter dataGet = new CarGetter();
        JSONArray jsonarray = dataGet.doInBackground(); // gets json array

        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            String given_id = jsonobject.getString("given_id");
            String lat = jsonobject.getString("lat");
            String lng = jsonobject.getString("long");
            String speed = jsonobject.getString("speed");
            String bearing = jsonobject.getString("bearing");
        }

        // request returns [{given_id -> String, lat, long, speed, bearing}]
        return null;
    }

    public static void sendLocation(Car c) throws JSONException {
        // send POST req with payload { given_id, lat, long, speed, bearing } (everything optional)
        // request returns the current car's DB entry

        CarPoster dataPost = new CarPoster();
        JSONArray jsonarray = dataPost.doInBackground(); // gets json array

        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            String given_id = jsonobject.getString("given_id");
            String lat = jsonobject.getString("lat");
            String lng = jsonobject.getString("long");
            String speed = jsonobject.getString("speed");
            String bearing = jsonobject.getString("bearing");
        }

    }
}
