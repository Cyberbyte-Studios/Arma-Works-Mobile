package org.uk.cyberbyte.armaworks;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.uk.cyberbyte.armaworks.Config.SharedConfig;

public class BaseActivity extends AppCompatActivity {

    protected FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setUserProperty("server_name", getSettings().getString(SharedConfig.SERVER_NAME, "none"));
    }

    public SharedPreferences getSettings() {
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }
}
