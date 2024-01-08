package com.manappuram.msmetracker.dashboard.modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.manappuram.msmetracker.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class StartServiceResponse extends BaseResponse {


    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
