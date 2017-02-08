package com.v2v.vehiclemap;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public final class CarPoster extends AsyncTask<Void, Void, JSONArray>  {
    private static String url = "";

    @Override
    protected JSONArray doInBackground(Void... ok) {

        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.POST(url);

        Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONArray jsonObj = new JSONArray(jsonStr);
                return jsonObj;
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        super.onPostExecute(result);
        //Do anything with response..
    }
}
