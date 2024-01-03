package com.manappuram.msmetracker.session;

import android.app.Application;

public class AppLockManager {
    private static AppLockManager instance;
    private DefaultAppLock currentAppLocker;

    public static AppLockManager getInstance() {
        if (instance == null) {
            instance = new AppLockManager();
        }
        return instance;
    }

    public void enableDefaultAppLockIfAvailable(Application currentApp) {
        currentAppLocker = new DefaultAppLock(currentApp);
    }

    public void updateTouch() {
        currentAppLocker.updateTouch();
    }
}
