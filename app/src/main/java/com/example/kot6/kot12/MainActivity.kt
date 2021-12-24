package com.example.kot6.kot12

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kot6.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity:AppCompatActivity(){

    private val auth :FirebaseAuth = FirebaseAuth.getInstance() // 초기화 시 바로 가져오기
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kot12)
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser ==null){startActivity(Intent(this,LoginActivity::class.java))}
    }
}