package org.uk.cyberbyte.armaworks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.uk.cyberbyte.armaworks.Config.RemoteConfig;
import org.uk.cyberbyte.armaworks.Config.SharedConfig;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    private static final int RC_SIGN_IN = 100;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupRemoteConfig();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            userAlreadySignedIn();
        } else {
            signUserIn();
        }
    }

    private void setupRemoteConfig() {
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

    private void signUserIn() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setTheme(AuthUI.getDefaultTheme())
                        .setLogo(AuthUI.NO_LOGO) //todo@ add logo :)
                        .setProviders(signInProviders())
                        .setTosUrl(mFirebaseRemoteConfig.getString(RemoteConfig.TOS_URL))
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                        .build(),
                RC_SIGN_IN);
        finish();
    }

    private List<AuthUI.IdpConfig> signInProviders() {
        List<AuthUI.IdpConfig> selectedProviders = new ArrayList<>();
        selectedProviders.add(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());
//        selectedProviders.add(new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build());
        selectedProviders.add(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());
//        selectedProviders.add(new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build());
        return selectedProviders;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode, data);
            return;
        }
        // WHOOPS NO THING
    }

    private void handleSignInResponse(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            userAlreadySignedIn();
            return;
        }

        if (resultCode == RESULT_CANCELED) {
            showSnackbar(R.string.error_sign_in_cancelled);
            return;
        }

        if (resultCode == ResultCodes.RESULT_NO_NETWORK) {

            showSnackbar(R.string.error_no_internet);
            return;
        }
        showSnackbar(R.string.error);
    }

    private void userAlreadySignedIn() {
        if (hasValidServer()) {
            startActivity(new Intent(this, MainActivity.class));
        }
        Log.d(TAG, "Server not found redirecting to AddServer");
        startActivity(new Intent(this, AddServerActivity.class));
        finish();
    }

    private boolean hasValidServer() {
        SharedPreferences settings = getSettings();
        return settings.contains(SharedConfig.SERVER_NAME) &&
                settings.contains(SharedConfig.SERVER_URL) &&
                settings.contains(SharedConfig.SERVER_TOKEN);
    }

    private void showSnackbar(@StringRes int errorMessageRes) {
//        Snackbar.make(mRootView, errorMessageRes, Snackbar.LENGTH_LONG).show();
    }
}