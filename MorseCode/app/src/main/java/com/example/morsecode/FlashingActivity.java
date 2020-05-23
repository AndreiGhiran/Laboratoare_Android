package com.example.morsecode;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class FlashingActivity extends AppCompatActivity {

    TextView codeView;
    EditText inputText;
    Button stopButton;
    Translator translator;
    private CameraManager mCameraManager;
    private String mCameraId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText =  (EditText) findViewById(R.id.text_input);
        String textToEncode = String.valueOf(inputText.getText());
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
        setContentView(R.layout.flashing_activity);
        codeView = (TextView) findViewById(R.id.codeView);
        stopButton = (Button) findViewById(R.id.stopButton);
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
}
