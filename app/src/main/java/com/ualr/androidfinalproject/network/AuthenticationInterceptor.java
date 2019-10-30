package com.ualr.androidfinalproject.network;

import com.ualr.androidfinalproject.network.session.Session;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {

    private Session session;

    public AuthenticationInterceptor(Session session) {
        this.session = session;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request newRequest  = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + this.session.getAuthToken())
                .build();
        return chain.proceed(newRequest);
    }
}
