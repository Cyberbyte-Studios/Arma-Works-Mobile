package org.uk.cyberbyte.armaworks.Api;

import android.preference.PreferenceManager;

import com.loopj.android.http.*;

public class ArmaLifeRestClient {
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return getServerUrl() + relativeUrl;
    }

    private static String getServerUrl() {
        return "http://localhost:8000";
//        return PreferenceManager.getDefaultSharedPreferences();
    }
}
