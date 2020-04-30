package com.example.morsecode;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Translator  extends AppCompatActivity {
    String[] Characters;
    HashMap<String, String> char_to_Code = new HashMap<String, String>();
    HashMap<String, String> code_to_Char = new HashMap<String, String>();
    InputStream in;
    public Translator(InputStream stream){
        in = stream;
        Initialize();
    }

    void Initialize(){
        String text = "";
        try {
            int size = in.available();
            byte[]buffer = new byte[size];
            in.read(buffer);
            in.close();
            text = new String(buffer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        String[] lines =  text.split("\n");
        String[] line;
        for(int i = 0;  i< lines.length; i++){
            line = lines[i].split(" ");
            code_to_Char.put(line[1].trim(),line[0].trim());
            char_to_Code.put(line[0].trim(),line[1].trim());
        }
    }

    String Encode(String text){
        String code = "";
        for(int i = 0; i<text.length();i++)
        {
            switch (text.charAt(i)){
                case ' ':
                    code += " ";
                    break;
                default:
                    code += char_to_Code.get(String.valueOf(text.charAt(i)).toUpperCase());
            }
            code+="/";
        }
        return code;
    }

    String Decode(String code){
        String text = "";
        return text;
    }
    }
