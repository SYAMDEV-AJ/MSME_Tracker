package com.manappuram.msmetracker.map.map;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.manappuram.msmetracker.R;
import com.manappuram.msmetracker.base.BaseActivity;
import com.manappuram.msmetracker.databinding.WebviewmapBinding;

public class MapViewActivity extends BaseActivity {
    WebviewmapBinding binding;
    String three = "", four = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.webviewmap);
        three = getIntent().getStringExtra("three");
        four = getIntent().getStringExtra("four");


        binding.webViewDocument.getSettings().setLoadsImagesAutomatically(true);
        binding.webViewDocument.getSettings().setJavaScriptEnabled(true);
//        binding.webViewDocument.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        binding.webViewDocument.getSettings().setLoadWithOverviewMode(true);
        binding.webViewDocument.getSettings().setUseWideViewPort(true);
        binding.webViewDocument.getSettings().setBuiltInZoomControls(true);
        //  Utility.setProgressbar(this);
        binding.webViewDocument.setWebViewClient(new WebViewClient());
        binding.webViewDocument.loadUrl("https://www.google.com/maps/dir/" + startlatitudedata + "," + startlogitudedata + "/" + three + "," + four);

    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // Utility.cancelProgressbar();
        }
    }
}
