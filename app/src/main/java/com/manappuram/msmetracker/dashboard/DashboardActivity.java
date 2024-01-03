package com.manappuram.msmetracker.dashboard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

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

        mActivity = this;

        startbtnclick();
        startbtnenablecheck();

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

    private void startbtnenablecheck() {
        binding.remarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.remarks.getText().toString().equals("")) {
                    binding.startbtnenable.setVisibility(View.VISIBLE);
                    binding.startbtndisable.setVisibility(View.GONE);
                } else {
                    binding.startbtnenable.setVisibility(View.GONE);
                    binding.startbtndisable.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void startbtnclick() {
        binding.startbtnenable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.remarks.getText().toString().equals("")) {
                    binding.remarktoast.setVisibility(View.VISIBLE);
                } else {
                    binding.remarktoast.setVisibility(View.GONE);

                }
            }
        });
    }



}
