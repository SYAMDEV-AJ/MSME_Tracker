package com.manappuram.msmetracker.dashboard.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.manappuram.msmetracker.R;
import com.manappuram.msmetracker.base.BaseActivity;
import com.manappuram.msmetracker.databinding.ActivityDashboardnewBinding;
import com.manappuram.msmetracker.login.model.ActivityCheckResponse;
import com.manappuram.msmetracker.reports.view.ReportDashboardActivity;
import com.manappuram.msmetracker.utility.Utility;
import com.manappuram.msmetracker.viewmodel.LoginViewmodel;

public class DashboardNewActivity extends BaseActivity {
    ActivityDashboardnewBinding binding;
    LoginViewmodel viewmodel;
    String reporthide = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboardnew);
        viewmodel = ViewModelProviders.of(this).get(LoginViewmodel.class);
        mActivity = this;
        reporthide = getIntent().getStringExtra("reporthide");
        assert reporthide != null;
        if (reporthide.equals("reporthide")) {
            binding.reportclick.setVisibility(View.GONE);
            binding.activityclick.setVisibility(View.VISIBLE);

        } else {
            binding.reportclick.setVisibility(View.VISIBLE);
            binding.activityclick.setVisibility(View.GONE);
        }

        activityclick();
        reportclick();
        observer();
        binding.titleempname.setText(name);

    }

    private void activityclick() {
        binding.activityclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unfinishedactivitychecking();
            }
        });
    }

    private void unfinishedactivitychecking() {
        String data = Utility.encodecusid(sessionId + "$" + empCode);
        assert data != null;
        String encrypted = data.replaceAll("\\s", "");
        showProgress();
        viewmodel.MSME_live_activity(encrypted);
    }

    private void reportclick() {
        binding.reportclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ReportDashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    private void observer() {
        viewmodel.getActivityCheckResponseMutableLiveData().observe(this, new Observer<ActivityCheckResponse>() {
            @Override
            public void onChanged(ActivityCheckResponse activityCheckResponse) {
                hideProgress();
                if (activityCheckResponse.getStatus().equals("111")) {
                    String unfinishedtask = activityCheckResponse.getResult();
                    String[] data = unfinishedtask.split("~");
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
}
