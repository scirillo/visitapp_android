package com.vistapp.visitapp.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.vistapp.visitapp.R;

/**
 * Created by Santiago Cirillo on 18/05/2017.
 */

public class WebViewFragment extends Fragment {

    private WebView mWebView;
    private ProgressBar spinner;

    public static WebViewFragment newInstance(){
        return new WebViewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.webview_fragment, container, false);
        mWebView = (WebView) rootView.findViewById(R.id.webview);
        spinner = (ProgressBar) rootView.findViewById(R.id.spinner);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.infobae.com/america/");
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("WebView", "onPageStarted " + url);
                showSpinner(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("WebView", "onPageFinished " + url);
                showSpinner(false);
            }
        });
    }

    private void showSpinner(boolean show) {
        if(show){
            spinner.setVisibility(View.VISIBLE);
        }else spinner.setVisibility(View.GONE);
    }
}
