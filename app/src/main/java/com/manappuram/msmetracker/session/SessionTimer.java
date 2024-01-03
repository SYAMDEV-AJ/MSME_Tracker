package com.manappuram.msmetracker.session;

import android.content.Context;
import android.util.Log;

/**
 * Thread class to count user idle time
 */
public class SessionTimer extends Thread {
    private static final String TAG = SessionTimer.class.getName();
    private long lastUsed;
    private long period;
    private boolean stop = false;
    private Context mContext;

    public SessionTimer(Context context, long period) {
        this.period = period;
        stop = false;
        mContext = context;
    }

    /**
     * Method to execute task
     */
    public void run() {
        long idle = 0;
        this.touch();
        do {
            idle = System.currentTimeMillis() - lastUsed;
            try {
                Thread.sleep(20000000); //check every 20 seconds
            } catch (InterruptedException e) {
                Log.e(TAG, "SessionTimer interrupted!");
            }
            if (idle > period) {
                idle = 0;
                Log.d("Session", "Your Session is expire");
                // Perform Your desired Function like Logout or expire the session for the app.
                stopThread();
            }
        }
        while (!stop);
    }

    public synchronized void touch() {
        lastUsed = System.currentTimeMillis();
    }

    public synchronized void forceInterrupt() {
        this.interrupt();
    }

    public synchronized void setPeriod(long period) {
        this.period = period;
    }

    public synchronized void stopThread() {
        stop = true;
//        if (!(mContext instanceof RegistrationActivity)) {
//            Utility.showSessionDialog(mContext);
//            this.stopThread();
//        }
    }

    public synchronized void startThread() {
        stop = false;
    }

    public synchronized void closeThread() {
        // Perform Your desired Function like Logout or expire the session for the app.
        stopThread();
    }

}