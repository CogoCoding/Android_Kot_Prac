package com.example.kot6.kot7

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.kot6.R

class MainAcitivity : AppCompatActivity()  {

    private val webView:WebView by lazy{
        findViewById(R.id.webView)
    }

    private val addressBar: EditText by lazy{
        findViewById(R.id.addressBar)
    }

    private val goHomeBtn:ImageButton by lazy{
        findViewById(R.id.homeBtn)
    }

    private val goBackBtn:ImageButton by lazy{
        findViewById(R.id.backBtn)
    }

    private val goForwardBtn:ImageButton by lazy{
        findViewById(R.id.forwardBtn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kot7)
        initViews()
        bindViews()
    }

    override fun onBackPressed() {
        if(webView.canGoBack()){webView.goBack()}
        else{super.onBackPressed()}
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews(){
        webView.apply{
            webView.webViewClient= WebViewClient()
            webView.settings.javaScriptEnabled=true
            webView.loadUrl(DEFAULT_URI)
        }
    }

    private fun bindViews(){
        addressBar.setOnEditorActionListener{v,actionId,event->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                webView.loadUrl(v.text.toString())
            }
            return@setOnEditorActionListener false
        }
        goHomeBtn.setOnClickListener{
            webView.loadUrl(DEFAULT_URI)
        }

        goBackBtn.setOnClickListener{
            webView.goBack()
        }

        goForwardBtn.setOnClickListener{
            webView.goForward()
        }
    }

    companion object{
        private const val DEFAULT_URI = "http://www.google.com"

    }



}