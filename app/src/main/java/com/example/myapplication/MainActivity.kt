package com.example.myapplication

import android.os.Bundle
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
        webView.loadUrl("https://auth.mercadopago.com.ar/stage/authorization?client_id=1725127649086275&response_type=code&platform_id=mp")
        webView.webViewClient = WebViewClient()
        val settings:WebSettings = webView.settings
        settings.javaScriptEnabled = true

    }
}