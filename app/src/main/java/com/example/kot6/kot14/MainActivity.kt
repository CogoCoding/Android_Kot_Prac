package com.example.kot6.kot14

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isGone
import com.example.kot6.BuildConfig

import com.example.kot6.databinding.ActivityMainKot14Binding
import com.example.kot6.kot14.utility.AuthTokenProvider
import com.example.kot6.kot14.utility.RetrofitUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity:AppCompatActivity(),CoroutineScope {

    private lateinit var binding: ActivityMainKot14Binding

    private val authTokenProvider by lazy{AuthTokenProvider(this)}

    val job : Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainKot14Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
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
            launch(coroutineContext){
                showProgress()
                val getAccessTokenJob = getAccessToken(it)
                hideProgress()
            }
        }
    }

    private suspend fun showProgress()= withContext(coroutineContext){
        with(binding){
            loginBtn.isGone=true
            progressBar.isGone=false
            progressTv.isGone=false
        }
    }

    private suspend fun hideProgress()= withContext(coroutineContext){
        with(binding){
            loginBtn.isGone=false
            progressBar.isGone=true
            progressTv.isGone=true
        }
    }


    private suspend fun getAccessToken(code:String)= withContext(Dispatchers.IO){
        val response = RetrofitUtil.authApiService.getAccessToken(
            clientId = BuildConfig.GITHUB_CLIENT_ID,
            clientSecret = BuildConfig.GITHUB_CLIENT_SECRET,
            code = code
        )
        if(response.isSuccessful){
            val accessToken = response.body()?.accessToken?:""
            Log.e("accessToken",accessToken)
            if(accessToken.isNotEmpty()){
                authTokenProvider.updateToken(accessToken)
            }else{
                Toast.makeText(this@MainActivity,"accessToken이 존재하지 않습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
}