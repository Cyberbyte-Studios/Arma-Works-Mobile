package org.uk.cyberbyte.armaworks.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import auto.parcelgson.gson.AutoParcelGsonTypeAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static ApiPlug REST_CLIENT;
    private static final String API_URL = "https://arma-works.firebaseapp.com/";

    static {
        setupRestClient();
    }

    private ApiClient() {
    }

    public static ApiPlug get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        //Uncomment these lines below to start logging each request.
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new AutoParcelGsonTypeAdapterFactory()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        //http://stackoverflow.com/questions/34288873/retrofit-2-rxjava-gson-global-deserialization-change-response-type
        //http://www.jsonschema2pojo.org/

        REST_CLIENT = retrofit.create(ApiPlug.class);
    }
}
