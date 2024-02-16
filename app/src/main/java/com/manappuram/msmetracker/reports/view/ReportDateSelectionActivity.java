package com.manappuram.msmetracker.reports.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.manappuram.msmetracker.R;
import com.manappuram.msmetracker.base.BaseActivity;
import com.manappuram.msmetracker.databinding.ActivityReportDateSelectionBinding;
import com.manappuram.msmetracker.reports.modelclass.StateListReponse;
import com.manappuram.msmetracker.viewmodel.LoginViewmodel;

public class ReportDateSelectionActivity extends BaseActivity {
    ActivityReportDateSelectionBinding binding;
    LoginViewmodel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_date_selection);
        viewmodel = ViewModelProviders.of(this).get(LoginViewmodel.class);
        mActivity = this;
        statelistspinner();
    }

    private void statelistspinner() {
        viewmodel.Get_All_state("");
        viewmodel.getStateListReponseMutableLiveData().observe(this, new Observer<StateListReponse>() {
            @Override
            public void onChanged(StateListReponse stateListReponse) {
                if (stateListReponse.getStatus().equals("111")){

                }else {
                    Toast.makeText(mActivity, stateListReponse.getResult() , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
