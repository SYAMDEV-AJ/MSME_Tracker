package com.manappuram.msmetracker.dashboard.modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.manappuram.msmetracker.base.BaseResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivitylistResponse extends BaseResponse {

    @SerializedName("get_activity_list_data")
    @Expose
    public List<ActivitylistResponse.get_activity_list_data> get_activity_list_data = null;


    public class get_activity_list_data implements Serializable {

        @SerializedName("activity_id")
        @Expose
        private String activity_id;

        @SerializedName("activity_name")
        @Expose
        private String activity_name;

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public String getActivity_name() {
            return activity_name;
        }

        public void setActivity_name(String activity_name) {
            this.activity_name = activity_name;
        }
    }

    public List<ActivitylistResponse.get_activity_list_data> getGet_activity_list_data() {
        return get_activity_list_data;
    }

}
