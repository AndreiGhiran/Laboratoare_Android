package com.example.onlineshop;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class MySettingsPref extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
