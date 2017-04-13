package com.v2v.vehiclemap;

/**
 * Created by kreesperez94 on 4/6/17.
 */
import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

public class PermissionsActiviy extends runtimePermissions{
    private static final int REQUEST_PERMISSION = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        requestAppPermissions(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.ACCESS_FINE_LOCATION
            },
            R.string.msg,REQUEST_PERMISSION
        );

    }

    @Override
    public void onPermissionGranted(int requestCode){
        //WHAT TO DO WHEN PERMISSION GRANTED
        Toast.makeText(getApplicationContext(), "Permissions Granted", Toast.LENGTH_LONG).show();
    }
}
