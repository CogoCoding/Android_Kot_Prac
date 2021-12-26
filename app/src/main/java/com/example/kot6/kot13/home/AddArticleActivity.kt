package com.example.kot6.kot13.home

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.kot6.R

class AddArticleActivity:AppCompatActivity() {
    private var selectedUri: Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_article)
        findViewById<Button>(R.id.imageAddBtn).setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startContentProvider()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    showPermissionContextPopup()
                }
                else -> {
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        1010)
                }
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            1010->
                if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    startContentProvider()
                }else{
                    Toast.makeText(this,"권한을거부하셨습니다",Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun startContentProvider(){
        val intent= Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent,2020)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode!= Activity.RESULT_OK){
            return
        }
        when(requestCode){
            2020->{
                val uri = data?.data
                if(uri!=null){
                    findViewById<ImageView>(R.id.photoImageView).setImageURI(uri)
                    selectedUri=uri
                }else{
                    Toast.makeText(this,"사진을 가져오지 못했습니다",Toast.LENGTH_SHORT).show()
                }
            }
            else ->{
                Toast.makeText(this,"사진을 가져오지 못했습니다",Toast.LENGTH_SHORT).show()
            }
        }
    }
}