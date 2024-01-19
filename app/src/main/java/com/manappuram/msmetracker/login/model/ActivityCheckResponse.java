package com.manappuram.msmetracker.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.manappuram.msmetracker.base.BaseResponse;

import java.io.Serializable;

public class ActivityCheckResponse extends BaseResponse {


    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
