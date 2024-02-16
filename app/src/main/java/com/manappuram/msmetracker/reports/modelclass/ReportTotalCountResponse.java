package com.manappuram.msmetracker.reports.modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.manappuram.msmetracker.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class ReportTotalCountResponse extends BaseResponse {

    @SerializedName("totalcount")
    @Expose
    public String totalcount;

    @SerializedName("totaltraveldistance")
    @Expose
    public String totaltraveldistance;

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public String getTotaltraveldistance() {
        return totaltraveldistance;
    }

    public void setTotaltraveldistance(String totaltraveldistance) {
        this.totaltraveldistance = totaltraveldistance;
    }
}
