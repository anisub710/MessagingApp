package edu.uw.ask710.yama;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by Anirudh Subramanyam on 11/1/2017.
 */


public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initializes new settings fragment.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    //custom preference fragment for settings.
    public static class SettingsFragment extends PreferenceFragment {

        public static final String PREFERENCE_KEY = "my_preferences";
        public static final String TAG = "FRAGMENT";
        private boolean defaultVal;
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);

            defaultVal = false;
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            sharedPrefs.getBoolean("pref_auto_reply", false);
             //default value when the app is loaded.
            sharedPrefs.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPrefs, String key) {
                    defaultVal = !defaultVal;
                    sharedPrefs.getBoolean("pref_auto_reply", defaultVal);
                    if(defaultVal == true){
                        Log.v(TAG, "This is right");
                    }
                    //updated notification in messageReceiver.

                }
            });

        }

    }
}
