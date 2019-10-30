package com.ualr.androidfinalproject.network;

import androidx.annotation.Nullable;

public class ApiServiceHolder {
    WebAPI myService = null;

    @Nullable
    public WebAPI get() {
        return myService;
    }

    public void set(WebAPI myService) {
        this.myService = myService;
    }
}