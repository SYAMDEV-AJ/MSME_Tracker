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
import com.manappuram.msmetracker.base.BaseResponse;
import com.manappuram.msmetracker.base.Event;
import com.manappuram.msmetracker.databinding.ActivityReportDashboardBinding;
import com.manappuram.msmetracker.reports.adapterclass.ActivityAdapter;
import com.manappuram.msmetracker.reports.modelclass.ReportActivityListReponse;
import com.manappuram.msmetracker.reports.modelclass.ReportTotalCountResponse;
import com.manappuram.msmetracker.utility.Utility;
import com.manappuram.msmetracker.viewmodel.LoginViewmodel;

import java.util.ArrayList;
import java.util.List;

public class ReportDashboardActivity extends BaseActivity {
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
        observer();


        binding.backbuttonclick.setOnClickListener(v -> {
            mActivity.finish();
        });


    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        // dist = Math.round(dist * 60 * 1.60934);
        dist = dist * 60 * 1.60934;
        // double dist1 = dist / 1000;
        // int dist1 = (int) dist;
        //dist = dist * 60 * 1.60934;

        double decimalDegrees = dist;
        String finalDist = String.valueOf(dist);
        return (dist);
    }


//    public double getDistanceBetweenLocations(double lat1, double lon1, double lat2, double lon2) {
//        LatLng firstloc = new LatLng(lat1, lon1);
//        LatLng secloc = new LatLng(lat2, lon2);
//        double distance = SphericalUtil.computeDistanceBetween(firstloc, secloc);
//        String data = String.valueOf(distance / 1000);
//        String data1 = data;
//        return distance;
//    }

    // Usage


    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private void observer() {

        viewmodel.getReportTotalCountMutableLiveData().observe(this, new Observer<ReportTotalCountResponse>() {
            @Override
            public void onChanged(ReportTotalCountResponse reportTotalCount) {
                hideProgress();
                if (reportTotalCount.getStatus().equals("111")) {

                    binding.totalcount.setText(reportTotalCount.getTotalcount());
                    binding.tttraveldistance.setText(reportTotalCount.getTotaltraveldistance());

                } else {
                    Toast.makeText(mActivity, reportTotalCount.getResult(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewmodel.getReportActivityListReponseMutableLiveData().observe(this, new Observer<ReportActivityListReponse>() {
            @Override
            public void onChanged(ReportActivityListReponse reportActivityListReponse) {
                hideProgress();
                spinnerlist.clear();
                if (reportActivityListReponse.getStatus().equals("111")) {
                    spinnerlist.addAll(reportActivityListReponse.getGet_activity_list_data());

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
                } else {
                    Toast.makeText(mActivity, reportActivityListReponse.getResult(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewmodel.loginRepository.getErrorsMutable().observe(this, new Observer<Event<BaseResponse>>() {
            @Override
            public void onChanged(Event<BaseResponse> baseResponseEvent) {
                if (baseResponseEvent != null) {
                    hideProgress();
                    Toast.makeText(mActivity, "Internal Server Occurred", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    private void Activityspinner() {
        String data = Utility.encodecusid(sessionId + "$" + "0");
        assert data != null;
        String encypted = data.replaceAll("\\s", "");
        showProgress();
        viewmodel.Get_activity_listdrop(encypted);


    }

    private void totalcountall(String id) {
        String data = Utility.encodecusid(sessionId + "$" + id);
        String enrypted = data.replaceAll("\\s", "");
        showProgress();
        viewmodel.gettotalcount_totaltraveldistance(enrypted);


    }

    private void totalcountactivity(String id) {
        String data = Utility.encodecusid(sessionId + "$" + id);
        String enrypted = data.replaceAll("\\s", "");
        showProgress();
        viewmodel.getactivitytotalcount_totaltraveldistance(enrypted);


    }


    private void recyclerdata() {
        binding.reclceract.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ActivityAdapter(this, spinnerlist, new ActivityAdapter.Spinnerclick() {
            @Override
            public void Spinnerrclick(String id, String name) {
                activityid = id;
                activityname = name;
                if (activityid.equals("0")) {
                    totalcountall(id);
                    binding.ttcount.setText("Total Daily Activity\n Count");
                    binding.ttdistance.setText("Total Activity\n Travel KM");

                } else {
                    totalcountactivity(id);
                    binding.ttcount.setText("Total Activity Count");
                    binding.ttdistance.setText("Total Activity\n Travel KM");
                }

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
        });
        binding.reclceract.setAdapter(adapter);
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
}
