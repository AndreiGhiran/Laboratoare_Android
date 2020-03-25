package com.example.onlineshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.preference.SwitchPreferenceCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Xml;
import android.widget.CompoundButton;
import android.widget.Switch;


public class SetingsMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Settings");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.preferance_container, new MySettingsPref())
                .commit();
        setContentView(R.layout.activity_setings_menu);
//        Switch not_switch = findViewById(R.id.cash_switch);
//        //Switch cashing = fin
//        not_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                SharedPreferences sharedPref = SetingsMenu.this.getPreferences(Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPref.edit();
//                editor.putBoolean("notifications",isChecked);
//                editor.apply();
//            }
//        });

    }
}
