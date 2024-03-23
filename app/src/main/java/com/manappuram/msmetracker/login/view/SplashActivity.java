package com.manappuram.msmetracker.login.view;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.manappuram.msmetracker.BuildConfig;
import com.manappuram.msmetracker.R;
import com.manappuram.msmetracker.base.BaseActivity;
import com.manappuram.msmetracker.databinding.ActivitySplashBinding;

public class SplashActivity extends BaseActivity {

    ActivitySplashBinding binding;
    private static int TIME_OUT = 4000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        FirebaseApp.initializeApp(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);

    }

    private void checkFirebaseData() {
        FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            Log.d(TAG, "Config params updated: " + updated);

                            // Get the values from Remote Config
                            String versionname = firebaseRemoteConfig.getString("version_name");
                            String mapKey = firebaseRemoteConfig.getString("map_key");
                            int versioncode = (int) firebaseRemoteConfig.getLong("version_code");
                            boolean forceupdate = firebaseRemoteConfig.getBoolean("force_update");
                            editor.putString("mapKey", mapKey);
                            editor.apply();

                            if (versioncode > BuildConfig.VERSION_CODE) {

                                if (forceupdate) {

                                }

//                                Toast.makeText(SplashActivity.this, "Update available", Toast.LENGTH_SHORT).show();
                            }
                            Log.i("mapKey", mapKey);

                        } else {
                            Log.e(TAG, "Fetch failed");
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkFirebaseData();
    }
}