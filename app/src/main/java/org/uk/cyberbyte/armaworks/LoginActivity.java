package org.uk.cyberbyte.armaworks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.uk.cyberbyte.armaworks.Activities.ArmaLife.ArmaLifeActivity;
import org.uk.cyberbyte.armaworks.Activities.BaseActivity;
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
            IdpResponse response = IdpResponse.fromResultIntent(data);
            handleSignInResponse(resultCode, response);
        }
    }

    private void handleSignInResponse(int resultCode, IdpResponse response) {
        // Successfully signed in
        if (resultCode == ResultCodes.OK) {
            logSignIn();
            UserService.saveAuthUser();
            userSignedIn();
            finish();
            return;
        } else {
            // Sign in failed
            if (response == null) {
                // User pressed back button
                showSnackbar(R.string.sign_in_cancelled);
                return;
            }

            if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                showSnackbar(R.string.no_internet_connection);
                return;
            }

            if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                showSnackbar(R.string.unknown_error);
                return;
            }
        }
        showSnackbar(R.string.unknown_sign_in_response);
    }

    private void logSignIn() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SIGN_UP_METHOD, "Firebase Auth UI");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
    }

    private void userSignedIn() {
        startActivity(new Intent(LoginActivity.this, ArmaLifeActivity.class));
        finish();
    }
}