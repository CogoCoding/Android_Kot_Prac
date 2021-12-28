package com.example.kot6.kot13.chatlist

data class ChatItem (
    val senderId:String,
    val message:String,
){
    constructor():this("","")
}
