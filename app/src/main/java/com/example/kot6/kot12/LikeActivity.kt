package com.example.kot6.kot12

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kot6.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class LikeActivity:AppCompatActivity(),CardStackListener { // CardStackListener 인터페이스 추가 후 자동 implement

    private var auth:FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var userDB : DatabaseReference
    private val adapter = CardItemAdapter()
    private val cardItems = mutableListOf<CardItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_like)

        userDB = Firebase.database.reference.child("Users")

        val currentUserDB = userDB.child(getCurrentUserID())
        currentUserDB.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.child("name").value==null){
                    showNameInputPopup()
                    return
                }
                //todo 유저정보 갱신
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        initCardStackView()
    }

    private fun initCardStackView() {
        val stackView = findViewById<CardStackView>(R.id.cardStackView)
        stackView.layoutManager = CardStackLayoutManager(this)
        stackView.adapter = adapter
    }

    private fun showNameInputPopup(){
        val editText = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("이름 : ")
            .setView(editText)
            .setPositiveButton("저장"){_,_->
                if(editText.text.isEmpty()){
                    showNameInputPopup()
                }else{
                    saveUserName(editText.text.toString())
                }
            }
            .setCancelable(false)
            .show()
    }

    private fun saveUserName(name: String) {
        val userId = getCurrentUserID()
        val currentUserDB = userDB.child(userId)
        val user = mutableMapOf<String,Any>()
        user["userId"]=userId
        user["name"]= name
        currentUserDB.updateChildren(user)
    }

    private fun setUserName(name:String){

    }

    private fun getCurrentUserID():String{
        if(auth.currentUser==null){
            Toast.makeText(this,"로그인", Toast.LENGTH_SHORT).show()
        }
        return auth.currentUser?.uid.orEmpty()

    }

    // 아래 자동 implement 된 6개 함수
    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }
    override fun onCardSwiped(direction: Direction?) {
    }
    override fun onCardRewound() {
    }
    override fun onCardCanceled() {
    }
    override fun onCardAppeared(view: View?, position: Int) {
    }
    override fun onCardDisappeared(view: View?, position: Int) {
    }
}