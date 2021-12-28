package com.example.kot6.kot13.chatlist

data class ChatListItem (
    val buyerId:String,
    val sellerId:String,
    val itemTitle:String,
    val key:Long
){
    constructor():this("","","",0) //빈 생성자
}