package com.example.morsecode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView morseCodeView;
    EditText inputText;
    Button encodeButton;
    Button decodeButton;
    private CameraManager mCameraManager;
    private String mCameraId;
    private boolean hasFlash;
    Camera.Parameters params;
    Translator translator;
    boolean flashing;
    int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        morseCodeView = (TextView) findViewById(R.id.MorceCodeView);
        inputText =  (EditText) findViewById(R.id.text_input);
        encodeButton = (Button) findViewById(R.id.encodeButton);
        decodeButton = (Button) findViewById(R.id.decodeButton);
        //flash_check();
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
        encodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToEncode = String.valueOf(inputText.getText());
                String code = translator.Encode(textToEncode);
                morseCodeView.setText(code);
                long time = 0, unit = 500;
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
                            time += 2 * unit;
                            break;
                        case ' ':
                            time += 2 * unit;
                            break;
                    }
                }
            }

        });
        decodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToEncode = String.valueOf(inputText.getText());
                morseCodeView.setText(translator.Decode(textToEncode));
            }
        });
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
}
