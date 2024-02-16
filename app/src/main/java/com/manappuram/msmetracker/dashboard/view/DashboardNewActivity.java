package com.manappuram.msmetracker.dashboard.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.manappuram.msmetracker.R;
import com.manappuram.msmetracker.base.BaseActivity;
import com.manappuram.msmetracker.databinding.ActivityDashboardnewBinding;
import com.manappuram.msmetracker.reports.view.ReportDashboardActivity;

public class DashboardNewActivity extends BaseActivity {
    ActivityDashboardnewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboardnew);
        mActivity = this;

        binding.reportclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ReportDashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}
