package com.example.kot6.kot11

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kot6.R
import com.example.kot6.databinding.ActivityMainKot11Binding
import com.example.kot6.kot11.api.BookService
import com.example.kot6.kot11.model.BestSellerDto
import com.example.kot6.kot11.model.SearchBookDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    companion object{
        const val tag:String ="MainActivity"
    }

    private lateinit var binding : ActivityMainKot11Binding
    private lateinit var adapter : BookAdapter
    private lateinit var bookService: BookService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainKot11Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initBookRecyclerView()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://book.interpark.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookService = retrofit.create(BookService::class.java)
        bookService.getBestSellerBooks(getString(R.string.interparkAPIKey))
            .enqueue(object : Callback<BestSellerDto>{
                override fun onResponse(
                    call: Call<BestSellerDto>,
                    response: Response<BestSellerDto>,
                ) {
                   // STEP1 성공처리
                    if(response.isSuccessful.not()){
                        Log.e(tag,"not! success")
                        return
                    }
                    adapter.submitList(response.body()?.books.orEmpty())
                    response.body()?.let{
                        Log.d(tag,it.toString())
                        it.books.forEach{book->
                            Log.d(tag,book.toString())
                        }
                        adapter.submitList(it.books)
                    }
                }
                override fun onFailure(call: Call<BestSellerDto>, t: Throwable) {
                    // TODO 실패처리
                    Log.e(tag,t.toString())
                }
            })
        binding.searchEditTx.setOnKeyListener { view, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN){
                search(binding.searchEditTx.text.toString())
                return@setOnKeyListener true
            }else{
                return@setOnKeyListener false
            }
        }
    }

    private fun search(keyword: String) {
        bookService.getBooksByName(getString(R.string.interparkAPIKey),keyword)
            .enqueue(object : Callback<SearchBookDto>{
                override fun onResponse(
                    call: Call<SearchBookDto>,
                    response: Response<SearchBookDto>
                ) {
                    // STEP1 성공처리
                    if(response.isSuccessful.not()){
                        Log.e(tag,"not! success")
                        return
                    }
                    response.body()?.let{
                        Log.d(tag,it.toString())
                        it.books.forEach{book->
                            Log.d(tag,book.toString())
                        }
                        adapter.submitList(it.books)
                    }
                }
                override fun onFailure(call: Call<SearchBookDto>, t: Throwable) {
                    // TODO 실패처리
                    Log.e(tag,t.toString())
                }
            })
    }

    fun initBookRecyclerView(){
        adapter = BookAdapter()
        binding.bookRecycleView.layoutManager = LinearLayoutManager(this)
        binding.bookRecycleView.adapter=adapter
    }

}