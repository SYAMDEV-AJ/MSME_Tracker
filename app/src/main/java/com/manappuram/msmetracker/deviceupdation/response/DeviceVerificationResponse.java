package com.manappuram.msmetracker.deviceupdation.response;

import com.google.gson.annotations.SerializedName;
import com.manappuram.msmetracker.base.BaseResponse;

public class DeviceVerificationResponse extends BaseResponse {

    @SerializedName("deviceid")
    private String deviceid;

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
}
