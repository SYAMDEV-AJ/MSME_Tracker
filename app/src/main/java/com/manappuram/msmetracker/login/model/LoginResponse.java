package com.manappuram.msmetracker.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.manappuram.msmetracker.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class LoginResponse extends BaseResponse {

    @SerializedName("EmpDetails")
    @Expose
    public List<LoginResponse.EmpDetails> EmpDetails = null;

    public class EmpDetails implements Serializable {

        @SerializedName("empCode")
        private String empCode;

        @SerializedName("deptId")
        private String deptId;

        @SerializedName("deptName")
        private String deptName;

        @SerializedName("postId")
        private String postId;

        @SerializedName("name")
        private String name;

        @SerializedName("designation")
        private String designation;

        @SerializedName("branch")
        private String branch;

        @SerializedName("branchId")
        private String branchId;

        @SerializedName("joinDate")
        private String joinDate;

        @SerializedName("sessionId")
        private String sessionId;

        @SerializedName("areaId")
        private String areaId;

        @SerializedName("regionId")
        private String regionId;

        @SerializedName("zoneId")
        private String zoneId;


        public String getEmpCode() {
            return empCode;
        }

        public void setEmpCode(String empCode) {
            this.empCode = empCode;
        }

        public String getDeptId() {
            return deptId;
        }

        public void setDeptId(String deptId) {
            this.deptId = deptId;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public String getBranchId() {
            return branchId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public String getJoinDate() {
            return joinDate;
        }

        public void setJoinDate(String joinDate) {
            this.joinDate = joinDate;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getRegionId() {
            return regionId;
        }

        public void setRegionId(String regionId) {
            this.regionId = regionId;
        }

        public String getZoneId() {
            return zoneId;
        }

        public void setZoneId(String zoneId) {
            this.zoneId = zoneId;
        }
    }
}
