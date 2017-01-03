package org.uk.cyberbyte.armaworks;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.TextView;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.uk.cyberbyte.armaworks.Api.Core.ApiStatus;
import org.uk.cyberbyte.armaworks.Api.Server;
import org.uk.cyberbyte.armaworks.Util.ExceptionAsyncTask;

public class AddServerActivity extends AppCompatActivity {

    private static final String TAG = "AddServer";

    TextView mUrlView;
    TextView mEmailView;
    TextView mPasswordView;

    private View mProgressView;
    private View mAddServerFormView;

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
        Server server = new Server();
        server.setUrl(mUrlView.getText().toString());
        server.setEmail(mEmailView.getText().toString());
        server.setPassword(mPasswordView.getText().toString());
        if (validateServer(server)) {
            showProgress(true);
            checkServer(server);
        }
    }

    private void checkServer(Server server) {
        new GetServerStatusTask().execute(server.getUrl() + "/api/status");
    }

    private void saveServer() {

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

    private class GetServerStatusTask extends ExceptionAsyncTask<String, Void, ApiStatus> {
        @Override
        protected ApiStatus doInBackground() throws Exception {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return restTemplate.getForObject(getParams()[0], ApiStatus.class);
        }

        @Override
        protected void onPostExecute(Exception exception, ApiStatus status) {
            if (exception != null) {
                Log.e(TAG, "Unable to verify server status page:" + exception.getMessage());
                try {
                    throw exception;
                } catch (HttpClientErrorException e) {
                    if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                        mUrlView.setError(getString(R.string.error_url_not_found));
                    }
                } catch (ResourceAccessException e) {
                    mUrlView.setError(getString(R.string.error_url_connect));
                } catch (Exception e) {
                    Log.e(TAG, exception.getMessage());
                    mUrlView.setError(getString(R.string.error_url_invalid));
                } finally {
                    mUrlView.requestFocus();
                    showProgress(false);
                }
            }
            saveServer();
            Log.d(TAG, "Connected to server version: " + status.getVersion());
            showProgress(false);
        }
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
