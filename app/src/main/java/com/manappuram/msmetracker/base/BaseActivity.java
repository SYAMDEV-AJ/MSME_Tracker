package com.manappuram.msmetracker.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.manappuram.msmetracker.utility.CustomLoader;

public class BaseActivity extends AppCompatActivity {

    public Activity mActivity;

    private CustomLoader customLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void showProgress() {
        if (!customLoader.isShowing())
            customLoader.show();
    }

    /**
     * Hide Progress Dialog
     */
    public void hideProgress() {
        if (customLoader.isShowing())
            customLoader.dismiss();
    }
}
