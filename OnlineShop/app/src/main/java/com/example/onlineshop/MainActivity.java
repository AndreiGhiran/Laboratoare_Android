package com.example.onlineshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

import static android.location.LocationManager.GPS_PROVIDER;

public class MainActivity extends AppCompatActivity {

    String[] myStrings = {"item1","item2","item3"};
    HashMap<String, String> itemMap = new HashMap<String, String>();
    int pos;
    Button cameraButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.d("MainActivity_onCreate", "s-a apelat onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraButton = (Button) findViewById(R.id.cameraButton);

        for (int i = 0; i < myStrings.length; i++) {
            itemMap.put(myStrings[i], myStrings[i] + " details");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myStrings);
        ListView view = findViewById(R.id.listView);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                TextView detailsText = findViewById(R.id.textView);
                String finalString = myStrings[position] + ": " + itemMap.get(myStrings[position]);
                detailsText.setText(finalString);
            }
        });
        cameraButton.setOnClickListener(cameraButtonListener);
    }

    View.OnClickListener cameraButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openCameraActivity();
        }
    };

    private void openCameraActivity() {
        Intent cameraIntent = new Intent(this,camera_activity.class);
        startActivity(cameraIntent);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("MainActivity_Settings", "s-a apelat onSaveInstanceState");
        super.onSaveInstanceState(savedInstanceState);
        TextView detailsText = findViewById(R.id.textView);
        String finalString = detailsText.getText().toString();
        savedInstanceState.putString("Description", finalString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Log.d("MainActivity_Settings", "a fost apasat Settings");
                Intent intent = new Intent(this, SetingsMenu.class);
                startActivity(intent);
                return true;
            case R.id.share:
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                String textMessage = myStrings[pos];
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage);
                sendIntent.setType("text/plain");
                String title = "Share this item with";
                Intent chooser = Intent.createChooser(sendIntent, title);
                // Verify that the intent will resolve to an activity
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
                return true;
            case R.id.dialog:
                DialogCeva dialog = new DialogCeva();
                dialog.show(getSupportFragmentManager(), "dialog");
                return true;
            case R.id.sensors:
                Intent sensor_intent = new Intent(this, SensorLayout.class);
                startActivity(sensor_intent);
                return true;
            case R.id.gps_location:
                Intent location_intent = new Intent(this, GPS_Location_Dialog.class);
                startActivity(location_intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        //super.onSaveInstanceState(savedInstanceState);
        TextView detailsText = findViewById(R.id.textView);
        String finalString = savedInstanceState.getString("Description");
        detailsText.setText(finalString);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity_onStart","S-a apelat onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();

        Log.d("MainActivity_onResume","s-a apelat onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("MainActivity_onPause","s-a apelat onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d("MainActivity_onStop","s-a apelat onStop");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("MainActivity_onRestart","s-a apelat onRestart");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        Log.d("MainActivity_onDestroy","s-a apelat onDestroy");
    }
}
