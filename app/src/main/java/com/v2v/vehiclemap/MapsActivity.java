package com.v2v.vehiclemap;

import android.app.FragmentManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.app.FragmentTransaction;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;

    final private String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1.f, this);

        /*FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        OverlayFragment overlayFragment = new OverlayFragment();
        fragmentTransaction.add(overlayFragment, "overlay_frag");
        fragmentTransaction.commit();*/


    }

    GroundOverlay dot;
    float lat, lang;

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        lat = 32.77f;
        lang = -96.80f;
        pos = new LatLng(lat, lang);

        dot = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.dot))
                .position(pos, 20.f));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17.0f));
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
