package com.v2v.vehiclemap;

import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import com.google.android.gms.common.api.GoogleApiClient;

import android.os.Bundle;

import android.provider.Settings.Secure;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    final private String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    Map<String, GroundOverlay> otherCarDots = new ArrayMap<>();
    LatLng pos;
    private String TAG = "MapsActivity";
    private String android_id;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private CarDatabase carDatabase;

    private OverlayFragment overlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        android_id = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);

        this.carDatabase = CarDatabase.getInstance(this);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        overlay =  (OverlayFragment) getFragmentManager().findFragmentById(R.id.map_overlay);


        //SpriteManager.addSprite(getResources(), "test1", R.drawable.car_front);
        //SpriteManager.getSprite("test1").setPosition(200, 200);
        //SpriteManager.getSprite("test1").scale(0.15f);

        synchronized(SpriteManager.getInstance()) {
            SpriteManager.addSprite(getResources(), "warning-gradient", R.drawable.warning_gradient);
            SpriteManager.getSprite("warning-gradient").setPosition(Resources.getSystem().getDisplayMetrics().widthPixels / 2,
                    Resources.getSystem().getDisplayMetrics().heightPixels / 2);
            SpriteManager.getSprite("warning-gradient").setAlpha(0);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1.f, this);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                Car self = new Car(android_id, location.getLatitude(), location.getLongitude(), location.getSpeed(), location.getBearing());
                new CarPoster().execute(self);
            }
        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(), "NO LOCATION PERMISSIONS", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "Got GMap");
        mMap = googleMap;

        // Add a marker and move the camera there
        double lat = 32.842478;
        double lang = -96.782588;
        pos = new LatLng(lat, lang);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17.0f));

        Point p = mMap.getProjection().toScreenLocation(pos);
        synchronized(SpriteManager.getInstance()) {
            SpriteManager.addSprite(getResources(),android_id, R.drawable.car_front);
            SpriteManager.getSprite(android_id).setPosition(p.x, p.y);
            SpriteManager.getSprite(android_id).scale(0.15);

        }


        new CarGetter().execute();
    }

    public void onLocationChanged(Location location) {

        pos = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

        Point p = mMap.getProjection().toScreenLocation(pos);

        synchronized(SpriteManager.getInstance()) {

            if (!SpriteManager.contains(android_id)) {
                SpriteManager.addSprite(getResources(), android_id, R.drawable.car_front);
                SpriteManager.getSprite(android_id).scale(0.15);
            }

            SpriteManager.getSprite(android_id).setPosition(p.x, p.y);
        }

        Car self = new Car(android_id, location.getLatitude(), location.getLongitude(), location.getSpeed(), location.getBearing());
        new CarPoster().execute(self);

        redrawMapMarkers();
    }

    public void onProviderEnabled(String s) {

    }

    public void onProviderDisabled(String s) {

    }

    public void onStatusChanged(String s, int i, Bundle b) {

    }

    private void redrawMapMarkers() {
        ArrayList<Car> cars = carDatabase.getAllCars();
        Set<String> oldCars = new ArraySet<>();
        for(String carid: otherCarDots.keySet()) oldCars.add(carid);
        Point p;

        // add other cars
        for (Car car : cars) {
            if (car.id.equals(android_id)) continue;
            pos = new LatLng(car.lat, car.lon);
            p = mMap.getProjection().toScreenLocation(pos);

            //Log.d("MapsActivity", "Updating car " + car.id + " at screen position (" + p.x + ", " + p.y);

            synchronized(SpriteManager.getInstance()) {
                if (oldCars.contains(car.id)) {

                    SpriteManager.getSprite(car.id).setPosition(p.x, p.y);
                    //otherCarDots.get(car.id).setPosition(pos);
                    oldCars.remove(car.id);

                } else {

                    SpriteManager.addSprite(getResources(), car.id, R.drawable.car_front);
                    SpriteManager.getSprite(car.id).setPosition(p.x, p.y);
                    SpriteManager.getSprite(car.id).scale(0.15);
                    //SpriteManager.getSprite(car.id).setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    //otherCarDots.put(car.id, mMap.addGroundOverlay(new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.dot)).position(pos, 20.f)));

                }
            }

        }

        for (String oldCar : oldCars) {
            //otherCarDots.get(oldCar).remove();
            //otherCarDots.remove(oldCar);
            synchronized(SpriteManager.getInstance()) {
                SpriteManager.removeSprite(oldCar);
            }
        }

    }

    private final class CarGetter extends AsyncTask<Void, Void, Void> {
        private String TAG = "MapsActivity>CarGetter";
        private String url = "http://conradhappeliv.com:1337/getall";

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "Starting CarGetter");
        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.GET(url, null);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONArray carArr = new JSONArray(jsonStr);
                    for (int i = 0; i < carArr.length(); i++) {
                        Car c = new Car(carArr.getJSONObject(i));
                        carDatabase.updateCar(c);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            Log.d(TAG, "Ending CarGetter");
            super.onPostExecute(res);
            redrawMapMarkers();
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            new CarGetter().execute();
                        }
                    },
                    1000
            );
        }
    }

    public final class CarPoster extends AsyncTask<Car, Void, Void> {
        private String TAG = "MapsActivity>CarPoster";
        private String url = "http://conradhappeliv.com:1337/update/";

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "Starting CarPoster");
        }

        @Override
        protected Void doInBackground(Car... param) {
            HttpHandler sh = new HttpHandler();

            Car c = param[0];
            Map<String, String> params = new ArrayMap<>();
            params.put("given_id", c.id);
            params.put("lat", Double.toString(c.lat));
            params.put("long", Double.toString(c.lon));
            params.put("speed", Double.toString(c.speed));
            params.put("bearing", Double.toString(c.bearing));

            // ignore response, don't need it for now
            String jsonStr = sh.POST(url, params);
            Log.e(TAG, "Response from url: " + jsonStr);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            Log.d(TAG, "Ending CarPoster");
        }
    }
}
