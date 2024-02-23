package com.manappuram.msmetracker.reports.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.manappuram.msmetracker.R;
import com.manappuram.msmetracker.base.BaseActivity;
import com.manappuram.msmetracker.databinding.ActivityReportDeptDetailsBinding;
import com.manappuram.msmetracker.reports.adapterclass.BranchAdapter;
import com.manappuram.msmetracker.reports.adapterclass.DepartmentAdapter;
import com.manappuram.msmetracker.reports.modelclass.DepartmentWiseListReponse;
import com.manappuram.msmetracker.utility.Utility;
import com.manappuram.msmetracker.viewmodel.LoginViewmodel;

import java.util.ArrayList;
import java.util.List;

public class ReportDepartmentDtsActivity extends BaseActivity {
    ActivityReportDeptDetailsBinding binding;
    LoginViewmodel viewmodel;
    List<DepartmentWiseListReponse.get_activity_list_data> departmentlist = new ArrayList<>();
    DepartmentAdapter adapter;
    String selectedbranchid = "", branchaname = "", statename = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_dept_details);
        viewmodel = ViewModelProviders.of(this).get(LoginViewmodel.class);
        mActivity = this;
        selectedbranchid = getIntent().getStringExtra("branchidselected");
        branchaname = getIntent().getStringExtra("branchaname");
        statename = getIntent().getStringExtra("statename");
        departmentdatashow();
        observer();
        recylerdept();
        changebtnclick();
        binding.statename.setText(statename);
        binding.branchaname.setText(branchaname);
    }

    private void changebtnclick() {
        binding.changeclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ReportDateSelectionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void departmentdatashow() {
        String data = Utility.encodecusid(sessionId + "$" + selectedbranchid);
        String encrypted = data.replaceAll("\\s", "");
        showProgress();
        viewmodel.getdepartmentwise(encrypted);

    }

    private void observer() {
        viewmodel.getDepartmentWiseListReponseMutableLiveData().observe(this, new Observer<DepartmentWiseListReponse>() {
            @Override
            public void onChanged(DepartmentWiseListReponse departmentWiseListReponse) {
                hideProgress();
                departmentlist.clear();
                if (departmentWiseListReponse.getStatus().equals("111")) {
                    departmentlist.addAll(departmentWiseListReponse.getGet_activity_list_data());
                } else {
                    Toast.makeText(mActivity, departmentWiseListReponse.getResult(), Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void recylerdept() {
        binding.deptrecyler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DepartmentAdapter(this, departmentlist, new DepartmentAdapter.Spinnerclick() {
            @Override
            public void Spinnerrclick(String id) {
                String deptid = id;
                Intent intent = new Intent(mActivity, ReportBranchDtsActivity.class);
                intent.putExtra("depatmentid", deptid);
                intent.putExtra("selectedbranchid", selectedbranchid);
                startActivity(intent);
            }
        });
        binding.deptrecyler.setAdapter(adapter);
    }

}
