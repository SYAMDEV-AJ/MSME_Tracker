package com.manappuram.msmetracker.dashboard.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.jsibbold.zoomage.ZoomageView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.manappuram.msmetracker.R;
import com.manappuram.msmetracker.base.BaseActivity;
import com.manappuram.msmetracker.dashboard.modelclass.ImageViewResponse;
import com.manappuram.msmetracker.dashboard.modelclass.StartServiceResponse;
import com.manappuram.msmetracker.databinding.ActivityDistanceCalculationBinding;
import com.manappuram.msmetracker.utility.Utility;
import com.manappuram.msmetracker.viewmodel.LoginViewmodel;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;


public class DistanceCalculationActivity extends BaseActivity {

    ActivityDistanceCalculationBinding binding;
    LoginViewmodel viewmodel;
    Thread t = null;


    String[] perms = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    String activityid = "";
    String activityname = "";
    String startlatitude = "";
    String startlongitude = "";
    String endremark = "", startimagename = "", profileimagevalue = "", activitynamefrom = "";
    String endlocationlat = "", endlocationlog = "", startimageid = "", endimageid = "", endimagename = "", finalDist = "";

    private static final int REQUEST_CAPTURE_IMAGE = 1;

    Handler handler;
    Runnable runnable;
    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_distance_calculation);
        viewmodel = ViewModelProviders.of(this).get(LoginViewmodel.class);
        mActivity = this;

        activityid = getIntent().getStringExtra("activityid");
        activityname = getIntent().getStringExtra("activityname");
        startlatitude = getIntent().getStringExtra("startlatitude");
        startlongitude = getIntent().getStringExtra("startlongitude");
        endremark = getIntent().getStringExtra("endremark");
        startimagename = getIntent().getStringExtra("startimagename");
        startimageid = getIntent().getStringExtra("startimageid");
        activitynamefrom = getIntent().getStringExtra("activitynamefrom");

        if (activitynamefrom.equals("none")) {

        } else {
            binding.startimagelayout.setVisibility(View.GONE);
        }


        binding.startimagename.setText(startimagename);
        binding.spinnevalue.setText(activityname);
        binding.remarks.setText(endremark);
        binding.remarks.setEnabled(false);
        binding.titleempname.setText(name);


        checkAndRequestPermissions();
        fetchLastLocation();
        // getCurrentLocation();
        // runthread();
        observers();
        imageviewclick();
        imageviewendclick();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//    }

    private void runthread1() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                try {
//                   // Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }


                if (ActivityCompat.checkSelfPermission(DistanceCalculationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DistanceCalculationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestforMediaPermission();
                } else {
                    ChooseTypeBottomsheet();
                }


            }
        });
    }

    private void runthread() {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        fetchLastLocation();
                        getCurrentLocation();

                        Toast.makeText(mActivity, "Location thread", Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });
        t.start();
        t.setPriority(Thread.MAX_PRIORITY);

    }

    private void imageviewclick() {
        binding.viewimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mActivity, "clicked", Toast.LENGTH_SHORT).show();
                String imageview = Utility.encodecusid(sessionId + "$" + startimageid);
                String encrypted = imageview.replaceAll("\\s", "");

                showProgress();
                viewmodel.photo_view(encrypted, "1");
            }
        });


    }

    private void imageviewendclick() {
        binding.endimageviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mActivity, "clicked", Toast.LENGTH_SHORT).show();
                String imageview = Utility.encodecusid(sessionId + "$" + endimageid);
                String encrypted = imageview.replaceAll("\\s", "");

                showProgress();
                viewmodel.photo_view(encrypted, "2");
            }
        });


    }


    private void observers() {

        viewmodel.getEndServiceResponseMutableLiveData().observe(this, new Observer<StartServiceResponse>() {
            @Override
            public void onChanged(StartServiceResponse startServiceResponse) {
                hideProgress();
                if (startServiceResponse.getStatus().equals("111")) {

                    endimageid = startServiceResponse.getResult();
                    endimagename = startServiceResponse.getName();
                    binding.endimagename.setText(endimagename);


                    Toast.makeText(mActivity, "Successfully Uploaded", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(mActivity, startServiceResponse.getResult(), Toast.LENGTH_SHORT).show();

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
                    Picasso.get().invalidate("https://uatonpay.manappuram.com/TrackerAPI/images/" + imagenameofpic);
                    Picasso.get().load(("https://uatonpay.manappuram.com/TrackerAPI/images/") + imagenameofpic).into(image);

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
                showProgress();
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
                        try {

                            if (locationResult != null && locationResult.getLocations().size() > 0) {


                                int lastLocationIndex = locationResult.getLocations().size() - 1;
                                double endlatitude = locationResult.getLocations().get(lastLocationIndex).getLatitude();
                                double endlongitude = locationResult.getLocations().get(lastLocationIndex).getLongitude();

                                endlocationlat = String.valueOf(endlatitude);
                                endlocationlog = String.valueOf(endlongitude);

                                if (endlocationlat.equals("") || endlocationlog.equals("")) {
                                    hideProgress();
                                    Toast.makeText(mActivity, "Not Getting Location", Toast.LENGTH_SHORT).show();
                                } else {
                                    hideProgress();
                                    runthread1();
                                }


                                double firstlat = Double.parseDouble(startlatitude);
                                double firstlog = Double.parseDouble(startlongitude);

//                                handler = new Handler();
//                                runnable = new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        distance(firstlat, firstlog, endlatitude, endlongitude);
//
//
//                                    }
//                                };
//                                handler.postDelayed(runnable, 100);


                                try {
                                    Geocoder geocoder;
                                    List<Address> addresses;
                                    geocoder = new Geocoder(mActivity, Locale.getDefault());
                                    addresses = geocoder.getFromLocation(endlatitude, endlongitude, 1);
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


                        } catch (Exception e) {
                            return;

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
                        Uri selectedFileUri = getImageUri(DistanceCalculationActivity.this.getApplicationContext(), photo);
                        UploadMedia(selectedFileUri);


                    }

                }
            });

    private void UploadMedia(Uri businesspicUri) {
        Bitmap originalBitmap = null;
        long filesize;
        ByteArrayOutputStream bytes = null;
        try {
            originalBitmap = MediaStore.Images.Media.getBitmap(DistanceCalculationActivity.this.getContentResolver(), businesspicUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = new ByteArrayOutputStream();
        originalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        originalBitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        byte[] imageInByte = bytes.toByteArray();
        filesize = (long) (imageInByte.length / 1024.0);//KB

        String filename = Utility.getFileName(DistanceCalculationActivity.this, businesspicUri);
//        binding.endimagename.setText(filename);
        binding.imageuploadlayout.setVisibility(View.VISIBLE);

        if (filesize > 3072) {
/*
            originalBitmap = resize(originalBitmap, originalBitmap.getWidth() / 2, originalBitmap.getHeight() / 2);
*/
            bytes = new ByteArrayOutputStream();
            originalBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
            originalBitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
            imageInByte = bytes.toByteArray();
            filesize = (long) (imageInByte.length / 2048.0);//KB

        } else {
            Log.e("TAG", "UploadMedia: " + filesize);
        }
        Toast.makeText(mActivity, "Distance is Calculating Please Wait a Moment...", Toast.LENGTH_SHORT).show();

        profileimagevalue = Base64.encodeToString(imageInByte, Base64.DEFAULT);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                distance(Double.parseDouble(startlatitude), Double.parseDouble(startlongitude), Double.parseDouble(endlocationlat), Double.parseDouble(endlocationlog));


            }
        };
        handler.postDelayed(runnable, 100);

//        if (profileimagevalue.equals("") && endlocationlat.equals("") && endlocationlog.equals("")) {
//
//            getCurrentLocation();
//
//        } else {
//            String data = Utility.encodecusid(sessionId + "$" + activityid + "~" + endremark + "~" + endlocationlat + "~" + endlocationlog + "~" + finalDist);
//            String enrypted = data.replaceAll("\\s", "");
//            Log.i("enddistance", enrypted);
//            showProgress();
//            viewmodel.MSME_end_activity(enrypted, profileimagevalue);
//        }


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
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
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


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = Math.round(dist * 60 * 1.60934);
        int dist1 = (int) dist;
        //dist = dist * 60 * 1.60934;

        finalDist = String.valueOf(dist1);


        binding.totaldistance.setText(finalDist + " KM");

        if (profileimagevalue.equals("")) {

        } else {
            String data = Utility.encodecusid(sessionId + "$" + activityid + "~" + endremark + "~" + lat2 + "~" + lon2 + "~" + finalDist);
            String enrypted = data.replaceAll("\\s", "");
            Log.i("enddistance", enrypted);
            showProgress();
            viewmodel.MSME_end_activity(enrypted, profileimagevalue);
        }


        Log.i("ditancecalculation", String.valueOf(dist));
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        fetchLastLocation();
//        getCurrentLocation();
    }
}
