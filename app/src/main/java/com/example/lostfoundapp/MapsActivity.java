package com.example.lostfoundapp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;

import com.example.lostfoundapp.data.DatabaseHelper;
import com.example.lostfoundapp.util.Util;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.lostfoundapp.databinding.ActivityMapsBinding;
import com.google.android.gms.location.Priority;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DatabaseHelper(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null)
        {
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Cursor cursor = db.getAllAdverts();

        while(cursor.moveToNext())
        {
            //checking lat & lng exist
            if (!cursor.isNull(
                    cursor.getColumnIndexOrThrow(Util.LATITUDE))
                    &&
                    !cursor.isNull(
                            cursor.getColumnIndexOrThrow(Util.LONGITUDE))) {

                double lat = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(
                                Util.LATITUDE));

                double lng = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(
                                Util.LONGITUDE));

                String itemName =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        Util.ITEM_NAME));

                String postType =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        Util.POST_TYPE));

                LatLng position = new LatLng(lat, lng);

                mMap.addMarker(new MarkerOptions()
                        .position(position)
                        .title(postType + ": " + itemName));
            }
        }
        cursor.close();

        // Marker in melbourne and move the camera
        LatLng melbourne = new LatLng(-37.8136, 144.9631);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(melbourne, 13f));

        FusedLocationProviderClient fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener(location -> {

                        if (location != null) {

                            LatLng userLocation = new LatLng(
                                    location.getLatitude(),
                                    location.getLongitude()
                            );

                            mMap.animateCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                            userLocation,
                                            13f
                                    )
                            );
                        }
                    });
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 1001
            );
        }

    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults);

        if (requestCode == 1001) {

            if (grantResults.length > 0
                    && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {

                // Permission granted
                recreate(); // reload map
            }
        }
    }

}