package com.manappuram.msmetracker.utility;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.manappuram.msmetracker.session.AppLockManager;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public class MSMEApplication extends Application {

    public static MSMEApplication getAppInstance() {
        return appInstance;
    }

    public void touch() {
        AppLockManager.getInstance().updateTouch();
    }

    public static CryptLib getCryptLib() {
        return cryptLib;
    }

    private static MSMEApplication appInstance;
    private static CryptLib cryptLib;
    public static Boolean showAnimation = true;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        Stetho.initializeWithDefaults(this);
        initCrypto();
        AppLockManager.getInstance().enableDefaultAppLockIfAvailable(
                this);

    }


    /**
     * Initialize Encryption for App
     */
    private void initCrypto() {
        try {
            cryptLib = new CryptLib();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }


}
