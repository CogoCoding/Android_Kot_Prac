package com.example.kot6.kot11.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kot6.databinding.ItemBookBinding
import com.example.kot6.kot11.adapter.BookAdapter.BookItemViewHolder
import com.example.kot6.kot11.model.Book

class BookAdapter(private val itemClickListener:(Book)->Unit): ListAdapter<Book, BookItemViewHolder>(
    diffUtil) { //diffutil로 중복제거

    inner class BookItemViewHolder(private val binding: ItemBookBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(bookModel:Book){
            binding.titleTv.text = bookModel.title
            binding.descriptionTv.text = bookModel.description

            binding.root.setOnClickListener{
                itemClickListener(bookModel)
            }

            Glide
                .with(binding.coverImageView.context)
                .load(bookModel.coverSmallUrl)
                .into(binding.coverImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        return BookItemViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object: DiffUtil.ItemCallback<Book>(){
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}