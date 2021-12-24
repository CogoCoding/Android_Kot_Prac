package com.example.kot6.kot12

import android.content.Intent
import android.net.wifi.hotspot2.pps.Credential
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.kot6.R
import com.example.kot6.databinding.ActivityLoginBinding
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.database.ktx.database

class LoginActivity:AppCompatActivity(){

    private lateinit var auth : FirebaseAuth
    private lateinit var callbackManager: CallbackManager

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        val emailEditText = findViewById<EditText>(R.id.emailEditText)
//        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
//        val loginBtn = findViewById<Button>(R.id.loginBtn)
//        val JoinBtn = findViewById<Button>(R.id.joinBtn)
//        위 4줄처럼 일일이 하지 않고 아래처럼 binding 활용
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth // auth = FirebaseAuth.getInstance()랑 같은데 lateinit으로 한 것
        callbackManager = CallbackManager.Factory.create()

        initLoginBtn() //해당 함수 블럭표시 후 단축키 ctrl + alt + m로 extract Function
        initJoinBtn()
        initEmailAndPasswordEditText()
        initFacebookLoginButton()
    }

    private fun initFacebookLoginButton() {
        val facebookLoginButton = findViewById<LoginButton>(R.id.facebookLoginBtn)
        facebookLoginButton.setPermissions("email","public_profile")
        facebookLoginButton.registerCallback(callbackManager, object:FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult) {
                //액세스토큰을 firebase로 넘김
                val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this@LoginActivity) { task ->
                        if (task.isSuccessful) {
                            handleSuccessLogin()
                        } else {
                            Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            override fun onCancel() {
            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(this@LoginActivity,"로그인 실패",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun handleSuccessLogin() {
        if(auth.currentUser == null){
            Toast.makeText(this,"로그인 실패",Toast.LENGTH_SHORT).show()
            return
        }
        val userId = auth.currentUser?.uid.orEmpty()
        val currentUserDB = Firebase.database.reference.child("Users").child(userId)
        val user = mutableMapOf<String,Any>()
        user["userId"]=userId
        currentUserDB.updateChildren(user)
        finish()
    }

    private fun getInputPassword():String {
        return binding.passwordEditText.text.toString()
    }

    private fun getInputEmail():String {
        return binding.emailEditText.text.toString()
    }

    private fun initLoginBtn() {
        binding.loginBtn.setOnClickListener {
            Log.d("login btn","눌림")
            auth.signInWithEmailAndPassword(getInputEmail(), getInputPassword())
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG).show()
                        handleSuccessLogin()
                    } else {
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun initJoinBtn() {
        binding.joinBtn.setOnClickListener {
            Log.d("join btn","눌림")
            auth.createUserWithEmailAndPassword(getInputEmail(),getInputPassword())
                .addOnCompleteListener(this){task->
                    if(task.isSuccessful){
                        Toast.makeText(this, "가입 성공", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this, "가입 실패", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun initEmailAndPasswordEditText(){
        binding.emailEditText.addTextChangedListener {
            val enable =
                binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()
            binding.loginBtn.isEnabled = enable
            binding.joinBtn.isEnabled = enable
        }
        binding.passwordEditText.addTextChangedListener {
            val enable =
                binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()
            binding.loginBtn.isEnabled = enable
            binding.joinBtn.isEnabled = enable
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}