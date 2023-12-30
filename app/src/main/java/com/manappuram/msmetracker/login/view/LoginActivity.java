package com.manappuram.msmetracker.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.manappuram.msmetracker.R;
import com.manappuram.msmetracker.base.BaseActivity;
import com.manappuram.msmetracker.dashboard.DashboardActivity;
import com.manappuram.msmetracker.databinding.ActivityLoginBinding;
import com.manappuram.msmetracker.utility.Utility;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding;

    String flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);

            }
        });
    }

    private void Login() {

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                if (binding.employeeid.getText().toString().equals("") & binding.password.getText().toString().equals("") & branchid.equals("")) {
//                    Toast.makeText(mActivity, "Please Enter Valid Credentials", Toast.LENGTH_SHORT).show();
//                } else {
                if (flag.equals("1")) {


                } else if (flag.equals("2")) {
                    Utility.showSnackbar(binding.getRoot(), "No Internet Connection");


                }

            }


        });


    }

}