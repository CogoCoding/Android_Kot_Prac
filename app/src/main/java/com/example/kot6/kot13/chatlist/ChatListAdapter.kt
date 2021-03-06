package com.example.kot6.kot13.chatlist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kot6.databinding.ItemChatListBinding


class ChatListAdapter (val onitemClicked:(ChatListItem)->Unit): ListAdapter<ChatListItem, ChatListAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemChatListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(chatListItem: ChatListItem) {
            binding.root.setOnClickListener{
                onitemClicked(chatListItem)
            }
            binding.chatRoomTitleTv.text=chatListItem.itemTitle
            Log.e("chatroomtitle","chatListItem.itemTitle")
        }
    }

    companion object{
        val diffUtil = object: DiffUtil.ItemCallback<ChatListItem>(){
            override fun areItemsTheSame(oldItem: ChatListItem, newItem: ChatListItem): Boolean {
                return oldItem.key== newItem.key
            }
            override fun areContentsTheSame(oldItem: ChatListItem, newItem: ChatListItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChatListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}



