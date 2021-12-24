package com.example.kot6.kot12

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.kot6.R

// Recyclerview에서 제공하는 listAdapter를 사용, ListAdapter의 속성으로 직접만든 CardItem,CardItemAdapter.ViewHolder를 넘겨줌
class CardItemAdapter : ListAdapter<CardItem, CardItemAdapter.ViewHolder>(diffUtil){
    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(cardItem:CardItem){
            view.findViewById<TextView>(R.id.nameTv).text = cardItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_card,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object: DiffUtil.ItemCallback<CardItem>(){
            override fun areItemsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
                return oldItem.userId == newItem.userId
            }
        }
    }
}
