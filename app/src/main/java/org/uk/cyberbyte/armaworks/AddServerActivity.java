package org.uk.cyberbyte.armaworks;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.uk.cyberbyte.armaworks.Api.Models.Core.ApiStatus;
import org.uk.cyberbyte.armaworks.Api.Server;
import org.uk.cyberbyte.armaworks.Config.SharedConfig;

import cz.msebera.android.httpclient.Header;

public class AddServerActivity extends BaseActivity {

    private static final String TAG = "AddServer";

    private TextView mUrlView;
    private TextView mEmailView;
    private TextView mPasswordView;

    private View mProgressView;
    private View mAddServerFormView;

    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_server);

        mUrlView = (TextView) findViewById(R.id.serverUrl);
        mEmailView = (TextView) findViewById(R.id.serverEmail);
        mPasswordView = (TextView) findViewById(R.id.serverPassword);

        mAddServerFormView = findViewById(R.id.add_server_form);
        mProgressView = findViewById(R.id.add_server_progress);

        TextView addServerButton = (TextView) findViewById(R.id.add_server_button);

        addServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addServerClicked();
            }
        });
    }

    private void addServerClicked() {
        server = new Server();
        server.setUrl(mUrlView.getText().toString());
        server.setEmail(mEmailView.getText().toString());
        server.setPassword(mPasswordView.getText().toString());
        if (validateServer(server)) {
            showProgress(true);
            checkServer(server);
        }
    }

    private void checkServer(final Server server) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(server.getUrl() + "/api/status", new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                try {
                    Gson gson = new GsonBuilder().create();
                    ApiStatus status = gson.fromJson(response, ApiStatus.class);
                    Log.d(TAG, "Connected to server version: " + status.getVersion());
                    server.setName(status.getName());
                    server.setToken(getServerToken());

                    saveServer();
                    showProgress(false);
                    redirectHome();
                } catch (JsonSyntaxException e) {
                    Log.e(TAG, "Request to "+this.getRequestURI()+" failed to convert to JSON");
                    urlInvalid(getString(R.string.error_url_invalid));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
                Log.e(TAG, "Request to "+this.getRequestURI()+" failed with message: "+e.getMessage());
                urlInvalid(getString(R.string.error_url_invalid));
            }
        });
    }

    private void redirectHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void urlInvalid(String message) {
        mUrlView.setError(message);
        mUrlView.requestFocus();
        showProgress(false);
    }

    private void saveServer() {
        SharedPreferences preferences = getSettings();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SharedConfig.SERVER_NAME, server.getName());
        editor.putString(SharedConfig.SERVER_URL, server.getUrl());
        editor.putString(SharedConfig.SERVER_TOKEN, server.getToken());
        Log.d(TAG, "Saved Server "+server.getName()+"("+server.getUrl()+") with token " + server.getToken());
        editor.apply();
    }

    private String getServerToken() {
        return "TOKEN";
    }

    private boolean validateServer(Server server) {
        clearErrors();

        View requestFocus = null;
        if (server.getEmail().isEmpty() || !server.getEmail().contains("@")) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            requestFocus = mEmailView;
        }

        if (server.getUrl().isEmpty() || !URLUtil.isValidUrl(server.getUrl())) {
            mUrlView.setError(getString(R.string.error_url_invalid));
            requestFocus = mUrlView;
        }

        if (server.getPassword().isEmpty()) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            requestFocus = mPasswordView;
        }

        if (requestFocus != null) {
            requestFocus.requestFocus();
            return false;
        }
        return true;
    }

    private void clearErrors() {
        mEmailView.setError(null);
        mUrlView.setError(null);
        mPasswordView.setError(null);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mAddServerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mAddServerFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAddServerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mAddServerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
