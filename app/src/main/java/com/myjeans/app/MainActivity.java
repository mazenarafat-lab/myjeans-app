package com.myjeans.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.pusher.pushnotifications.PushNotifications;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private static final int NOTIFICATION_PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Pusher Beams
        PushNotifications.start(getApplicationContext(), "b94c7893-86d1-4668-9f36-c7678f825be4");
        PushNotifications.subscribeToInterest("myjeans-updates");

        // Request notification permission for Android 13+
        requestNotificationPermission();

        // Setup WebView
        webView = findViewById(R.id.webView);
        setupWebView();
        webView.loadUrl("http://myjeans-sy.com/");
    }

    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        
        // Fix font and zoom to match Chrome
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setTextZoom(100);
        webSettings.setDefaultFontSize(16);
        webSettings.setMinimumFontSize(12);

        webView.setWebChromeClient(new WebChromeClient());
        
        // Handle external links properly
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Load main domain in WebView, open external domains in browser
                if (url.contains("myjeans-sy.com")) {
                    // Load main website in WebView
                    view.loadUrl(url);
                    return true;
                } else if (url.startsWith("http://") || url.startsWith("https://")) {
                    // Open external HTTP/HTTPS links in browser
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Cannot open link: " + url, Toast.LENGTH_SHORT).show();
                    }
                    return true;
                } else {
                    // Open external links (tel:, mailto:, whatsapp:, etc.) in external apps
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Cannot open link: " + url, Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            }
        });
    }

    private void requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) 
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, 
                new String[]{Manifest.permission.POST_NOTIFICATIONS}, 
                NOTIFICATION_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, 
                                          @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
