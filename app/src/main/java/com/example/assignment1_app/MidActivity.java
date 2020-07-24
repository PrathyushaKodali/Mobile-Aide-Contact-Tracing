package com.example.assignment1_app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.assignment1_app.ui.login.ChatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.os.IBinder;
import android.util.Log;
import android.view.View;

import net.sqlcipher.database.SQLiteDatabase;

public class MidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mid);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SQLiteDatabase.loadLibs(this);
//        Intent intent;
//        intent = new Intent(getApplicationContext(), MyLocationService.class);
//        startService(intent);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Move to chat window from here
                Log.d("Button clicked","Inside button click");
                Intent intent;
                intent = new Intent(getApplicationContext(), MyLocationService.class);
                startService(intent);
                Log.d("Button clicked","After service started");
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });



    }





    public static class MyLocationService extends Service {
        private static final String TAG = "GPSLocationService";
        private LocationManager mLocationManager = null;
        private static final int interval = 15*60*1000;
        private static final float distance = 0;
        SQLiteDatabase db = DatabaseHelp.getInstance(this).getWritableDatabase("password");
        public static final String GPS_INFO_TABLE = "gps_info";


        private class LocationListener implements android.location.LocationListener {
            Location LastKnownLocation;

            private void addLatLng(float timestamp, double lat, double lng,SQLiteDatabase database) {
                String latlngInfo = "(" + timestamp + "," + lat + "," + lng + ");";
                String ADD_LATLNG = "INSERT INTO " + GPS_INFO_TABLE + " VALUES" + latlngInfo;
                database.execSQL(ADD_LATLNG);
            }

            public LocationListener(String provider) {
                Log.e(TAG, "LocationListener " + provider);
                LastKnownLocation = new Location(provider);
            }

            @Override
            public void onLocationChanged(Location location) {
                Log.e(TAG, "onLocationChanged: " + location);
                LastKnownLocation.set(location);
                Log.e(TAG, "location longitude " + location.getLongitude() + " location latitude" + location.getLatitude());
                addLatLng(System.currentTimeMillis(),location.getLatitude(),location.getLongitude(),db);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.e(TAG, "onProviderDisabled: " + provider);
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.e(TAG, "onProviderEnabled: " + provider);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.e(TAG, "onStatusChanged: " + provider);
            }
        }

        LocationListener mLocationListeners = new LocationListener(LocationManager.GPS_PROVIDER);

        @Override
        public IBinder onBind(Intent arg0) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.e(TAG, "onStartCommand");
            super.onStartCommand(intent, flags, startId);
            return START_NOT_STICKY;
        }

        @Override
        public void onCreate() {

            Log.e(TAG, "onCreate");

            initializeLocationManager();

            try {
                mLocationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        interval,
                        distance,
                        mLocationListeners
                );
                Log.e(TAG, "request location updates");
            } catch (java.lang.SecurityException ex) {
                Log.i(TAG, "fail to request location update, ignore", ex);
            } catch (IllegalArgumentException ex) {
                Log.d(TAG, "network provider does not exist, " + ex.getMessage());
            }

        }
        @Override
        public void onDestroy() {
            Log.e(TAG, "onDestroy");
            super.onDestroy();
            if (mLocationManager != null) {
                try {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mLocationManager.removeUpdates(mLocationListeners);
                } catch (Exception ex) {
                    Log.i(TAG, "failed", ex);
                }
            }
        }

        private void initializeLocationManager() {
            Log.e(TAG, "initializing Location Manager "+ interval + " distance " + distance);
            if (mLocationManager == null) {
                mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            }
        }
    }
}