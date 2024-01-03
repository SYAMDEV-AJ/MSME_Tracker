package com.manappuram.msmetracker.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.manappuram.msmetracker.R;
import com.manappuram.msmetracker.base.BaseActivity;
import com.manappuram.msmetracker.dashboard.DashboardActivity;
import com.manappuram.msmetracker.databinding.ActivityLoginBinding;
import com.manappuram.msmetracker.login.model.LoginResponse;
import com.manappuram.msmetracker.network.ConnectionLiveData;
import com.manappuram.msmetracker.utility.Connectivity;
import com.manappuram.msmetracker.utility.Utility;
import com.manappuram.msmetracker.viewmodel.LoginViewmodel;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding;
    LoginViewmodel viewmodel;

    String flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewmodel = ViewModelProviders.of(this).get(LoginViewmodel.class);
//        mActivity = this;

        ShowHidePass();
        checkInternetConnectivity();
        Login();

//        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
//                startActivity(intent);
//
//            }
//        });
    }

    private void Login() {

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                if (binding.employeeid.getText().toString().equals("") & binding.password.getText().toString().equals("") & branchid.equals("")) {
//                    Toast.makeText(mActivity, "Please Enter Valid Credentials", Toast.LENGTH_SHORT).show();
//                } else {
                if (flag.equals("1")) {
                    String deviceId = Settings.Secure.getString(LoginActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);

                    String empcode = binding.employeeid.getText().toString();
                    String password = Utility.encodecusid(binding.password.getText().toString());
                    String spaceremoved = password.replaceAll("\\s", "");
                    Log.i("dddd", spaceremoved);

                    showProgress();
                    viewmodel.userLogin(empcode, spaceremoved, "", deviceId);
                    viewmodel.getLoginResponseMutableLiveData().observe(LoginActivity.this, new Observer<LoginResponse>() {
                        @Override
                        public void onChanged(LoginResponse loginResponse) {
                            hideProgress();
                            if (loginResponse.getStatus().equals("111")) {

                                Toast.makeText(LoginActivity.this, loginResponse.getResult(), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(LoginActivity.this, loginResponse.getResult(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                } else if (flag.equals("2")) {
                    Utility.showSnackbar(binding.getRoot(), "No Internet Connection");


                }

            }


        });


    }


    public void ShowHidePass() {
        binding.showpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int inputtype = binding.password.getInputType();

                if (binding.password.getInputType() != (InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    binding.showpassword.setBackground(getResources().getDrawable(R.drawable.ic_showhide_password));
                    binding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    binding.password.setSelection(binding.password.getText().toString().length());
                } else {
                    binding.password.setTransformationMethod(null);
                    binding.password.setInputType(InputType.TYPE_CLASS_TEXT);
                    binding.showpassword.setBackground(getResources().getDrawable(R.drawable.ic_show_password));
                    binding.password.setSelection(binding.password.getText().toString().length());
                }
            }
        });
    }

    private void checkInternetConnectivity() {
        if (Connectivity.isConnected(this)) {

            flag = "1";

        } else {
            flag = "2";
            Utility.showSnackbar(binding.getRoot(), "No Internet Connection");
            initInternetListener();
        }
    }

    private void initInternetListener() {
        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getApplicationContext());
        connectionLiveData.observe(this, connectionModel -> {
            if (connectionModel.getIsConnected()) {
                Utility.showSnackbar(binding.getRoot(), "Connected");

                flag = "1";


            } else {
                flag = "2";
                Utility.showSnackbar(binding.getRoot(), "No Internet Connection");
            }
        });
    }


}