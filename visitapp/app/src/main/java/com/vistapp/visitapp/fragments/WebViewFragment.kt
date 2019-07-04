package com.vistapp.visitapp.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

import com.vistapp.visitapp.R

/**
 * Created by Santiago Cirillo on 18/05/2017.
 */

class WebViewFragment : Fragment() {

    private var mWebView: WebView? = null
    private var spinner: ProgressBar? = null

    @Nullable
    @Override
    fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup, @Nullable savedInstanceState: Bundle): View {
        val rootView = inflater.inflate(R.layout.webview_fragment, container, false)
        mWebView = rootView.findViewById(R.id.webview) as WebView
        spinner = rootView.findViewById(R.id.spinner) as ProgressBar
        return rootView
    }

    @Override
    fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle) {
        super.onViewCreated(view, savedInstanceState)
        val webSettings = mWebView!!.getSettings()
        webSettings.setJavaScriptEnabled(true)
        mWebView!!.loadUrl("http://www.infobae.com/america/")
        mWebView!!.setWebViewClient(object : WebViewClient() {

            @Override
            fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                Log.d("WebView", "onPageStarted " + url)
                showSpinner(true)
            }

            @Override
            fun onPageFinished(view: WebView, url: String) {
                Log.d("WebView", "onPageFinished " + url)
                showSpinner(false)
            }
        })
    }

    private fun showSpinner(show: Boolean) {
        if (show) {
            spinner!!.setVisibility(View.VISIBLE)
        } else
            spinner!!.setVisibility(View.GONE)
    }

    companion object {

        fun newInstance(): WebViewFragment {
            return WebViewFragment()
        }
    }
}
