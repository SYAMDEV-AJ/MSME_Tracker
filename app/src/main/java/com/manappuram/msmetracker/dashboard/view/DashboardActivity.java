package com.manappuram.msmetracker.dashboard.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jsibbold.zoomage.ZoomageView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.manappuram.msmetracker.R;
import com.manappuram.msmetracker.base.BaseActivity;
import com.manappuram.msmetracker.dashboard.adapter.AdapterSpinner;
import com.manappuram.msmetracker.dashboard.modelclass.ActivitylistResponse;
import com.manappuram.msmetracker.dashboard.modelclass.ImageViewResponse;
import com.manappuram.msmetracker.dashboard.modelclass.StartServiceResponse;
import com.manappuram.msmetracker.databinding.ActivityDashboardBinding;
import com.manappuram.msmetracker.login.model.ActivityCheckResponse;
import com.manappuram.msmetracker.receiver.Restarter;
import com.manappuram.msmetracker.receiver.YourService;
import com.manappuram.msmetracker.utility.Utility;
import com.manappuram.msmetracker.viewmodel.LoginViewmodel;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;


public class DashboardActivity extends BaseActivity {

    ActivityDashboardBinding binding;
    LoginViewmodel viewmodel;
    AdapterSpinner adapter;
    String activityid = "", activityname = "", currentlatitiudestring = "", currentlongitudestring = "", activitynamefrom = "";
    String remarks = "", imageid = "", imagename = "", halfimagename = "", unfinishedtask = "";
    double currentLatitude;
    double currentLongitude;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    List<ActivitylistResponse.get_activity_list_data> spinnerlist = new ArrayList<>();
    ArrayList<String> spinnerlistone = new ArrayList<>();
    List<ActivitylistResponse.get_activity_list_data> spinnerdata;
    AlertDialog dialog;
    String[] perms = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int REQUEST_CAPTURE_IMAGE = 1;


    Intent mServiceIntent;
    private YourService mYourService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        viewmodel = ViewModelProviders.of(this).get(LoginViewmodel.class);
        mActivity = this;
        activitynamefrom = (getIntent().getStringExtra("activityname")) != null ? getIntent().getStringExtra("activityname") : "";
        halfimagename = (getIntent().getStringExtra("halfimagename")) != null ? getIntent().getStringExtra("halfimagename") : "";
        unfinishedtask = (getIntent().getStringExtra("unfinishedtask")) != null ? getIntent().getStringExtra("unfinishedtask") : "";
        if (unfinishedtask.contains("none")) {
            fetchLastLocation();
            getCurrentLocation();
        }
//        mServiceIntent = new Intent(this, mYourService.getClass());
//        if (!isMyServiceRunning(mYourService.getClass())) {
//            startService(mServiceIntent);
//        }

        pullToRefresh();
        checkAndRequestPermissions();
        startbtnclick();
        stopbtnClick();
        startbtnenablecheck();
        observers();
        RecyclerviewData();
        imageviewclick();


        binding.titleempname.setText(name);
        binding.pulltorefresh.setVisibility(View.VISIBLE);
        binding.activityselection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.chatrecyclerlayout.setVisibility(View.VISIBLE);
                binding.linelayout.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_from_top);
                animation.setStartOffset(0);
                binding.chatrecyclerlayout.startAnimation(animation);
                RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                rotateAnimation.setRepeatCount(0);
                rotateAnimation.setDuration(300);
                rotateAnimation.setFillAfter(true);
                binding.arrow.startAnimation(rotateAnimation);
            }
        });

    }


    private void pullToRefresh() {
        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                activityadapterspinner();
                binding.activityselection.setEnabled(true);
                binding.stopbtn.setVisibility(View.GONE);
                binding.startbtnenable.setVisibility(View.VISIBLE);
                binding.remarks.setText("");
            }
        });
    }

    private void datatolist() {
        if (spinnerlist != null) {
            if (spinnerlist.size() > 0) {
                for (ActivitylistResponse.get_activity_list_data tt : spinnerlist) {
                    if (tt.getActivity_id().equals(activitynamefrom)) {
                        binding.spinnevalue.setText(tt.getActivity_name());
                        activityid = activitynamefrom;
                        activityname = tt.getActivity_name();
                    }
                }
            }
        }
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

    private void imageviewclick() {
        binding.viewimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "clicked", Toast.LENGTH_SHORT).show();
                String imageview = Utility.encodecusid(sessionId + "$" + imageid);
                String encrypted = imageview.replaceAll("\\s", "");
                Log.i("imageview", encrypted);
                showProgress();
                viewmodel.photo_view(encrypted, "1");
            }
        });


    }

    private void stopbtnClick() {
        binding.stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.startimagename.getText().toString().equals("")) {
                    Toast.makeText(mActivity, "Please Upload Starting Image", Toast.LENGTH_SHORT).show();
                } else if (binding.remarks.getText().toString().equals("")) {
                    Toast.makeText(mActivity, "Please Enter Remarks", Toast.LENGTH_SHORT).show();
                } else {
                    showAlertDialog(mActivity, (dialog, i) -> {
                    });
                }
            }
        });
    }


    private void observers() {
        viewmodel.getActivitylistResponseMutableLiveData().observe(this, new Observer<ActivitylistResponse>() {
            @Override
            public void onChanged(ActivitylistResponse activitylistResponse) {
                spinnerlist.clear();
                binding.refresh.setRefreshing(false);
                spinnerdata = activitylistResponse.getGet_activity_list_data();

                if (activitylistResponse.getStatus().equals("111")) {
                    spinnerlist.addAll(activitylistResponse.getGet_activity_list_data());
                    if (activitynamefrom.equals("none")) {
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(mActivity, "Please Complete the Unfinished task", Toast.LENGTH_SHORT).show();
                        binding.startbtnenable.setVisibility(View.GONE);
                        binding.stopbtn.setVisibility(View.VISIBLE);
                        binding.activityselection.setEnabled(false);
                        datatolist();
                    }
                } else {
                    Toast.makeText(mActivity, activitylistResponse.getResult(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewmodel.getStartServiceResponseMutableLiveData().observe(this, new Observer<StartServiceResponse>() {
            @Override
            public void onChanged(StartServiceResponse startServiceResponse) {
                hideProgress();
                if (startServiceResponse.getStatus().equals("111")) {
                    binding.remarks.setText("");
                    binding.activityselection.setEnabled(false);
                    binding.stopbtn.setVisibility(View.VISIBLE);
                    binding.startbtnenable.setVisibility(View.GONE);
                    binding.pulltorefresh.setVisibility(View.GONE);
                    imageid = startServiceResponse.getResult();
                    imagename = startServiceResponse.getName();
                    binding.startimagename.setText(imagename);
                } else {
                    Toast.makeText(mActivity, startServiceResponse.getResult(), Toast.LENGTH_SHORT).show();
                    binding.stopbtn.setVisibility(View.GONE);
                    binding.startbtnenable.setVisibility(View.VISIBLE);
                }
            }
        });

        viewmodel.getImageViewResponseMutableLiveData().observe(this, new Observer<ImageViewResponse>() {
            @Override
            public void onChanged(ImageViewResponse imageViewResponse) {
                hideProgress();
                if (imageViewResponse.getStatus().equals("111")) {
                    String imagenameofpic = imageViewResponse.getImage();
                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                    final View customLayout = getLayoutInflater().inflate(R.layout.custom_kyc_layout_new, null);
                    builder.setView(customLayout);
                    ZoomageView image = customLayout.findViewById(R.id.imageView);
                    Picasso.get().invalidate("https://online.manappuram.com/TrackerAPI/images/" + imagenameofpic);
                    Picasso.get().load(("https://online.manappuram.com/TrackerAPI/images/") + imagenameofpic).into(image);
                    builder.setPositiveButton("CANCEL", (dialog, which) -> {
                        dialog.dismiss();
                    });
                    dialog = builder.create();
                    dialog.show();
                } else {
                    Toast.makeText(mActivity, imageViewResponse.getResult(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void activityadapterspinner() {
        String data = Utility.encodecusid(sessionId + "$" + "0");
        String encrypted = data.replaceAll("\\s", "");
        viewmodel.Get_activity_list(encrypted);
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

                if (binding.spinnevalue.getText().toString().contains("Select the Activity")) {
                    Toast.makeText(mActivity, "Please Select an Activity", Toast.LENGTH_SHORT).show();
                } else {
                    remarks = binding.remarks.getText().toString();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        //14 code
                        camerpermissionforupsidedowncake();
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        //13 code
                        camerpermissionforupsidedowncake();
                    } else {
                        if (ActivityCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestforMediaPermission();
                        } else {
                            ChooseTypeBottomsheet();
                        }
                    }
                }

            }
        });
    }

    private void camerpermissionforupsidedowncake() {
        // Check if the camera permission is granted
        if (ContextCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, proceed with camera operations
            ChooseTypeBottomsheet();
        }

    }


    private void RecyclerviewData() {
        binding.chatrecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new AdapterSpinner(mActivity, spinnerlist, new AdapterSpinner.spinnerclick() {
            @Override
            public void spinnerclick(String name, String id) {

                activityid = id;
                activityname = name;
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
        binding.chatrecycler.setAdapter(adapter);
    }

    private void fetchLastLocation() {
        LocationManager locationManager;
        locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestforGpsPermission();

        } else {


            boolean gps_enabled = false;

            gps_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!gps_enabled) {
                new android.app.AlertDialog.Builder(mActivity)
                        .setMessage("To continue, let your device turn on location using Google\\'s location Service")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Turn on", (paramDialogInterface, paramInt) ->
                                mActivity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))).show();
            } else {

                getCurrentLocation();

                //Toast.makeText(MapsActivity.this, "latitude = " + mCurrentLocation.getLatitude() + "longtitude = " + mCurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void getCurrentLocation() {
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkAndRequestPermissions();
        }
        LocationServices.getFusedLocationProviderClient(mActivity).
                requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(mActivity).
                                removeLocationUpdates(this);

                        if (locationResult != null && locationResult.getLocations().size() > 0) {


                            int lastLocationIndex = locationResult.getLocations().size() - 1;
                            currentLatitude = locationResult.getLocations().get(lastLocationIndex).getLatitude();
                            currentLongitude = locationResult.getLocations().get(lastLocationIndex).getLongitude();

                            currentlatitiudestring = String.valueOf(currentLatitude);
                            currentlongitudestring = String.valueOf(currentLongitude);

                            editor.putString("startlatitude", currentlatitiudestring);
                            editor.putString("startlogitude", currentlongitudestring);
                            editor.apply();


                            Log.i("locationnn", "<==" + currentLatitude);
                            Log.i("locationnn", "<==" + currentLongitude);
                            Log.i("locationnn", "<==" + currentlatitiudestring);
                            Log.i("locationnn", "<==" + currentlongitudestring);


                            try {
                                Geocoder geocoder;
                                List<Address> addresses;
                                geocoder = new Geocoder(mActivity, Locale.getDefault());
                                addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1);
                                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                String currentCity = addresses.get(0).getLocality();
//                                binding.location.setText(address);
                                if (currentCity == null) {
                                    String[] location_name = address.split(",");
                                    String loc_name1 = location_name[1];
                                    String loc_name2 = location_name[2];
                                    Log.i("locationnn_loc_name", loc_name2);
                                    currentCity = loc_name2;


                                }
                                String state = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                String postalCode = addresses.get(0).getPostalCode();
                                String knownName = addresses.get(0).getFeatureName();


                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }

                    }
                }, Looper.getMainLooper());
    }

    public void requestforMediaPermission() {
        Dexter.withActivity(this)
                .withPermissions(perms)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            ChooseTypeBottomsheet();

                        } else if (report.isAnyPermissionPermanentlyDenied()) {

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }

                }).onSameThread()
                .check();
    }


    private void ChooseTypeBottomsheet() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        proofPhotoUpload.launch(camera_intent);


    }


    ActivityResultLauncher<Intent> proofPhotoUpload = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Do your code from onActivityResult
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Bundle extras = data.getExtras();
                        Bitmap photo = extras.getParcelable("data");
                        assert data != null;
                        Uri selectedFileUri = getImageUri(DashboardActivity.this.getApplicationContext(), photo);
                        UploadMedia(selectedFileUri);


                    }

                }
            });

    private void UploadMedia(Uri businesspicUri) {
        Bitmap originalBitmap = null;
        long filesize;
        ByteArrayOutputStream bytes = null;
        try {
            originalBitmap = MediaStore.Images.Media.getBitmap(DashboardActivity.this.getContentResolver(), businesspicUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = new ByteArrayOutputStream();
        originalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        originalBitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        byte[] imageInByte = bytes.toByteArray();
        filesize = (long) (imageInByte.length / 1024.0);//KB

        String filename = Utility.getFileName(DashboardActivity.this, businesspicUri);
        binding.startimagename.setText(filename);
        binding.imageuploadlayout.setVisibility(View.VISIBLE);

        if (filesize > 2048) {
            originalBitmap = resize(originalBitmap, originalBitmap.getWidth() / 2, originalBitmap.getHeight() / 6);
            bytes = new ByteArrayOutputStream();
            originalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            originalBitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
            imageInByte = bytes.toByteArray();
            filesize = (long) (imageInByte.length / 1024.0);//KB

        } else {
            Log.e("TAG", "UploadMedia: " + filesize);
        }

//        binding.profileEditImage.setImageBitmap(originalBitmap);

        //   String profileimagevalue = Utility.BitMapToStringphoto(originalBitmap);
        String profileimagevalue = Base64.encodeToString(imageInByte, Base64.DEFAULT);


        String data = Utility.encodecusid(sessionId + "$" + activityid + "~" + remarks + "~" + currentLatitude + "~" + currentLongitude + "~" + branchId + "~" + empCode);
        String enrypted = data.replaceAll("\\s", "");
        showProgress();
        viewmodel.MSME_start_activity(enrypted, profileimagevalue);


    }

    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = "";

        String currentTime = new SimpleDateFormat("ddmmyyyyHHmmss", Locale.getDefault()).format(new Date());
        try {
            path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + currentTime, null);

        } catch (Exception e) {
            Log.e("Media", String.valueOf(e));
        }
        return Uri.parse(path);
    }

    public void requestforGpsPermission() {
        Dexter.withActivity(mActivity)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            fetchLastLocation();

                        } else if (report.isAnyPermissionPermanentlyDenied()) {

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).onSameThread()
                .check();
    }

    private boolean checkAndRequestPermissions() {

        if (EasyPermissions.hasPermissions(mActivity, perms)) {
            return true;
        } else {
            requestPerms();
            return false;
        }

    }

    private void requestPerms() {

        EasyPermissions.requestPermissions(new pub.devrel.easypermissions.PermissionRequest.Builder(this, REQUEST_CAPTURE_IMAGE, perms)
                .setRationale("Camera and Location access required for this app")
                .build());

    }

    public void showAlertDialog(Context context, DialogInterface.OnClickListener listener) {

        builder = new AlertDialog.Builder(context);
        builder.setMessage("Alert!!!");
        builder.setCancelable(true);

        builder.setPositiveButton("Are you Sure Want to Stop?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(mActivity, DistanceCalculationActivity.class);
                intent.putExtra("activityid", activityid);
                intent.putExtra("activityname", activityname);
                intent.putExtra("startimagename", imagename);
                intent.putExtra("startimageid", imageid);
                intent.putExtra("endremark", binding.remarks.getText().toString());
                intent.putExtra("activitynamefrom", activitynamefrom);
                intent.putExtra("halfimagename", halfimagename);
                startActivity(intent);


            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();


            }

        });
        alertDialog = builder.create();
        alertDialog.show();


    }


    @Override
    protected void onResume() {
        super.onResume();
        activityadapterspinner();
//        if (activitynamefrom.equals("none")) {
//
//        } else {
//            binding.spinnevalue.setText(unfinishedactivity);
//        }


    }

    //    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        Intent broadcastIntent = new Intent();
//        broadcastIntent.setAction("restartservice");
//        broadcastIntent.setClass(this, Restarter.class);
//        this.sendBroadcast(broadcastIntent);
//        super.onDestroy();
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with camera operations
                ChooseTypeBottomsheet();
            } else {
                Toast.makeText(mActivity, "Please Enable Permission to access Camera", Toast.LENGTH_SHORT).show();
                // Permission denied, handle accordingly (e.g., show a message or disable camera functionality)
            }
        }
    }

}
