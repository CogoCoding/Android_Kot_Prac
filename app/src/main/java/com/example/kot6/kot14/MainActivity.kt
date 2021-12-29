package com.example.kot6.kot14

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.example.kot6.BuildConfig
import com.example.kot6.BuildConfig.GITHUB_CLIENT_ID
import com.example.kot6.R
import com.example.kot6.databinding.ActivityMainBinding
import com.example.kot6.databinding.ActivityMainKot14Binding

class MainActivity:AppCompatActivity() {

    private lateinit var binding: ActivityMainKot14Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainKot14Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initViews() = with(binding){
        loginBtn.setOnClickListener {
            loginGithub()
        }
    }

    // todo https://github.com/login/oauth/authorize?client_id=
    private fun loginGithub() {
        val loginUri = Uri.Builder().scheme("https").authority("github.com")
            .appendPath("login")
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
            .build()

        CustomTabsIntent.Builder().build().also{
            it.launchUrl(this,loginUri)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.getQueryParameter("code")?.let{
            // todo getAccessToken
        }
    }
}