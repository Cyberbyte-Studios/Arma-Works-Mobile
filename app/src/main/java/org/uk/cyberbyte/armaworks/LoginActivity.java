package org.uk.cyberbyte.armaworks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;

import org.uk.cyberbyte.armaworks.Api.Server;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            userAlreadySignedIn();
        } else {
            signUserIn();
        }
    }

    private void signUserIn() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setTheme(AuthUI.getDefaultTheme())
                        .setLogo(AuthUI.NO_LOGO) //todo@ add logo :)
                        .setProviders(signInProviders())
                        .setTosUrl("http://bbc.co.uk")
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                        .build(),
                RC_SIGN_IN);
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

        // Sign in canceled
        if (resultCode == RESULT_CANCELED) {
//            showSnackbar(R.string.sign_in_cancelled);
            return;
        }

        // No network
        if (resultCode == ResultCodes.RESULT_NO_NETWORK) {

//            showSnackbar(R.string.no_internet_connection);
            return;
        }
    }

    private void userAlreadySignedIn() {
        if (hasValidServer()) {
            startActivity(new Intent(this, MainActivity.class));
        }
        startActivity(new Intent(this, AddServerActivity.class));
    }

    private boolean hasValidServer() {
        SharedPreferences settings = getSettings();
        return settings.contains(Server.SETTING_NAME) &&
                settings.contains(Server.SETTING_URL) &&
                settings.contains(Server.SETTING_TOKEN);
    }
}