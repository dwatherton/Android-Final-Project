package com.ualr.androidfinalproject.network;

import com.ualr.androidfinalproject.network.session.Session;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceManager {

    private static WebAPI service;
    private final static String BASE_URL = "https://20de40b1-94fa-4074-9616-748dd189cce7.app.jexia.com/";

    private WebServiceManager() {

    }

    public static WebAPI getService() {
        if (service == null) {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

            ApiServiceHolder apiServiceHolder = new ApiServiceHolder();
            Session session = new Session();
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(new AuthenticationInterceptor(session))
                    .authenticator(new TokenRefreshAuthenticator(apiServiceHolder, session))
                    .addInterceptor(loggingInterceptor)
                    .build();

            service = new retrofit2.Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(WebAPI.class);

            apiServiceHolder.set(service);
        }
        return service;
    }

}