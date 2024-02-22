package com.manappuram.msmetracker.reports.modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.manappuram.msmetracker.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class BranchDetailsReponse extends BaseResponse {

    @SerializedName("get_activity_list_data")
    @Expose
    public List<BranchDetailsReponse.get_activity_list_data> get_activity_list_data = null;

    public class get_activity_list_data implements Serializable {
        @SerializedName("number_of_activity")
        @Expose
        public String number_of_activity;

        @SerializedName("emp_code")
        @Expose
        public String emp_code;

        @SerializedName("total_distance")
        @Expose
        public String total_distance;

        @SerializedName("emp_name")
        @Expose
        public String emp_name;

        @SerializedName("branch_id")
        @Expose
        public String branch_id;

        @SerializedName("msme_branch")
        @Expose
        public String msme_branch;

        @SerializedName("punched_branch")
        @Expose
        public String punched_branch;

        @SerializedName("punched_branchname")
        @Expose
        public String punched_branchname;

        @SerializedName("state_name")
        @Expose
        public String state_name;

        @SerializedName("mobile_no")
        @Expose
        public String mobile_no;

        public String getNumber_of_activity() {
            return number_of_activity;
        }

        public void setNumber_of_activity(String number_of_activity) {
            this.number_of_activity = number_of_activity;
        }

        public String getEmp_code() {
            return emp_code;
        }

        public void setEmp_code(String emp_code) {
            this.emp_code = emp_code;
        }

        public String getTotal_distance() {
            return total_distance;
        }

        public void setTotal_distance(String total_distance) {
            this.total_distance = total_distance;
        }

        public String getEmp_name() {
            return emp_name;
        }

        public void setEmp_name(String emp_name) {
            this.emp_name = emp_name;
        }

        public String getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
        }

        public String getMsme_branch() {
            return msme_branch;
        }

        public void setMsme_branch(String msme_branch) {
            this.msme_branch = msme_branch;
        }

        public String getPunched_branch() {
            return punched_branch;
        }

        public void setPunched_branch(String punched_branch) {
            this.punched_branch = punched_branch;
        }

        public String getPunched_branchname() {
            return punched_branchname;
        }

        public void setPunched_branchname(String punched_branchname) {
            this.punched_branchname = punched_branchname;
        }

        public String getState_name() {
            return state_name;
        }

        public void setState_name(String state_name) {
            this.state_name = state_name;
        }

        public String getMobile_no() {
            return mobile_no;
        }

        public void setMobile_no(String mobile_no) {
            this.mobile_no = mobile_no;
        }
    }

    public List<BranchDetailsReponse.get_activity_list_data> getGet_activity_list_data() {
        return get_activity_list_data;
    }
}
