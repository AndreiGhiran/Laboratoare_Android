package com.example.morsecode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.IOException;

public class decodeActivity extends Activity {

    Button decodeButton;
    EditText inputText;
    TextView decodedText;
    Translator translator;

    Intent intent;
    String action;
    String type;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decode_sms_layout);
        try {
            translator = new Translator(getAssets().open("MorseCode.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        interfaceSetUp();
        intent = getIntent();
        action = intent.getAction();
        type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                decodeReceivedText(intent);
            }
        }
    }

    void interfaceSetUp(){
        decodeButton = (Button) findViewById(R.id.decodeButton);
        inputText = (EditText) findViewById(R.id.codedSMS);
        decodedText = (TextView) findViewById(R.id.decodedSMS);

        decodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = String.valueOf(inputText.getText());
                String decoded = translator.Decode(code);
                decodedText.setText(decoded);
            }
        });
    }

    void decodeReceivedText(Intent intent){
        String code = intent.getStringExtra(Intent.EXTRA_TEXT);
        inputText.setText(code);
        String decoded = translator.Decode(code);
        decodedText.setText(decoded);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("TextToDecode", String.valueOf(inputText.getText()));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        String finalString = savedInstanceState.getString("TextToDecode");
        inputText.setText(finalString);
    }
}
