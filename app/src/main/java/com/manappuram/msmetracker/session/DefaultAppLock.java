package com.manappuram.msmetracker.session;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import java.util.Date;

public class DefaultAppLock implements Application.ActivityLifecycleCallbacks {

    final String TAG = DefaultAppLock.class.getSimpleName();

    private Application mCurrentApp;

    private long WAIT_TIME = 5 * 60 * 1000;
    private SessionTimer sessionTimer;
    private Date mLostFocusDate;

    public DefaultAppLock(Application app) {
        super();
        mCurrentApp = app;
        //Registering Activity lifecycle callbacks
        mCurrentApp.unregisterActivityLifecycleCallbacks(this);
        mCurrentApp.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        //Call when user resume app from lock state
        if (sessionTimer != null) {
            sessionTimer.stopThread();
        }
        sessionTimer = new SessionTimer(activity, WAIT_TIME);
        sessionTimer.start();

        // for Screen lock
        if (shouldShowUnlockScreen()) {
//            Log.i(DefaultAppLock.class.getName(), "Session Expired : Sending back to Login");
//            mLostFocusDate = null;
//            if (activity instanceof DeviceIdActivity || activity instanceof DashboardActivity) {
//                return;
//            } else {
//                showSessionDialog(activity);
//            }
        }
    }

    /**
     * Method to check session timeout
     *
     * @return
     */
    private boolean shouldShowUnlockScreen() {
        Boolean isValid = false;
        if (mLostFocusDate == null) {
            isValid = false;
        } else {
            Log.d(TAG, "Timeout >" + timeSinceLocked());
            if (timeSinceLocked() >= (WAIT_TIME / 1000))
                isValid = true;
            else
                mLostFocusDate = null;
        }
        Log.d(TAG, isValid.toString());
        return isValid;
    }

    /**
     * Calculate Locked Time
     *
     * @return
     */
    private int timeSinceLocked() {
        return Math.abs((int) ((new Date().getTime() - mLostFocusDate.getTime()) / 1000));
    }


    @Override
    public void onActivityPaused(Activity activity) {
        if (sessionTimer != null) {
            sessionTimer.stopThread();
        }
        mLostFocusDate = new Date();
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    /**
     * Update Timer on User Touch event
     */
    public void updateTouch() {
        if (sessionTimer != null) {
            sessionTimer.touch();
        }
        mLostFocusDate = new Date();
    }

//    public void showSessionDialog(Activity activity) {
//        new AlertDialog.Builder(activity)
//                .setTitle(activity.getString(R.string.session_expired))
//                .setMessage(activity.getString(R.string.your_session_exp))
//                .setCancelable(false)
//                .setPositiveButton(activity.getString(R.string.login), (dialog, id) -> {
//                    Intent intent = new Intent(activity.getApplicationContext(), SplashActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    activity.startActivity(intent);
//                    activity.finish();
//                })
//                .show();
//    }
}
