package com.manappuram.msmetracker.reports.view;

import android.annotation.SuppressLint;
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
import com.manappuram.msmetracker.databinding.ActivityReportDateSelectionBinding;
import com.manappuram.msmetracker.reports.adapterclass.ActivityAdapter;
import com.manappuram.msmetracker.reports.adapterclass.BranchAdapter;
import com.manappuram.msmetracker.reports.adapterclass.StateAdapter;
import com.manappuram.msmetracker.reports.modelclass.BranchListReponse;
import com.manappuram.msmetracker.reports.modelclass.ReportActivityListReponse;
import com.manappuram.msmetracker.reports.modelclass.StateListReponse;
import com.manappuram.msmetracker.utility.Utility;
import com.manappuram.msmetracker.viewmodel.LoginViewmodel;

import java.util.ArrayList;
import java.util.List;

public class ReportDateSelectionActivity extends BaseActivity {
    ActivityReportDateSelectionBinding binding;
    LoginViewmodel viewmodel;
    StateAdapter adapter;
    BranchAdapter branchAdapter;
    List<StateListReponse.get_activity_list_data> statelist = new ArrayList<>();
    List<BranchListReponse.get_activity_list_data> branchlist = new ArrayList<>();
    String branchidselected = "", statename = "", branchaname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_date_selection);
        viewmodel = ViewModelProviders.of(this).get(LoginViewmodel.class);
        mActivity = this;
        statelistspinner();
        observer();
        recyclerdata();
        recyclerdatabranch();
        datashowclick();

        binding.backbuttonclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });


    }


    private void observer() {
        viewmodel.getStateListReponseMutableLiveData().observe(this, new Observer<StateListReponse>() {
            @Override
            public void onChanged(StateListReponse stateListReponse) {
                statelist.clear();
                if (stateListReponse.getStatus().equals("111")) {
                    statelist.addAll(stateListReponse.getGet_activity_list_data());
                    binding.stateselection.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.staterecyler.setVisibility(View.VISIBLE);
                            binding.linelayout.setVisibility(View.VISIBLE);
                            binding.chatrecyclerlayout.setVisibility(View.VISIBLE);
                            Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_from_top);
                            animation.setStartOffset(0);
                            binding.staterecyler.startAnimation(animation);
                            RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            rotateAnimation.setInterpolator(new DecelerateInterpolator());
                            rotateAnimation.setRepeatCount(0);
                            rotateAnimation.setDuration(300);
                            rotateAnimation.setFillAfter(true);
                            binding.arrow.startAnimation(rotateAnimation);
                        }
                    });

                } else {
                    Toast.makeText(mActivity, stateListReponse.getResult(), Toast.LENGTH_SHORT).show();
                }
                binding.staterecyler.invalidate();
                adapter.notifyDataSetChanged();
            }
        });

        viewmodel.getBranchListReponseMutableLiveData().observe(this, new Observer<BranchListReponse>() {
            @Override
            public void onChanged(BranchListReponse branchListReponse) {
                branchlist.clear();
                if (branchListReponse.getStatus().equals("111")) {
                    branchlist.addAll(branchListReponse.getGet_activity_list_data());

                    binding.branchselection.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.branchreclerlayout.setVisibility(View.VISIBLE);
                            binding.branchreclycer.setVisibility(View.VISIBLE);
                            binding.branchline.setVisibility(View.VISIBLE);
                            Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_from_top);
                            animation.setStartOffset(0);
                            binding.branchreclycer.startAnimation(animation);
                            RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            rotateAnimation.setInterpolator(new DecelerateInterpolator());
                            rotateAnimation.setRepeatCount(0);
                            rotateAnimation.setDuration(300);
                            rotateAnimation.setFillAfter(true);
                            binding.arrowbranch.startAnimation(rotateAnimation);
                        }
                    });
                } else {
                    Toast.makeText(mActivity, branchListReponse.getResult(), Toast.LENGTH_SHORT).show();
                }
                binding.branchreclycer.invalidate();
                branchAdapter.notifyDataSetChanged();
            }

        });

    }

    private void statelistspinner() {
        String data = Utility.encodecusid(sessionId + "$" + "0");
        String encrypted = data.replaceAll("\\s", "");
        viewmodel.Get_All_state(encrypted);

    }

    private void branchlistspinner(String id) {
        String data = Utility.encodecusid(sessionId + "$" + id);
        String encrypted = data.replaceAll("\\s", "");
        viewmodel.Get_All_branch(encrypted);

    }

    private void recyclerdata() {
        adapter = new StateAdapter(this, statelist);
        binding.staterecyler.setAdapter(adapter);
        adapter.listener = new SpinnerOnclickListener() {
            @Override
            public void onClick(String id, String name) {

                statename = name;

                String activityname = name;
                if (id.equals("")) {
                    Toast.makeText(mActivity, "Please Select State", Toast.LENGTH_SHORT).show();
                } else {
                    branchlistspinner(id);

                }
                RotateAnimation rotateAnimation = new RotateAnimation(180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                rotateAnimation.setRepeatCount(0);
                rotateAnimation.setDuration(300);
                rotateAnimation.setFillAfter(true);
                binding.arrow.startAnimation(rotateAnimation);
                binding.chatrecyclerlayout.setVisibility(View.GONE);
                binding.linelayout.setVisibility(View.GONE);
                binding.spinnevalue.setText(activityname);
            }
        };
    }

    private void recyclerdatabranch() {
        binding.branchreclycer.setLayoutManager(new LinearLayoutManager(this));
        branchAdapter = new BranchAdapter(this, branchlist, new BranchAdapter.Spinnerclick() {
            @Override
            public void Spinnerrclick(String id, String name) {
                branchidselected = id;
                branchaname = name;


                RotateAnimation rotateAnimation = new RotateAnimation(180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                rotateAnimation.setRepeatCount(0);
                rotateAnimation.setDuration(300);
                rotateAnimation.setFillAfter(true);
                binding.arrowbranch.startAnimation(rotateAnimation);
                binding.branchreclerlayout.setVisibility(View.GONE);
                binding.branchline.setVisibility(View.GONE);
                binding.spinnevaluebranch.setText(name);

            }
        });
        binding.branchreclycer.setAdapter(branchAdapter);
    }


    private void datashowclick() {
        binding.showbtnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statename.equals("")) {
                    Toast.makeText(mActivity, "Please Select State id", Toast.LENGTH_SHORT).show();

                } else if (branchidselected.equals("")) {
                    Toast.makeText(mActivity, "Please Select Branch id", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(mActivity, ReportDepartmentDtsActivity.class);
                    intent.putExtra("branchidselected", branchidselected);
                    intent.putExtra("statename", statename);
                    intent.putExtra("branchaname", branchaname);
                    startActivity(intent);
                }

            }
        });
    }

    public interface SpinnerOnclickListener {

        void onClick(String id, String name);

    }
}
