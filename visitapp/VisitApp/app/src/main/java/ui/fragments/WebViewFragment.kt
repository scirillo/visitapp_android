package com.vistapp.visitapp.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.charly.visitapp.R

/**
 * Created by Santiago Cirillo on 18/05/2017.
 */

class WebViewFragment : Fragment() {

    private var mWebView: WebView? = null
    private var spinner: ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.webview_fragment, container, false)
        mWebView = rootView.findViewById(R.id.webview)
        spinner = rootView.findViewById(R.id.spinner)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webSettings = mWebView!!.settings
        webSettings.javaScriptEnabled = true
        mWebView!!.loadUrl("http://www.infobae.com/america/")
        mWebView!!.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                Log.d("WebView", "onPageStarted " + url)
                showSpinner(true)
            }

            override fun onPageFinished(view: WebView, url: String) {
                Log.d("WebView", "onPageFinished " + url)
                showSpinner(false)
            }
        }
    }

    private fun showSpinner(show: Boolean) {
        if (show) {
            spinner!!.visibility = View.VISIBLE
        } else
            spinner!!.visibility = View.GONE
    }

    companion object {

        fun newInstance(): WebViewFragment {
            return WebViewFragment()
        }
    }
}
