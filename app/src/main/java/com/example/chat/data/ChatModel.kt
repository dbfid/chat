package com.example.chat.data

import android.widget.ImageView

data class ChatModel (
    var message: String = "",

    var time: String = "",

    var isMe: Boolean = false,

    var animate: Boolean = true,
)