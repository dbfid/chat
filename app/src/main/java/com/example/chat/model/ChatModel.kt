package com.example.chat.model

data class ChatModel (
    var message: String = "",
    var time: String = "",
    var isMe: Boolean = false,
    var animate: Boolean = true
)