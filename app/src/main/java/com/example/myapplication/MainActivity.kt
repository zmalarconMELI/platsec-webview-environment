package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setwebView()
    }

    fun setwebView() {
        webView = findViewById(R.id.webview)
        webView.loadUrl("https://auth.mercadopago.com.ar/stage/authorization?client_id=7797417756487296&response_type=code&scopes=read,account.debit&code_challenge=OYTJTHLI_DVE45e54_HxLbhHXDvYKe3eMpbkGGs6p9E&code_challenge_method=S256")
        val settings:WebSettings = webView.settings
        settings.javaScriptEnabled = true
        webView.webViewClient = object: WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                injectJS(view)
            }
        }
        webView.addJavascriptInterface(JSBridge, "Bridge")

    }

    override fun onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private fun injectJS(view: WebView?) {
        view!!.loadUrl(
            """
                javascript:(function(){
                    let button = document.getElementById("action-accept");
                    button.addEventListener("click", function(){
                        Bridge.calledFromJS(window.location.href);
                    })
                })()
            """.trimIndent()
        )
    }

    object JSBridge{
        @JavascriptInterface
        fun calledFromJS(msg: String){
            Log.d("DEBIN Flow", msg)
        }
    }
}