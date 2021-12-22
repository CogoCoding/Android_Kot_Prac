package com.example.kot6.kot11

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kot6.R
import com.example.kot6.kot11.api.BookService
import com.example.kot6.kot11.model.BestSellerDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    companion object{
        const val tag:String ="MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kot11)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://book.interpark.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val bookService = retrofit.create(BookService::class.java)
        bookService.getBestSellerBooks( "80E1EF53A3AC56C2CB65689FC0F0EE8A6BBFBB223A0B687773A61BEE892B42E1")
            .enqueue(object : Callback<BestSellerDto>{
                override fun onResponse(
                    call: Call<BestSellerDto>,
                    response: Response<BestSellerDto>,
                ) {
                   // TODO 성공처리
                    if(response.isSuccessful.not()){
                        Log.e(tag,"not! success")
                        return
                    }
                    response.body()?.let{
                        Log.d(tag,it.toString())

                        it.books.forEach{book->
                            Log.d(tag,book.toString())
                        }
                    }
                }

                override fun onFailure(call: Call<BestSellerDto>, t: Throwable) {
                    // TODO 실패처리
                }

            })
    }
}