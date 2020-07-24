package com.example.assignment1_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.assignment1_app.ui.login.ChatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MidActivity extends AppCompatActivity {

    public final String TAG = "MidActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mid);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent;
        intent = new Intent(getApplicationContext(), MyLocationService.class);
        startService(intent);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Move to chat window from here
                Log.d("Button clicked", "Inside button click");
                Intent intent;
                intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });

        final DatabaseHelp databaseHelp = new DatabaseHelp();


        BroadcastReceiver messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Bundle b = intent.getBundleExtra("Location");
                Location location = b.getParcelable("Location");
                if (location != null) {
                    Log.e(TAG,"Starting the database thing");
                    databaseHelp.openDatabase();
                    databaseHelp.addLatLng(System.currentTimeMillis(), location.getLatitude(), location.getLongitude());
                    String tester = databaseHelp.getTableAsString();
                    databaseHelp.close();
                    System.out.println(tester);
                }
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, new IntentFilter("GPSLocationUpdates"));
    }


}
