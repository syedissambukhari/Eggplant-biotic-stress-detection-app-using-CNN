package com.app.droneproject.mvvm.pojos.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegResponse {
    @SerializedName("key")
    @Expose
    String key;
    @SerializedName("success")
    @Expose
    String success;

    public RegResponse() {
    }

    public String getSuccess() {
        return success;
    }

    public String getKey() {
        return key;
    }
}
