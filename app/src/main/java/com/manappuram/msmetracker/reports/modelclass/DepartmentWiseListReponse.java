package com.manappuram.msmetracker.reports.modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.manappuram.msmetracker.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class DepartmentWiseListReponse extends BaseResponse {

    @SerializedName("get_activity_list_data")
    @Expose
    public List<DepartmentWiseListReponse.get_activity_list_data> get_activity_list_data = null;

    public class get_activity_list_data implements Serializable {
        @SerializedName("department_name")
        @Expose
        public String department_name;

        @SerializedName("dep_id")
        @Expose
        public String dep_id;

        @SerializedName("total_count")
        @Expose
        public String total_count;

        @SerializedName("present")
        @Expose
        public String present;

        @SerializedName("absent")
        @Expose
        public String absent;

        @SerializedName("moved")
        @Expose
        public String moved;
        @SerializedName("not_moved")
        @Expose
        public String not_moved;

        public String getDepartment_name() {
            return department_name;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }

        public String getDep_id() {
            return dep_id;
        }

        public void setDep_id(String dep_id) {
            this.dep_id = dep_id;
        }

        public String getTotal_count() {
            return total_count;
        }

        public void setTotal_count(String total_count) {
            this.total_count = total_count;
        }

        public String getPresent() {
            return present;
        }

        public void setPresent(String present) {
            this.present = present;
        }

        public String getAbsent() {
            return absent;
        }

        public void setAbsent(String absent) {
            this.absent = absent;
        }

        public String getMoved() {
            return moved;
        }

        public void setMoved(String moved) {
            this.moved = moved;
        }

        public String getNot_moved() {
            return not_moved;
        }

        public void setNot_moved(String not_moved) {
            this.not_moved = not_moved;
        }
    }

    public List<DepartmentWiseListReponse.get_activity_list_data> getGet_activity_list_data() {
        return get_activity_list_data;
    }
}
