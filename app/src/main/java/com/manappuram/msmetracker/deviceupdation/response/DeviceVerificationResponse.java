package com.manappuram.msmetracker.deviceupdation.response;

import com.google.gson.annotations.SerializedName;
import com.manappuram.msmetracker.base.BaseResponse;

public class DeviceVerificationResponse extends BaseResponse {

    @SerializedName("enter_by")
    private String enter_by;
    @SerializedName("deviceid")
    private String deviceid;

    @SerializedName("last_upt_dt")
    private String last_upt_dt;

    public String getEnter_by() {
        return enter_by;
    }

    public void setEnter_by(String enter_by) {
        this.enter_by = enter_by;
    }

    public String getLast_upt_dt() {
        return last_upt_dt;
    }

    public void setLast_upt_dt(String last_upt_dt) {
        this.last_upt_dt = last_upt_dt;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
}
