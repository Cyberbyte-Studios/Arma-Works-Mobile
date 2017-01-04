package org.uk.cyberbyte.armaworks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.ResultCodes;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.uk.cyberbyte.armaworks.Config.RemoteConfig;
import org.uk.cyberbyte.armaworks.Services.UserService;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (mFirebaseAuth.getCurrentUser() != null) {
            userSignedIn();
        } else {
            signInUser();
        }
    }

    private void signInUser() {
        if (!mFirebaseRemoteConfig.getBoolean(RemoteConfig.ENABLE_SIGN_IN)) {
            return;
        }
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setTheme(AuthUI.getDefaultTheme())
                        .setLogo(AuthUI.NO_LOGO) //todo@ add logo :)
                        .setProviders(signInProviders())
                        .setTosUrl(mFirebaseRemoteConfig.getString(RemoteConfig.TOS_URL))
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                        .build(),
                RC_SIGN_IN);
    }

    private List<AuthUI.IdpConfig> signInProviders() {
        List<AuthUI.IdpConfig> selectedProviders = new ArrayList<>();
        selectedProviders.add(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());
        if (mFirebaseRemoteConfig.getBoolean(RemoteConfig.GOOGLE_AUTH)) {
            selectedProviders.add(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());
        }
        return selectedProviders;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode);
        }
    }

    private void handleSignInResponse(int resultCode) {
        if (resultCode == RESULT_OK) {
            logSignIn();
            UserService.saveAuthUser();
            userSignedIn();
            return;
        }

        if (resultCode == RESULT_CANCELED) {
            Log.e(TAG, getString(R.string.error_sign_in_cancelled));
        } else if (resultCode == ResultCodes.RESULT_NO_NETWORK) {
            Log.e(TAG, getString(R.string.error_no_internet));
        }
    }

    private void logSignIn() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SIGN_UP_METHOD, "Firebase Auth UI");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
    }

    private void userSignedIn() {
        
    }
}