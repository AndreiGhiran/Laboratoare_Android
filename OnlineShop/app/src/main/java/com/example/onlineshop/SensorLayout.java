package com.example.onlineshop;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SensorLayout extends AppCompatActivity implements SensorEventListener {
    List<Sensor> sensors;
    ArrayList<String> sensorNames;
    HashMap<String, String> sensorMap = new HashMap<String, String>();
    SensorManager manager;
    Sensor mSens;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorNames = new ArrayList<String>();
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensors = manager.getSensorList(Sensor.TYPE_ALL);
        mSens= sensors.get(0);
        Log.d("SensorLayout_onCreate", sensors.get(0).getName());
        for (int i = 0; i < sensors.size(); i++) {
            sensorNames.add(sensors.get(i).getName());
            sensorMap.put(sensorNames.get(i), "Not Read");
        }
        setContentView(R.layout.sensors_layout);
        ListView sensorList = findViewById(R.id.sensorListView);
        ArrayAdapter sensorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sensorNames);
        sensorList.setAdapter(sensorAdapter);
        sensorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView detailsText = findViewById(R.id.sensorTextView);
                String text = sensorNames.get(position) + " value/values: " + sensorMap.get(sensorNames.get(position));
                detailsText.setText(text);
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String text = "";
        for (int i = 0; i < event.values.length; i++) {
            text += String.valueOf(event.values[i]) + " ";
        }
        sensorMap.put(event.sensor.getName(), text);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        for(int i = 0; i< sensors.size();i++){
            manager.registerListener(this, sensors.get(i),SensorManager.SENSOR_DELAY_NORMAL);
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        TextView sensorsText = findViewById(R.id.sensorTextView);
        String finalString = sensorsText.getText().toString();
        savedInstanceState.putString("SensorText", finalString);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        TextView sensorsText = findViewById(R.id.sensorTextView);
        String finalString = savedInstanceState.getString("SensorText");
        sensorsText.setText(finalString);
    }

}