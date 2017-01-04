package org.uk.cyberbyte.armaworks;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public SharedPreferences getSettings() {
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }
}
