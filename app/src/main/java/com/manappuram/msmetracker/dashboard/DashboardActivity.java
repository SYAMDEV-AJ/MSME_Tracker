package com.manappuram.msmetracker.dashboard;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;

import androidx.databinding.DataBindingUtil;


import com.manappuram.msmetracker.R;
import com.manappuram.msmetracker.base.BaseActivity;
import com.manappuram.msmetracker.databinding.ActivityDashboardBinding;


public class DashboardActivity extends BaseActivity {

    ActivityDashboardBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        binding.activityselection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int visibilty = binding.chatrecycler.getVisibility();
                if (visibilty == View.VISIBLE) {
                    RotateAnimation rotateAnimation = new RotateAnimation(180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setInterpolator(new DecelerateInterpolator());
                    rotateAnimation.setRepeatCount(0);
                    rotateAnimation.setDuration(300);
                    rotateAnimation.setFillAfter(true);
                    binding.arrow.startAnimation(rotateAnimation);
                    binding.chatrecycler.setVisibility(View.GONE);

                } else {
                    RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setInterpolator(new DecelerateInterpolator());
                    rotateAnimation.setRepeatCount(0);
                    rotateAnimation.setDuration(300);
                    rotateAnimation.setFillAfter(true);
                    binding.arrow.startAnimation(rotateAnimation);
                    binding.chatrecycler.setVisibility(View.VISIBLE);
                }


            }
        });

    }


}
