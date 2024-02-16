package com.manappuram.msmetracker.reports.modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.manappuram.msmetracker.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class StateListReponse extends BaseResponse {

    @SerializedName("get_activity_list_data")
    @Expose
    public List<StateListReponse.get_activity_list_data> get_activity_list_data = null;

    public class get_activity_list_data implements Serializable {
        @SerializedName("stateid")
        @Expose
        public String stateid;

        @SerializedName("statename")
        @Expose
        public String statename;

        public String getStateid() {
            return stateid;
        }

        public void setStateid(String stateid) {
            this.stateid = stateid;
        }

        public String getStatename() {
            return statename;
        }

        public void setStatename(String statename) {
            this.statename = statename;
        }
    }

    public List<StateListReponse.get_activity_list_data> getGet_activity_list_data() {
        return get_activity_list_data;
    }
}
