package com.example.morsecode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText inputText;
    Button encodeButton;
    TextView codeView;
    Button sosButton;
    private CameraManager mCameraManager;
    private String mCameraId;
    private boolean hasFlash;
    Translator translator;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flash_check();
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        try {
            translator = new Translator(getAssets().open("MorseCode.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        interfaceSetUp();
    }

    void interfaceSetUp(){
        inputText =  (EditText) findViewById(R.id.text_input);
        encodeButton = (Button) findViewById(R.id.encodeButton);
        sosButton = (Button) findViewById((R.id.sosButton));

        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = String.valueOf(inputText.getText());
                String textToEncode = "SOS";
                flashing(textToEncode);
            }
        });

        encodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToEncode = String.valueOf(inputText.getText());
                flashing(textToEncode);
            }

        });
    }

    void flashing(String textToEncode){
        setContentView(R.layout.flashing_activity);
        codeView = (TextView) findViewById(R.id.codeView);
        String code = translator.Encode(textToEncode);
        final String[] leters = code.split("/");
        int j = 1;
        long time = 0, unit = 500;
        codeView.setText(leters[j]);
        for (int i = 1; i < code.length()-1; i++){
            switch(code.charAt(i)){
                case '.':
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            flashOn();
                        }
                    }, time);
                    time += unit;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            flashOff();
                        }
                    }, time);
                    time += unit;
                    break;
                case '-':
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            flashOn();
                        }
                    }, time);
                    time += 3 * unit;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            flashOff();
                        }
                    }, time);
                    time += unit;
                    break;
                case '/':
                    j ++;
                    if(j!=leters.length) {
                        final int finalJ = j;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                codeView.setText(leters[finalJ]);
                            }
                        }, time);
                    }
                    time += 2 * unit;
                    break;
                case ' ':
                    time += 2 * unit;
                    break;
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_main);
                interfaceSetUp();
                inputText.setText(text);
            }
        }, time);
    }

    void flashOn(){
        try {
            mCameraManager.setTorchMode(mCameraId, true);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    void flashOff(){
        try {
            mCameraManager.setTorchMode(mCameraId, false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void flash_check(){
        hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash) {
            // device doesn't support flash
            // Show alert message and close the application
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                    .create();
            alert.setTitle("Error");
            alert.setMessage("Sorry, your device doesn't support flash light!");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                    //finish();
                }
            });
            alert.show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("MainActivity_Settings", "s-a apelat onSaveInstanceState");
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("TextToEncode", String.valueOf(inputText.getText()));
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        String finalString = savedInstanceState.getString("TextToEncode");
        inputText.setText(finalString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
//                Log.d("MainActivity_Settings", "a fost apasat Settings");
//                Intent intent = new Intent(this, SetingsMenu.class);
//                startActivity(intent);
                return true;
            case R.id.sendSMS:
                Intent smsIntent = new Intent(this, smsActivity.class);
                startActivity(smsIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
