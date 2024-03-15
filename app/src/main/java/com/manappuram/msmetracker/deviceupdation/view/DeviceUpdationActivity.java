package com.manappuram.msmetracker.deviceupdation.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.manappuram.msmetracker.R;
import com.manappuram.msmetracker.base.BaseActivity;
import com.manappuram.msmetracker.databinding.ActivityDeviceUpdationBinding;
import com.manappuram.msmetracker.deviceupdation.response.DeviceDeletionResponse;
import com.manappuram.msmetracker.deviceupdation.response.DeviceRegistrationResponse;
import com.manappuram.msmetracker.deviceupdation.response.DeviceUpdationResponse;
import com.manappuram.msmetracker.deviceupdation.response.DeviceVerificationResponse;
import com.manappuram.msmetracker.utility.Utility;
import com.manappuram.msmetracker.viewmodel.LoginViewmodel;

public class DeviceUpdationActivity extends BaseActivity {
    ActivityDeviceUpdationBinding binding;
    LoginViewmodel viewmodel;

    AlertDialog dialog;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_device_updation);
        viewmodel = ViewModelProviders.of(this).get(LoginViewmodel.class);
        mActivity = this;
        devicereglayoutclick();
        registerbtnclick();
        deviceverify();
        deviceupdationclick();
        devicedeletionclick();
        observerdata();
        binding.backbuttonclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
    }

    private void observerdata() {
        viewmodel.getDeviceRegistrationResponseMutableLiveData().observe(this, new Observer<DeviceRegistrationResponse>() {
            @Override
            public void onChanged(DeviceRegistrationResponse deviceRegistrationResponse) {
                hideProgress();
                if (deviceRegistrationResponse.getStatus().equals("111")) {
                    binding.devicereglayout.setVisibility(View.GONE);
                    binding.deviceidreg.setVisibility(View.VISIBLE);
                    showAlertDialog(deviceRegistrationResponse.getResult());
                    binding.regEmpcode.setText("");
                    binding.regDeviceid.setText("");
                    binding.regEnteredEmpcode.setText("");
                } else {
                    binding.devicereglayout.setVisibility(View.GONE);
                    binding.deviceidreg.setVisibility(View.VISIBLE);
                    binding.regEmpcode.setText("");
                    binding.regDeviceid.setText("");
                    binding.regEnteredEmpcode.setText("");
                    showAlertDialog(deviceRegistrationResponse.getResult());
                }
            }
        });
        viewmodel.getDeviceVerificationResponseMutableLiveData().observe(this, new Observer<DeviceVerificationResponse>() {
            @Override
            public void onChanged(DeviceVerificationResponse deviceVerificationResponse) {
                hideProgress();
                if (deviceVerificationResponse.getStatus().equals("111")) {
                    binding.verifyDeviceid.setText(deviceVerificationResponse.getDeviceid());
                    binding.verifyEnteredEmpcode.setText(deviceVerificationResponse.getEnter_by());
                    binding.updatedDate.setText(deviceVerificationResponse.getLast_upt_dt());

                } else {
                    showAlertDialog(deviceVerificationResponse.getResult());
                    binding.deviceverifylayout.setVisibility(View.GONE);
                    binding.deviceverify.setVisibility(View.VISIBLE);
                }
            }
        });
        viewmodel.getDeviceUpdationResponseMutableLiveData().observe(this, new Observer<DeviceUpdationResponse>() {
            @Override
            public void onChanged(DeviceUpdationResponse deviceUpdationResponse) {
                hideProgress();
                if (deviceUpdationResponse.getStatus().equals("111")) {
                    binding.deviceupdationlayout.setVisibility(View.GONE);
                    binding.deviceupdation.setVisibility(View.VISIBLE);
                    showAlertDialog(deviceUpdationResponse.getResult());
                    binding.updationEmpcode.setText("");
                    binding.updationDeviceid.setText("");
                    binding.updationEnteredEmpcode.setText("");
                } else {
                    showAlertDialog(deviceUpdationResponse.getResult());
                    binding.deviceupdationlayout.setVisibility(View.GONE);
                    binding.deviceupdation.setVisibility(View.VISIBLE);

                }
            }
        });
        viewmodel.getDeviceDeletionResponseMutableLiveData().observe(this, new Observer<DeviceDeletionResponse>() {
            @Override
            public void onChanged(DeviceDeletionResponse deviceDeletionResponse) {
                hideProgress();
                if (deviceDeletionResponse.getStatus().equals("111")) {
                    binding.devicedeletionlayout.setVisibility(View.GONE);
                    binding.devicedeletion.setVisibility(View.VISIBLE);
                    showAlertDialog(deviceDeletionResponse.getResult());
                    binding.deletionEmpcode.setText("");
                } else {
                    binding.devicedeletionlayout.setVisibility(View.GONE);
                    binding.devicedeletion.setVisibility(View.VISIBLE);
                    showAlertDialog(deviceDeletionResponse.getResult());

                }

            }
        });
    }

    private void registerbtnclick() {
        binding.registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.regEmpcode.getText().toString().equals("")) {
                    Toast.makeText(mActivity, "Please Enter Employee Code", Toast.LENGTH_SHORT).show();
                } else if (binding.regDeviceid.getText().toString().equals("")) {
                    Toast.makeText(mActivity, "Please Enter Device Id", Toast.LENGTH_SHORT).show();
                } else if (binding.regEnteredEmpcode.getText().toString().equals("")) {
                    Toast.makeText(mActivity, "Please Enter Entered Employee Code", Toast.LENGTH_SHORT).show();
                } else {
                    String deviceid = binding.regDeviceid.getText().toString();
                    String data = Utility.encodecusid(sessionId + "$" + binding.regEmpcode.getText().toString() + "~" + deviceid + "~" + binding.regEnteredEmpcode.getText().toString());
                    String encoded = data.replaceAll("\\s", "");
                    showProgress();
                    viewmodel.Msme_Deviceid_Insertion(encoded);
                }

            }
        });

    }

    private void deviceverify() {
        binding.verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.verifyEmpcode.getText().toString().equals("")) {
                    Toast.makeText(mActivity, "Please Enter Employee Code", Toast.LENGTH_SHORT).show();
                } else {
                    String data = Utility.encodecusid(sessionId + "$" + binding.verifyEmpcode.getText().toString());
                    String encoded = data.replaceAll("\\s", "");
                    showProgress();
                    viewmodel.deviceid_verify(encoded);
                }
            }
        });
    }

    private void deviceupdationclick() {
        binding.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.updationEmpcode.getText().toString().equals("")) {
                    Toast.makeText(mActivity, "Please Enter Employee Code", Toast.LENGTH_SHORT).show();
                } else if (binding.updationDeviceid.getText().toString().equals("")) {
                    Toast.makeText(mActivity, "Please Enter Device Id", Toast.LENGTH_SHORT).show();
                } else if (binding.updationEnteredEmpcode.getText().toString().equals("")) {
                    Toast.makeText(mActivity, "Please Enter Entered Employee Code", Toast.LENGTH_SHORT).show();
                } else {
                    String deviceid = binding.updationDeviceid.getText().toString();
                    String data = Utility.encodecusid(sessionId + "$" + deviceid + "~" + empCode + "~" + binding.updationEmpcode.getText().toString());
                    String encoded = data.replaceAll("\\s", "");
                    showProgress();
                    viewmodel.Msme_Deviceid_Updation(encoded);
                    Log.i(encoded, "updation");
                }

            }
        });
    }

    private void devicedeletionclick() {
        binding.deletionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.deletionEmpcode.getText().toString().equals("")) {
                    Toast.makeText(mActivity, "Please Enter Employeecode", Toast.LENGTH_SHORT).show();
                } else {
                    String data = Utility.encodecusid(sessionId + "$" + binding.deletionEmpcode.getText().toString());
                    String encoded = data.replaceAll("\\s", "");
                    showProgress();
                    viewmodel.Msme_Deviceid_delete(encoded);
                }
            }
        });
    }


    private void devicereglayoutclick() {
        binding.deviceidreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.devicereglayout.setVisibility(View.VISIBLE);
                binding.deviceidreg.setVisibility(View.GONE);
                binding.regEnteredEmpcode.setText(empCode);
                binding.regEnteredEmpcode.setEnabled(false);

            }
        });
        binding.layoutclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.deviceidreg.setVisibility(View.VISIBLE);
                binding.devicereglayout.setVisibility(View.GONE);
            }
        });
        binding.deviceverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.deviceverifylayout.setVisibility(View.VISIBLE);
                binding.deviceverify.setVisibility(View.GONE);
            }
        });
        binding.layoutclickverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.deviceverify.setVisibility(View.VISIBLE);
                binding.deviceverifylayout.setVisibility(View.GONE);
            }
        });
        binding.deviceupdation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.deviceupdationlayout.setVisibility(View.VISIBLE);
                binding.deviceupdation.setVisibility(View.GONE);
                binding.updationEnteredEmpcode.setText(empCode);
                binding.updationEnteredEmpcode.setEnabled(false);
            }
        });
        binding.layoutclickupdation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.deviceupdation.setVisibility(View.VISIBLE);
                binding.deviceupdationlayout.setVisibility(View.GONE);
            }
        });
        binding.devicedeletion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.devicedeletionlayout.setVisibility(View.VISIBLE);
                binding.devicedeletion.setVisibility(View.GONE);
            }
        });
        binding.layoutclickdeletion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.devicedeletion.setVisibility(View.VISIBLE);
                binding.devicedeletionlayout.setVisibility(View.GONE);
            }
        });
    }

    public void showAlertDialog(String s) {
        new androidx.appcompat.app.AlertDialog.Builder(mActivity)
                .setTitle("Message")
                .setMessage(s)
                .setCancelable(false)
                .setPositiveButton("ok", (dialog, id) -> {
                    dialog.dismiss();

                })
                .show();
    }


}
