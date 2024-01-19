package com.manappuram.msmetracker.network.retrofit;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.manappuram.msmetracker.BuildConfig;
import com.manappuram.msmetracker.network.APIInterface;


import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static String BASE_URL = "https://uatonpay.manappuram.com/TrackerAPI/MSME_EmployeeTrack.asmx/";
    //public static String BASE_URL = "https://online.manappuram.com/TrackerAPI/MSME_EmployeeTrack.asmx/"; //production
    private static RetrofitClient sInstance;
    private Retrofit retrofit;
    OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();


    public static void create() {
        if (sInstance == null) {
            synchronized (RetrofitClient.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitClient();
                }
            }
        }
    }


    public static APIInterface getAPIInterface() {
        if (sInstance == null)
            create();
        return sInstance.retrofit.create(APIInterface.class);
    }


    private RetrofitClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();


        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);
        }
        // httpClient.addInterceptor(new ApplicationInterceptor());
        httpClient.connectTimeout(300, TimeUnit.SECONDS);
        httpClient.writeTimeout(300, TimeUnit.SECONDS);
        httpClient.readTimeout(300, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                //.client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


    }

    /**
     * Returns the instance of {@link Retrofit}.
     * This method must be called after {@link #create}.
     */
    public static Retrofit retrofit() {
        synchronized (RetrofitClient.class) {
            if (sInstance == null)
                create();
        }
        return sInstance.retrofit;
    }

    public static class UnsafeOkHttpClient {
        public static OkHttpClient getUnsafeOkHttpClient() {
            try {
                // Create a trust manager that does not validate certificate chains
                final TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.addNetworkInterceptor(new StethoInterceptor());
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });

                OkHttpClient okHttpClient = builder.build();
                return okHttpClient;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
