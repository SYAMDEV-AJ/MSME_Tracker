package com.manappuram.msmetracker.base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.manappuram.msmetracker.utility.CustomLoader;

public class BaseActivity extends AppCompatActivity {

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public Activity mActivity;

    public String empCode = "";
    public String deptId = "";
    public String deptName = "";
    public String postId = "";
    public String name = "";
    public String designation = "";
    public String branch = "";
    public String branchId = "";
    public String joinDate = "";
    public String sessionId = "";
    public boolean login = false;
    public String areaId = "";
    public String regionId = "";
    public String zoneId = "";
    private CustomLoader customLoader;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customLoader = new CustomLoader(this);

        sharedPreferences = getSharedPreferences("msme-app", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        empCode = sharedPreferences.getString("empCode", "");
        sessionId = sharedPreferences.getString("sessionId", "");
        name = sharedPreferences.getString("name", "");
        postId = sharedPreferences.getString("postId", "");
        branchId = sharedPreferences.getString("brId", "");
        deptId = sharedPreferences.getString("departId", "");
        areaId = sharedPreferences.getString("areaId", "");
        regionId = sharedPreferences.getString("regionId", "");
        zoneId = sharedPreferences.getString("zoneId", "");
        login = sharedPreferences.getBoolean("login", false);
        designation = sharedPreferences.getString("designation", "");
        deptName = sharedPreferences.getString("deptName", "");
        branch = sharedPreferences.getString("branch", "");


    }


    public void showProgress() {
        if (!customLoader.isShowing())
            customLoader.show();
    }

    /**
     * Hide Progress Dialog
     */
    public void hideProgress() {
        if (customLoader.isShowing())
            customLoader.dismiss();
    }
}
