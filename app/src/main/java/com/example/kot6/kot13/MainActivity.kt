package com.example.kot6.kot13

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kot6.R
import com.example.kot6.databinding.ActivityMainKot13Binding
import com.example.kot6.kot13.chatlist.ChatListFragment
import com.example.kot6.kot13.home.HomeFragment
import com.example.kot6.kot13.mypage.MyPageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity:AppCompatActivity() {
    private lateinit var binding: ActivityMainKot13Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main_kot13)
        binding = ActivityMainKot13Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val chatListFragment = ChatListFragment()
        val myPageFragment = MyPageFragment()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavagationView)
        bottomNavigationView.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(homeFragment)
                R.id.chatList ->replaceFragment(chatListFragment)
                R.id.myPage ->replaceFragment(myPageFragment)
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragmentContainer,fragment)
                commit()
            }
    }
}