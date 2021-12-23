package com.example.kot6.kot11

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.kot6.databinding.ActivityDetailBinding
import com.example.kot6.kot11.model.Book
import com.example.kot6.kot11.model.Review

class DetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "BookSearchDB"
        ).build()

        val model = intent.getParcelableExtra<Book>("bookModel")
        binding.titleTv.text = model?.title.orEmpty()

        Glide.with(binding.coverImageView.context)
            .load(model?.coverSmallUrl.orEmpty())
            .into(binding.coverImageView)

        Thread{
            val review = db.reviewDao().getOneReview(model?.id?.toInt()?:0)
            runOnUiThread{
                binding.reviewEditTv.setText(review.review.orEmpty())
            }
        }

        binding.saveBtn.setOnClickListener{
            Thread{
                db.reviewDao().saveReview(
                    Review(model?.id?.toInt()?:0,
                          binding.reviewEditTv.text.toString()
                    )
                )

            }
        }
    }
}