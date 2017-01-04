package org.uk.cyberbyte.armaworks;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.uk.cyberbyte.armaworks.Config.SharedConfig;

public class BaseActivity extends AppCompatActivity {

    protected static final String TAG = "ArmaWorks";

    protected FirebaseAnalytics mFirebaseAnalytics;
    protected FirebaseRemoteConfig mFirebaseRemoteConfig;
    protected FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpRemoteConfig();
        setUpAnalytics();
        setUpAuth();
    }

    protected SharedPreferences getSettings() {
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    private void setUpRemoteConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

        long cacheExpiration = 3600; // 1 hour in seconds.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mFirebaseRemoteConfig.activateFetched();
                        } else {
                            Log.e(TAG, "Failed to fetch remote config");
                        }
                    }
                });
    }

    private void setUpAnalytics() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());
        mFirebaseAnalytics.setUserProperty("server_name", getSettings().getString(SharedConfig.SERVER_NAME, "none"));
    }

    private void setUpAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }
}
