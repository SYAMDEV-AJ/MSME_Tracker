package com.manappuram.msmetracker.reports.modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.manappuram.msmetracker.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class BranchListReponse extends BaseResponse {

    @SerializedName("get_activity_list_data")
    @Expose
    public List<BranchListReponse.get_activity_list_data> get_activity_list_data = null;

    public class get_activity_list_data implements Serializable {
        @SerializedName("branchid")
        @Expose
        public String branchid;

        @SerializedName("branchname")
        @Expose
        public String branchname;

        public String getBranchid() {
            return branchid;
        }

        public void setBranchid(String branchid) {
            this.branchid = branchid;
        }

        public String getBranchname() {
            return branchname;
        }

        public void setBranchname(String branchname) {
            this.branchname = branchname;
        }
    }

    public List<BranchListReponse.get_activity_list_data> getGet_activity_list_data() {
        return get_activity_list_data;
    }
}
