package com.example.morsecode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    Translator translator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        morseCodeView = (TextView) findViewById(R.id.MorceCodeView);
        inputText =  (EditText) findViewById(R.id.text_input);
        encodeButton = (Button) findViewById(R.id.encodeButton);
        decodeButton = (Button) findViewById(R.id.decodeButton);
        try {
            translator = new Translator(getAssets().open("MorseCode.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        encodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToEncode = String.valueOf(inputText.getText());
                morseCodeView.setText(translator.Encode(textToEncode));
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
}
