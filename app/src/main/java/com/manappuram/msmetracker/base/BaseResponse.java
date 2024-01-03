package com.manappuram.msmetracker.base;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private String result;

    public BaseResponse(String i, String s) {
        status = i;
        result = s;
    }

    public BaseResponse() {
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    @Override
    public String toString() {
        return "BaseResponse{" +
                " status='" + status + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
