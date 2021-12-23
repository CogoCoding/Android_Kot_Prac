package com.example.kot6.kot11

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.kot6.R
import com.example.kot6.databinding.ActivityMainKot11Binding
import com.example.kot6.kot11.adapter.BookAdapter
import com.example.kot6.kot11.adapter.HistoryAdapter
import com.example.kot6.kot11.api.BookService
import com.example.kot6.kot11.model.BestSellerDto
import com.example.kot6.kot11.model.History
import com.example.kot6.kot11.model.SearchBookDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainKot11Binding
    private lateinit var adapter : BookAdapter
    private lateinit var historyAdapter : HistoryAdapter
    private lateinit var bookService: BookService

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainKot11Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initBookRecyclerView()
        initHistoryRecyclerView()
        initSearchEditText()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "BookSearchDB"
        ).build()

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
                        Log.e("failure","not! success")
                        return
                    }
//                    adapter.submitList(response.body()?.books.orEmpty())
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
                    Log.e("failure",t.toString())
                }
            })
        binding.searchEditTx.setOnKeyListener { view, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN){
                Log.d("edittext",binding.searchEditTx.text.toString())
                search(binding.searchEditTx.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false

        }
    }

    private fun search(keyword: String) {
        bookService.getBooksByNamefull()
            .enqueue(object : Callback<SearchBookDto>{
                override fun onResponse(
                    call: Call<SearchBookDto>,
                    response: Response<SearchBookDto>
                ) {
                    Log.d("error_search",keyword)
                    // STEP1 성공처리
                    hideHistoryView()
                    saveSearchKeyword(keyword)
                    if(response.isSuccessful.not()){
                        Log.e("failure","not! success")
                        return
                    }
//                    adapter.submitList(response.body()?.books.orEmpty())
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
                    hideHistoryView()
                    Log.e("failure",t.toString())
                }
            })

        bookService.getBooksByName(getString(R.string.interparkAPIKey),keyword)
            .enqueue(object : Callback<SearchBookDto>{
                override fun onResponse(
                    call: Call<SearchBookDto>,
                    response: Response<SearchBookDto>
                ) {
                    Log.d("error_search",keyword)
                    // STEP1 성공처리
                    if(response.isSuccessful.not()){
                        Log.e("failure","not! success")
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
                override fun onFailure(call: Call<SearchBookDto>, t: Throwable) {
                    // TODO 실패처리
                    Log.e("failure",t.toString())
                }
            })
    }

    private fun initBookRecyclerView(){
        adapter = BookAdapter(itemClickListener = {
            val intent = Intent(this,DetailActivity::class.java)
            intent.putExtra("bookModel",it) //직렬화된 클래스라 통째로 넘김
            startActivity(intent)
        })
        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookRecyclerView.adapter=adapter
    }
    private fun initHistoryRecyclerView(){
        historyAdapter = HistoryAdapter(historyDeleteClickedListener = {
            deleteSearchKeyword(it)
        })
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter=historyAdapter
        initSearchEditText()
    }

    private fun initSearchEditText() {
        // key리스너
        binding.searchEditTx.setOnKeyListener { view, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN) {
                Log.d("edittext", binding.searchEditTx.text.toString())
                search(binding.searchEditTx.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        // touch리스터
        binding.searchEditTx.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                showHistoryView()
            }
            return@setOnTouchListener false
        }
    }

    private fun showHistoryView(){
        Thread{
            val keywords = db.historyDao().getAll().reversed()

            runOnUiThread{
                binding.historyRecyclerView.isVisible=true
                historyAdapter.submitList(keywords.orEmpty())
            }
        }.start()
        binding.historyRecyclerView.isVisible=true
    }

    private fun hideHistoryView(){
        binding.historyRecyclerView.isVisible=false
    }

    private fun saveSearchKeyword(keyword:String){
        Thread{
            db.historyDao().insertHistory(History(null,keyword))
        }.start()
    }

    private fun deleteSearchKeyword(keyword:String){
        Thread{
            db.historyDao().delete(keyword)
            showHistoryView()
        }.start()
    }
    companion object{
        const val tag:String ="MainActivity"
    }

}