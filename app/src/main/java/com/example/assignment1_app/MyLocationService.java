package com.example.assignment1_app;


import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.os.IBinder;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.app.Service;
import android.location.Location;


public class MyLocationService extends Service {
    private LocationManager LocationManager = null;
    private static final String TAG = "GPS Location";

    private class LocationListener implements android.location.LocationListener {
        Location LastKnownLocation;

        public LocationListener(String provider) {
            LastKnownLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            LastKnownLocation.set(location);
            Log.e(TAG, "location longitude " + location.getLongitude() + " location latitude" + location.getLatitude());
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    LocationListener Listener = new LocationListener(LocationManager.GPS_PROVIDER);

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {

        initializeLocationManager();

        try {
            LocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    15*60*1000,
                    0,
                    Listener
            );
            Log.e(TAG, "requesting location updates");
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "failed", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "no network provider, " + ex.getMessage());
        }

    }
    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (LocationManager != null) {
            try {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                LocationManager.removeUpdates(Listener);
            } catch (Exception ex) {
                Log.i(TAG, "onDestroy failed", ex);
            }
        }
    }

    private void initializeLocationManager() {
        if (LocationManager == null) {
            LocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}