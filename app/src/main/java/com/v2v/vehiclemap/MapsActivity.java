package com.v2v.vehiclemap;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private CarDatabase carDatabase;

    final private String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.carDatabase = new CarDatabase(this);
        if(this.carDatabase.getAllCars().isEmpty()) this.carDatabase.seedData();

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1.f, (LocationListener) this);
    }

    GroundOverlay dot;
    double lat, lang;

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ArrayList<Car> cars = carDatabase.getAllCars();

        // Add a marker and move the camera there
        lat = 32.842478;
        lang = -96.782588;
        pos = new LatLng(lat, lang);
        dot = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.dot))
                .position(pos, 20.f));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17.0f));

        // add other cars
        for(Car car:cars) {
            pos = new LatLng(car.lat, car.lon);
            mMap.addGroundOverlay(new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.drawable.dot))
                    .position(pos, 20.f));
        }
    }

    LatLng pos;

    public void onLocationChanged(Location location) {

        pos = new LatLng(location.getLatitude(), location.getLongitude());

        dot.setPosition(pos);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

    }

    public void onProviderEnabled(String s) {

    }

    public void onProviderDisabled(String s) {

    }

    public void onStatusChanged(String s, int i, Bundle b) {

    }
}
