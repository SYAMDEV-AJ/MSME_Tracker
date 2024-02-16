package com.manappuram.msmetracker.reports.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.manappuram.msmetracker.R;
import com.manappuram.msmetracker.base.BaseActivity;
import com.manappuram.msmetracker.databinding.ActivityReportDashboardBinding;
import com.manappuram.msmetracker.reports.adapterclass.ActivityAdapter;
import com.manappuram.msmetracker.reports.modelclass.ReportActivityListReponse;
import com.manappuram.msmetracker.reports.modelclass.ReportTotalCountResponse;
import com.manappuram.msmetracker.viewmodel.LoginViewmodel;

import java.util.ArrayList;
import java.util.List;

public class ReportDashboardActivity extends BaseActivity implements ActivityAdapter.Spinnerclick {
    ActivityReportDashboardBinding binding;
    LoginViewmodel viewmodel;
    ActivityAdapter adapter;
    List<ReportActivityListReponse.get_activity_list_data> spinnerlist = new ArrayList<>();
    String activityid = "", activityname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_dashboard);
        viewmodel = ViewModelProviders.of(this).get(LoginViewmodel.class);
        mActivity = this;

        Activityspinner();
        recyclerdata();
        generateclick();


        binding.activityselection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.activityrecycler.setVisibility(View.VISIBLE);
                binding.linelayout.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_from_top);
                animation.setStartOffset(0);
                binding.activityrecycler.startAnimation(animation);
                RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                rotateAnimation.setRepeatCount(0);
                rotateAnimation.setDuration(300);
                rotateAnimation.setFillAfter(true);
                binding.arrow.startAnimation(rotateAnimation);
            }
        });


    }

    private void generateclick() {
        binding.generateclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, ReportDateSelectionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Activityspinner() {
        showProgress();
        viewmodel.Get_activity_listdrop("");
        viewmodel.getReportActivityListReponseMutableLiveData().observe(this, new Observer<ReportActivityListReponse>() {
            @Override
            public void onChanged(ReportActivityListReponse reportActivityListReponse) {
                hideProgress();
                spinnerlist.clear();
                if (reportActivityListReponse.getStatus().equals("111")) {
                    spinnerlist.addAll(reportActivityListReponse.getGet_activity_list_data());
                } else {
                    Toast.makeText(mActivity, reportActivityListReponse.getResult(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void totalcount() {
        viewmodel.gettotalcount_totaltraveldistance("");
        viewmodel.reportTotalCountMutableLiveData.observe(this, new Observer<ReportTotalCountResponse>() {
            @Override
            public void onChanged(ReportTotalCountResponse reportTotalCount) {
                if (reportTotalCount.getStatus().equals("111")) {
                    binding.totalcount.setText(reportTotalCount.getTotalcount());
                    binding.tttraveldistance.setText(reportTotalCount.getTotaltraveldistance());

                } else {
                    Toast.makeText(mActivity, reportTotalCount.getResult(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void recyclerdata() {
        binding.reclceract.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ActivityAdapter(this, spinnerlist);
        binding.reclceract.setAdapter(adapter);
    }


    @Override
    public void Spinnerclick(String id, String name) {
        activityid = id;
        activityname = name;
        totalcount();
        RotateAnimation rotateAnimation = new RotateAnimation(180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true);
        binding.arrow.startAnimation(rotateAnimation);
        binding.activityrecycler.setVisibility(View.GONE);
        binding.linelayout.setVisibility(View.GONE);
        binding.spinnevalue.setText(name);
    }
}
