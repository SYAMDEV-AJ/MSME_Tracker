package com.manappuram.msmetracker.login.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.manappuram.msmetracker.dashboard.view.DashboardActivity;
import com.manappuram.msmetracker.databinding.ActivityLoginBinding;
import com.manappuram.msmetracker.login.model.ActivityCheckResponse;
import com.manappuram.msmetracker.login.model.LoginResponse;
import com.manappuram.msmetracker.network.ConnectionLiveData;
import com.manappuram.msmetracker.utility.Connectivity;
import com.manappuram.msmetracker.utility.Utility;
import com.manappuram.msmetracker.viewmodel.LoginViewmodel;

import java.util.Arrays;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding;
    LoginViewmodel viewmodel;
    String flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewmodel = ViewModelProviders.of(this).get(LoginViewmodel.class);
        mActivity = this;
        ShowHidePass();
        checkInternetConnectivity();
        Login();
        loginobserver();

        binding.deviceidcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deviceId = Settings.Secure.getString(mActivity.getContentResolver(), Settings.Secure.ANDROID_ID);
                ClipboardManager clipboard = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("deviceId", deviceId);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mActivity, "Copied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginobserver() {


        viewmodel.getLoginResponseMutableLiveData().observe(LoginActivity.this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                hideProgress();
                if (loginResponse.getStatus().equals("111")) {
                    editor.putString("empCode", loginResponse.getEmpDetails().getEmpCode());
                    editor.putString("designation", loginResponse.getEmpDetails().getDesignation());
                    editor.putString("postId", loginResponse.getEmpDetails().getPostId());
                    editor.putString("sessionId", loginResponse.getEmpDetails().getSessionId() + ":" + loginResponse.getEmpDetails().getEmpCode());
                    editor.putString("name", loginResponse.getEmpDetails().getName());
                    editor.putString("brId", loginResponse.getEmpDetails().getBranchId());
                    editor.putString("departId", loginResponse.getEmpDetails().getDeptId());
                    editor.putString("areaId", loginResponse.getEmpDetails().getAreaId());
                    editor.putString("regionId", loginResponse.getEmpDetails().getRegionId());
                    editor.putString("zoneId", loginResponse.getEmpDetails().getZoneId());
                    editor.putString("deptName", loginResponse.getEmpDetails().getDeptName());
                    editor.putString("branch", loginResponse.getEmpDetails().getBranch());
                    editor.putBoolean("login", false);
                    String logindate = Utility.getTodayDate();
                    editor.putString("logindate", logindate);
                    editor.apply();

                    String data = Utility.encodecusid(loginResponse.getEmpDetails().getSessionId() + ":" + loginResponse.getEmpDetails().getEmpCode() + "$" + loginResponse.getEmpDetails().getEmpCode());
                    String encripted = data.replaceAll("\\s", "");

                    if (loginResponse.getEmpDetails().getDeptId().equals("617") && !loginResponse.getEmpDetails().getBranchId().equals("0")) {
                        showProgress();
                        viewmodel.MSME_live_activity(encripted);
                    } else {
                        Toast.makeText(mActivity, "You are Not Authorized to this Application", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(mActivity, loginResponse.getResult(), Toast.LENGTH_SHORT).show();

                }
            }
        });

        viewmodel.getActivityCheckResponseMutableLiveData().observe(this, new Observer<ActivityCheckResponse>() {
            @Override
            public void onChanged(ActivityCheckResponse activityCheckResponse) {
                hideProgress();
                if (activityCheckResponse.getStatus().equals("111")) {
                    String unfinishedtask = activityCheckResponse.getResult();
                    String[] data = activityCheckResponse.getResult().split("~");
                    String value = data[1];
                    String halfimagename = data[2];
                    editor.putString("startlatitude", data[3]);
                    editor.putString("startlogitude", data[4]);
                    editor.apply();

                    Handler handler = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(mActivity, DashboardActivity.class);
                            intent.putExtra("activityname", value);
                            intent.putExtra("halfimagename", halfimagename);
                            intent.putExtra("unfinishedtask", unfinishedtask);
                            startActivity(intent);
                        }
                    };
                    handler.postDelayed(runnable, 100);

                } else {
                    Intent intent = new Intent(mActivity, DashboardActivity.class);
                    intent.putExtra("activityname", "none");
                    intent.putExtra("halfimagename", "none");
                    intent.putExtra("unfinishedtask", "none");
                    startActivity(intent);

                }

            }
        });
    }

    private void Login() {

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (binding.employeeid.getText().toString().equals("") & binding.password.getText().toString().equals("")) {
//                    Toast.makeText(mActivity, "Please Enter Valid Credentials", Toast.LENGTH_SHORT).show();
//                } else {
                if (flag.equals("1")) {
                    String deviceId = Settings.Secure.getString(LoginActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                    String empcode = binding.employeeid.getText().toString();
                    String password = Utility.encodecusid(binding.password.getText().toString());
                    String spaceremoved = password.replaceAll("\\s", "");
                    Log.i("dddd", spaceremoved);
                    showProgress();
                    viewmodel.userLogin("407068", "wqv/NG39+Z6pAzqGwpsjlw==", "", deviceId);
                    //viewmodel.userLogin(empcode, spaceremoved, "", deviceId);


                } else if (flag.equals("2")) {
                    Utility.showSnackbar(binding.getRoot(), "No Internet Connection");


                }

                //}


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