package com.example.kot6.kot13.chatlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kot6.databinding.ItemChatBinding

class ChatItemAdapter: ListAdapter<ChatItem, ChatItemAdapter.ViewHolder>(diffUtil) {
        inner class ViewHolder(private val binding: ItemChatBinding): RecyclerView.ViewHolder(binding.root) {
            fun bind(chatItem: ChatItem) {
                binding.senderTv.text=chatItem.senderId
                binding.messageTv.text=chatItem.message
            }
        }

        companion object{
            val diffUtil = object: DiffUtil.ItemCallback<ChatItem>(){
                override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
                    return oldItem == newItem
                }
                override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
                    return oldItem == newItem
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(ItemChatBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }
    }



