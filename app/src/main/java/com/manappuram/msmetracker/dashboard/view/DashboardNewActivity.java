package com.manappuram.msmetracker.dashboard.view;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.manappuram.msmetracker.DB.DatabaseHandler;
import com.manappuram.msmetracker.DB.LocationClass;
import com.manappuram.msmetracker.R;
import com.manappuram.msmetracker.base.BaseActivity;
import com.manappuram.msmetracker.databinding.ActivityDashboardnewBinding;
import com.manappuram.msmetracker.deviceupdation.view.DeviceUpdationActivity;
import com.manappuram.msmetracker.login.model.ActivityCheckResponse;
import com.manappuram.msmetracker.map.map.LiveTrackingActivity;
import com.manappuram.msmetracker.map.map.RealTimeLocationActivity;
import com.manappuram.msmetracker.map.map.mapsample;
import com.manappuram.msmetracker.receiver.Restarter;
import com.manappuram.msmetracker.receiver.YourService;
import com.manappuram.msmetracker.reports.view.ReportDashboardActivity;
import com.manappuram.msmetracker.utility.Utility;
import com.manappuram.msmetracker.viewmodel.LoginViewmodel;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

public class DashboardNewActivity extends BaseActivity {
    ActivityDashboardnewBinding binding;
    LoginViewmodel viewmodel;
    String reporthide = "";

    Intent mServiceIntent;
    private YourService mYourService;

    private FusedLocationProviderClient fusedLocationProviderClient;
    TextView locationTextView;
    LocationRequest locationRequest;


    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;

    String flag = "1";

    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboardnew);
        viewmodel = ViewModelProviders.of(this).get(LoginViewmodel.class);
        mActivity = this;

        db = new DatabaseHandler(this);

        // db.addLocation(new LocationClass("123", "456"));

        List<LocationClass> locations = db.getAllLocations();

        String latLongString = "";

        for (LocationClass rt : locations) {
            latLongString = latLongString + "#" + rt.get_id() + "^" + rt.get_latitude() + "^" + rt.get_longitude();
            Log.i("Locations: ", "Locations==>" + latLongString);

        }


        reporthide = getIntent().getStringExtra("reporthide");
        assert reporthide != null;
        if (reporthide.equals("reporthide")) {
            binding.reportclick.setVisibility(View.GONE);
            binding.activityclick.setVisibility(View.VISIBLE);
            binding.deviceupdation.setVisibility(View.GONE);
        } else if (reporthide.equals("none")) {
            binding.reportclick.setVisibility(View.GONE);
            binding.activityclick.setVisibility(View.GONE);
        } else {
            binding.reportclick.setVisibility(View.VISIBLE);
            binding.activityclick.setVisibility(View.GONE);
        }
        if (empCode.equals("53805") || empCode.equals("48762") || empCode.equals("13077") || empCode.equals("409357") || empCode.equals("380692") || empCode.equals("383184") || empCode.equals("68327") || empCode.equals("57047") || empCode.equals("385590") || empCode.equals("382400")) {
            binding.reportclick.setVisibility(View.GONE);
            binding.activityclick.setVisibility(View.GONE);
            binding.deviceupdation.setVisibility(View.VISIBLE);
        } else if (empCode.equals("43425") || empCode.equals("13645") || empCode.equals("10527") || empCode.equals("68807")) {
            binding.reportclick.setVisibility(View.VISIBLE);
            binding.activityclick.setVisibility(View.GONE);
            binding.deviceupdation.setVisibility(View.VISIBLE);
        }


        mYourService = new YourService();
        mServiceIntent = new Intent(this, mYourService.getClass());
        activityclick();
        reportclick();
        deviceupdationclick();
        observer();
        binding.reportclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = "1";

                if (!isMyServiceRunning(mYourService.getClass())) {
                    mServiceIntent.putExtra("stopaction", "startaction");
                    startService(mServiceIntent);


                }


            }
        });
        binding.deviceupdation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    flag = "0";

                    if (isMyServiceRunning(mYourService.getClass())) {
                        Intent service_intent = new Intent(DashboardNewActivity.this, YourService.class);
                        service_intent.setAction("stop");
                        service_intent.putExtra("stopaction", "stopaction");
                        ContextCompat.startForegroundService(DashboardNewActivity.this, service_intent);
                    }
                }
                db.deleteTableData();
                List<LocationClass> routes = db.getAllLocations();

            }
        });
        binding.mapseen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, LiveTrackingActivity.class);
                startActivity(intent);
            }
        });


//        if (!isMyServiceRunning(mYourService.getClass())) {
//            startService(mServiceIntent);
//        }
//        if (isMyServiceRunning(mYourService.getClass())) {
//            stopService(mServiceIntent);
//        }
        binding.titleempname.setText(name);

        requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 1);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {

                Log.i("Service status", "Running");

                return true;
            }
        }
        Log.i("Service status", "Not running");
        return false;
    }

    @Override
    protected void onDestroy() {
        //stopService(mServiceIntent);
        if (flag.equals("1")) {
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("restartservice");
            broadcastIntent.setClass(this, Restarter.class);
            this.sendBroadcast(broadcastIntent);
        } else if (flag.equals("2")) {
            stopService(mServiceIntent);
        }

        super.onDestroy();
    }

    private void deviceupdationclick() {
        binding.deviceupdation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Intent intent = new Intent(mActivity, DeviceUpdationActivity.class);
                Intent intent = new Intent(mActivity, mapsample.class);
                startActivity(intent);
            }
        });
    }

    private void activityclick() {
        binding.activityclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unfinishedactivitychecking();
            }
        });
    }

    private void unfinishedactivitychecking() {
        String data = Utility.encodecusid(sessionId + "$" + empCode);
        assert data != null;
        String encrypted = data.replaceAll("\\s", "");
        showProgress();
        viewmodel.MSME_live_activity(encrypted);
    }

    private void reportclick() {
        binding.reportclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, mapsample.class);
                startActivity(intent);
            }
        });
    }

    private void observer() {
        viewmodel.getActivityCheckResponseMutableLiveData().observe(this, new Observer<ActivityCheckResponse>() {
            @Override
            public void onChanged(ActivityCheckResponse activityCheckResponse) {
                hideProgress();
                if (activityCheckResponse.getStatus().equals("111")) {
                    String unfinishedtask = activityCheckResponse.getResult();
                    String[] data = unfinishedtask.split("~");
                    String value = data[1];
                    String halfimagename = data[2];
                    editor.putString("startlatitude", data[3]);
                    editor.putString("startlogitude", data[4]);
                    editor.apply();

                    Handler handler = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(mActivity, DashboardActivity.class);
                            intent.putExtra("activityname", value);
                            intent.putExtra("halfimagename", halfimagename);
                            intent.putExtra("unfinishedtask", unfinishedtask);
                            startActivity(intent);
                        }
                    };
                    handler.postDelayed(runnable, 100);

                } else {
                    Intent intent = new Intent(mActivity, DashboardActivity.class);
                    intent.putExtra("activityname", "none");
                    intent.putExtra("halfimagename", "none");
                    intent.putExtra("unfinishedtask", "none");
                    startActivity(intent);

                }
            }
        });
    }


}
