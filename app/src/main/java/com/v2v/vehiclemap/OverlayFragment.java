package com.v2v.vehiclemap;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Point;
import android.os.Bundle;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Mitchel on 2/11/2017.
 */

public class OverlayFragment extends Fragment {

    private OverlaySurface surface;

    public OverlayFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        surface = new OverlaySurface(getContext());

        return inflater.inflate(R.layout.overlay_fragment, container, false);

    }

    @Override
    public void onStop () {
        surface.signalThreadEnd();
        super.onStop();
    }

}