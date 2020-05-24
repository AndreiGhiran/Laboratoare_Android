package com.example.morsecode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.IOException;

public class smsActivity extends Activity {

    Button sendButton;
    Button previewButton;
    EditText inputText;
    TextView previewCode;
    Translator translator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_sms_layout);
        try {
            translator = new Translator(getAssets().open("MorseCode.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        interfaceSetUp();
    }

    void interfaceSetUp(){
        sendButton = (Button) findViewById(R.id.sendButon);
        previewButton = (Button) findViewById(R.id.previewButton);
        inputText = (EditText) findViewById(R.id.smsInputText);
        previewCode = (TextView) findViewById(R.id.previewCode);

        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TextToEncode = String.valueOf(inputText.getText());
                String code = translator.Encode(TextToEncode);
                previewCode.setText(code);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TextToEncode = String.valueOf(inputText.getText());
                String code = translator.Encode(TextToEncode);
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, code);
                sendIntent.setType("text/plain");
                String title = "Send the code with";
                Intent chooser = Intent.createChooser(sendIntent, title);
                // Verify that the intent will resolve to an activity
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });
    }
}
