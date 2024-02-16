package com.manappuram.msmetracker.reports.view;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.manappuram.msmetracker.R;
import com.manappuram.msmetracker.base.BaseActivity;
import com.manappuram.msmetracker.databinding.ActivityReportDeptDetailsBinding;

public class ReportDepartmentDtsActivity extends BaseActivity {
    ActivityReportDeptDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_dept_details);
        mActivity = this;
    }
}
