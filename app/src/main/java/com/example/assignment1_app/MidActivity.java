package com.example.assignment1_app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.assignment1_app.ui.login.ChatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MidActivity extends AppCompatActivity {

    public final String TAG = "MidActivity";

    @RequiresApi(api = Build.VERSION_CODES.R)
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

//        Double[] coords = {};
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(new InputStreamReader(new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"coords.txt")));
//            Double tempcoord = Double.valueOf(reader.readLine());
//            while(null != tempcoord){
//                Double tempcoord2 = Double.valueOf(reader.readLine());
//            }
//            coords = {tempcoord,tempcoord2};
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



//        BroadcastReceiver messageReceiver = new BroadcastReceiver() {
//            @RequiresApi(api = Build.VERSION_CODES.R)
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                Bundle b = intent.getBundleExtra("Location");
//                Location location = b.getParcelable("Location");
//                if (location != null) {
//                    Log.e(TAG,"Starting the database thing");
//                    databaseHelp.openDatabase();
//                    databaseHelp.addLatLng(System.currentTimeMillis(), location.getLatitude(), location.getLongitude());
//                    String tester = databaseHelp.getTableAsString();
//                    databaseHelp.close();
//                    System.out.println(tester);
//                }
//            }
//        };
//
//        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, new IntentFilter("GPSLocationUpdates"));
    }


}
