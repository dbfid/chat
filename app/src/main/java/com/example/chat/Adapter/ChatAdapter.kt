package com.example.chat.Adapter

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.chat.Extensions.isToday
import com.example.chat.Extensions.isYesterday
import com.example.chat.Extensions.toDateLong
import com.example.chat.R
import com.example.chat.model.ChatModel
import sasliderdemo.salmaan.ahmsal.com.flagchatadapter.FlagChatAdapter
import java.util.*
import kotlin.collections.ArrayList


class ChatAdapter(context: Context, private var list: ArrayList<Any>) : FlagChatAdapter(context){


    override val otherName: String get() = "상대방"

    override val listSize: Int get() = list.size

    override fun chatMessage(position: Int): String{
        return (list[position] as ChatModel).message
    }

    override fun messageTime(position: Int): String{
        return (list[position] as ChatModel).time
    }

    override fun isMe(position: Int): Boolean{
        return (list[position] as ChatModel).isMe
    }

    override fun animation(position: Int): Boolean{
        return (list[position] as ChatModel).animate
    }

    override fun setAnimationStatus(position: Int, animationStatus: Boolean) {
        (list[position] as ChatModel).animate = animationStatus
    }

    override fun isChatModel(position: Int): Boolean{
        return list[position] is ChatModel
    }

    override fun date(position: Int): String{
        val item= list[position] as Calendar
        return when{
            item.isToday() -> "오늘"
            item.isYesterday() -> "어제"
            else -> item.toDateLong()
        }
    }

    override fun colorOther(context: Context): Int{
        return ContextCompat.getColor(context, R.color.red)
    }

    override fun colorMe(context: Context): Int{
        return ContextCompat.getColor(context, R.color.cyan)
    }

    override fun onMessageLongClicked(position: Int) {
        Toast.makeText(context, "Long clicked on position $position", Toast.LENGTH_LONG).show()
    }

    override fun showTime(position: Int): Boolean {
        return super.showTime(position)
    }
}
