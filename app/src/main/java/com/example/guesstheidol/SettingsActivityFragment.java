package com.example.guesstheidol;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Set;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsActivityFragment extends PreferenceFragment
implements SharedPreferences.OnSharedPreferenceChangeListener{


    @SuppressLint("NewApi")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager.getDefaultSharedPreferences(getContext()).registerOnSharedPreferenceChangeListener(this);

    }

    @SuppressLint("NewApi")
    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getContext()).unregisterOnSharedPreferenceChangeListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i("SettingsActivityFrag", "on shared preferences changed called" + key);
        switch(key){
            case "choicesSelection":
                break;

            case "groupsSelection":
                Set<String> groups = sharedPreferences.getStringSet("groupsSelection", null);
                if(groups == null){
                    return;
                }
                if(groups.size() == 0){
                    Toast.makeText(getContext(), "You must select at lease one group." +
                            "\nDefaulting to ASTRO.", Toast.LENGTH_LONG).show();
                    String defaultGroup = "ASTRO";
                    groups.add(defaultGroup);
                    sharedPreferences.edit().putStringSet("groupsSelection", groups).apply();
                    MultiSelectListPreference pref = (MultiSelectListPreference) findPreference("groupsSelection");
                    pref.setValues(groups);
                }
                break;
        }

    }
}
