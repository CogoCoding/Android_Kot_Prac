package com.example.kot6.kot13

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kot6.R
import com.example.kot6.databinding.ActivityMainKot13Binding

class MainActivity:AppCompatActivity() {
    private lateinit var binding: ActivityMainKot13Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main_kot13)
        binding = ActivityMainKot13Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}