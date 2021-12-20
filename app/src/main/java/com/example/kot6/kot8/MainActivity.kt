package com.example.kot6.kot8

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kot6.R
// cloud messaging
class MainActivity : AppCompatActivity() {

    private val resultTv:TextView by lazy{
        findViewById(R.id.resultTv)
    }
    private val firebaseTv:TextView by lazy{
        findViewById(R.id.firebaseTv)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kot8)
    }
}