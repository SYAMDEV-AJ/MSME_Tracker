package com.manappuram.msmetracker.reports.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.manappuram.msmetracker.databinding.ActivityReportBranchDetailsBinding;
import com.manappuram.msmetracker.databinding.ActivityReportDeptDetailsBinding;
import com.manappuram.msmetracker.reports.adapterclass.BranchDetailsAdapter;
import com.manappuram.msmetracker.reports.adapterclass.DepartmentAdapter;
import com.manappuram.msmetracker.reports.adapterclass.StateAdapter;
import com.manappuram.msmetracker.reports.adapterclass.StatusAdapter;
import com.manappuram.msmetracker.reports.modelclass.BranchDetailsReponse;
import com.manappuram.msmetracker.reports.modelclass.StatusmodelClass;
import com.manappuram.msmetracker.utility.Utility;
import com.manappuram.msmetracker.viewmodel.LoginViewmodel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportBranchDtsActivity extends BaseActivity {
    ActivityReportBranchDetailsBinding binding;
    BranchDetailsAdapter adapter;
    StatusAdapter statusAdapter;
    LoginViewmodel viewmodel;
    List<StatusmodelClass> StatusSpinnerList = new ArrayList<>();
    List<BranchDetailsReponse.get_activity_list_data> branchlist = new ArrayList<>();
    Date c = Calendar.getInstance().getTime();

    SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    String formattedDate = date.format(c);

    String selectedbranchid = "", selecteddepartment = "", selecteddate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_branch_details);
        viewmodel = ViewModelProviders.of(this).get(LoginViewmodel.class);
        mActivity = this;
        selectedbranchid = getIntent().getStringExtra("selectedbranchid");
        selecteddepartment = getIntent().getStringExtra("depatmentid");
        selecteddate = getIntent().getStringExtra("selecteddate");
        selecteddate = getIntent().getStringExtra("selecteddate");
        StatusSpinner();
        recylerdept();
        observer();
        search();
        firstselection();
        binding.stateselection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.chatrecyclerlayout.setVisibility(View.VISIBLE);
                binding.chatrecycler.setVisibility(View.VISIBLE);
                binding.linelayout.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_from_top);
                animation.setStartOffset(0);
                binding.chatrecycler.startAnimation(animation);
                RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                rotateAnimation.setRepeatCount(0);
                rotateAnimation.setDuration(300);
                rotateAnimation.setFillAfter(true);
                binding.arrow.startAnimation(rotateAnimation);
            }
        });
        binding.backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
    }

    private void firstselection() {

        String data = Utility.encodecusid(sessionId + "$" + "1" + "~" + selecteddate + "~" + selecteddepartment + "~" + selectedbranchid);
        String encrypted = data.replaceAll("\\s", "");
        showProgress();
        viewmodel.getmovementwise(encrypted);
        StatusSpinnerList.clear();
        StatusSpinnerList.add(new StatusmodelClass("Not Moved"));
        statusAdapter.notifyDataSetChanged();

    }

    private void observer() {
        viewmodel.getBranchDetailsReponseMutableLiveData().observe(this, new Observer<BranchDetailsReponse>() {
            @Override
            public void onChanged(BranchDetailsReponse branchDetailsReponse) {
                hideProgress();
                branchlist.clear();
                if (branchDetailsReponse.getStatus().equals("111")) {
                    branchlist.addAll(branchDetailsReponse.getGet_activity_list_data());
                } else {
                    Toast.makeText(mActivity, branchDetailsReponse.getResult(), Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void StatusSpinner() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.chatrecycler.setLayoutManager(layoutManager);
        statusAdapter = new StatusAdapter(mActivity, StatusSpinnerList, new StatusAdapter.Spinnerclick() {
            @Override
            public void Spinnerrclick(String name) {
                if (name.equals("Moved")) {
                    StatusSpinnerList.clear();
                    StatusSpinnerList.add(new StatusmodelClass("Not Moved"));
                    statusAdapter.notifyDataSetChanged();
                    binding.search.setEnabled(true);
                    String data = Utility.encodecusid(sessionId + "$" + "1" + "~" + selecteddate + "~" + selecteddepartment + "~" + selectedbranchid);
                    String encrypted = data.replaceAll("\\s", "");
                    showProgress();
                    viewmodel.getmovementwise(encrypted);
                } else {
                    StatusSpinnerList.clear();
                    StatusSpinnerList.add(new StatusmodelClass("Moved"));
                    statusAdapter.notifyDataSetChanged();
                    binding.search.setEnabled(true);
                    String data = Utility.encodecusid(sessionId + "$" + "2" + "~" + selecteddate + "~" + selecteddepartment + "~" + selectedbranchid);
                    String encrypted = data.replaceAll("\\s", "");
                    showProgress();
                    viewmodel.getmovementwise(encrypted);
                }
                RotateAnimation rotateAnimation = new RotateAnimation(180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                rotateAnimation.setRepeatCount(0);
                rotateAnimation.setDuration(300);
                rotateAnimation.setFillAfter(true);
                binding.arrow.startAnimation(rotateAnimation);
                binding.chatrecyclerlayout.setVisibility(View.GONE);
                binding.linelayout.setVisibility(View.GONE);
                binding.spinnevalue.setText(name);
            }
        });
        binding.chatrecycler.setAdapter(statusAdapter);
        statusAdapter.notifyDataSetChanged();

    }


    private void recylerdept() {
        binding.branchdetialsrecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BranchDetailsAdapter(this, branchlist, new BranchDetailsAdapter.Spinnerclick() {
            @Override
            public void spinnerclick(String id) {
                String phonenumber = id;
                Uri number = Uri.parse("tel:" + phonenumber);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });
        binding.branchdetialsrecycler.setAdapter(adapter);
    }

    private void search() {
        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.search.getText().toString().length() > 0) {
                    filter(binding.search.getText().toString());
                } else {

                    filter("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filter(String toString) {
        ArrayList<BranchDetailsReponse.get_activity_list_data> newList = new ArrayList<>();
        for (BranchDetailsReponse.get_activity_list_data data : branchlist) {
            if (data.getEmp_code().toLowerCase().contains(toString.toLowerCase())) {
                newList.add(data);
            }
        }
        adapter.branchlist = newList;
        adapter.notifyDataSetChanged();
    }
}
