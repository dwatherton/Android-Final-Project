package com.ualr.androidfinalproject.network.session;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionDataQuery {
    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}