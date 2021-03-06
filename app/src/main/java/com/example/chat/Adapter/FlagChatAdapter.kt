package com.example.chat.Adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import kotlinx.android.synthetic.main.cell_chat.view.*
import kotlinx.android.synthetic.main.cell_chat_date.view.*

/**
 * Created by salmaanahmed on 04/09/2018.
 * Extend your recycler view adapter with this adapter and voila!
 * You have your animated chat adapter working.
 */
abstract class FlagChatAdapter(val context: Context) : RecyclerView.Adapter<FlagChatAdapter.ViewHolder>() {

    private val chatView = 0 //View types - Chat View
    private val dateView = 1 //View types - Date View

    /**
     * return chat message on the position passed as parameter
     */
    abstract fun chatMessage(position: Int): String

    /**
     * return time of message as string format on the position passed as parameter
     */
    abstract fun messageTime(position: Int): String

    /**
     * return message sender on the position passed as parameter
     * if its you, return true
     */
    abstract fun isMe(position: Int): Boolean

    /**
     * you must have a variable of animation in the object i.e. if you want to animate or not
     */
    abstract fun animation(position: Int): Boolean

    /**
     * the animation variable must be set to false when animation is performed once
     * otherwise flags will animate on every scroll
     */
    abstract fun setAnimationStatus(position: Int, animationStatus: Boolean)

    /**
     * You can implement whatever you want onLongClick event
     */
    abstract fun onMessageLongClicked(position: Int)

    /**
     * Name of the sender
     */
    abstract val otherName: String

    /**
     * You shall simply return list.size
     */
    abstract val listSize: Int

    /**
     * OPTIONAL:
     * you can change flag color of your chat message 나의 채팅 색 바꾸는거 가능
     */
    open fun colorMe(context: Context): Int {
        return ContextCompat.getColor(context, R.color.orange)
    }

    /**
     * OPTIONAL:
     * you can change flag color of other person's chat message 다른 사람의 채팅의 색을 바꾸는거 가능
     */
    open fun colorOther(context: Context): Int {
        return ContextCompat.getColor(context, R.color.green)
    }

    /**
     * OPTIONAL:
     * If your list contains some other data type from chat model i.e. date 만약 너의 리스타가 포함한다면 어떤 다른 쳇 모델의 데이터 타입으로 부터
     * you must return false in that case so adapter can check for date and display the date 너느 해야한다 반환을 펄스 그것은
     * chat message functions will not be called on the position if !isChatModel
     */
    //목록에 채팅 모델의 다른 데이터 유형(예: 날짜)이 포함되어 있는 경우
    // 어댑터가 날짜를 확인하고 날짜를 표시할 수 있도록 이 경우 False를 반환해야 합니다.
    //!iChatModel일 경우 위치에서 채팅 메세지 기능이 호출되지 않는다.
    open fun isChatModel(position: Int): Boolean {
        return true
    }

    /**
     * OPTIONAL:
     * If !isChatModel adapter will display date place holder
     * it will be populated with the string returned by this function
     * you shall return the date or strings such as TODAY or YESTERDAY in this function
     */
    open fun date(position: Int): String {
        return ""
    }

    /**
     * OPTIONAL:
     * This current code will not display time if same user sends message in same time
     * You may override this method to change the logic or simply return true if you always want to show time
     */
    open fun showTime(position: Int): Boolean {
        if (position > 0) {
            if (isChatModel(position) && isChatModel(position - 1)) {
                if (isMe(position) && isMe(position - 1) && messageTime(position) == messageTime(position - 1)) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * Overriden function to bind view holder
     * Decide to show the chat view or date view
     * Populate data in views and show animation
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (isChatModel(position)) { // If it is chat model
            val chatMessage = chatMessage(position)
            val showTime = showTime(position)
            val messageTime = messageTime(position)
            val isMe = isMe(position)
            val animate = animation(position)

            // Bind data with viewholder
            holder.bindData(chatMessage, showTime, messageTime, isMe, colorMe(context), colorOther(context), otherName)


            // If previous message is from same person, hide the flag
            if (position > 0 && isChatModel(position - 1) && isMe == isMe(position - 1)) { //Hide Flag
                holder.itemView.name.visibility = View.GONE
                holder.itemView.flagPadding.visibility = View.GONE
            } else { // Otherwise show flag
                if (animate) {// If flag is not animated before
                    holder.itemView.name.visibility = View.INVISIBLE
                    holder.itemView.flagPadding.visibility = View.VISIBLE
                    flagAnimation(holder.itemView, position)
                } else { // Do not animate flag if its animated before i.e. if user is scrolling list
                    holder.itemView.name.visibility = View.VISIBLE
                    holder.itemView.flagPadding.visibility = View.VISIBLE
                }
            }
            holder.itemView.rootView.setOnLongClickListener {
                onMessageLongClicked(position)
                return@setOnLongClickListener true
            }
        } else {
            // If it is date, populate the textview
            holder.itemView.date.text = date(position)
        }
    }

    /**
     * Item view type on basis of chat model
     */
    override fun getItemViewType(position: Int): Int {
        return if (isChatModel(position)) chatView else dateView
    }

    /**
     * Create view holder for both type of objects
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == chatView) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_chat, parent, false)
            ViewHolder(v)
        } else {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_chat_date, parent, false)
            ViewHolder(v)
        }
    }

    /**
     * Number of messages
     */
    override fun getItemCount(): Int {
        return listSize
    }

    /**
     * ViewHolder to cache the cells to optimize performance
     * Populate the data, set colors, flags, animation etc.
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(message: String, showTime: Boolean = true, time: String, isMe: Boolean, colorMe: Int, colorOther: Int, otherName: String) {
            setSides(isMe, colorMe, colorOther)
            setMessage(message, showTime, time, isMe, otherName)
        }

        // Set sides, colors, and views according to sender
        private fun setSides(isMe: Boolean, colorMe: Int, colorOther: Int) {
            if (isMe) {
                (itemView.line.layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                (itemView.name.layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                (itemView.message.layoutParams as LinearLayout.LayoutParams).gravity = Gravity.RIGHT
                (itemView.message.layoutParams as LinearLayout.LayoutParams).leftMargin = itemView.context.resources.getDimensionPixelSize(R.dimen.message_padding)
                (itemView.message.layoutParams as LinearLayout.LayoutParams).rightMargin = 0
                itemView.line.setBackgroundColor(colorMe)
                itemView.name.setBackgroundColor(colorMe)
                itemView.linearLayout.gravity = Gravity.RIGHT
                itemView.name.gravity = Gravity.RIGHT
                itemView.time.gravity = Gravity.RIGHT
            } else {
                (itemView.line.layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0)
                (itemView.name.layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0)
                (itemView.message.layoutParams as LinearLayout.LayoutParams).gravity = Gravity.LEFT
                (itemView.message.layoutParams as LinearLayout.LayoutParams).rightMargin = itemView.context.resources.getDimensionPixelSize(R.dimen.message_padding)
                (itemView.message.layoutParams as LinearLayout.LayoutParams).leftMargin = 0
                itemView.line.setBackgroundColor(colorOther)
                itemView.name.setBackgroundColor(colorOther)
                itemView.linearLayout.gravity = Gravity.LEFT
                itemView.name.gravity = Gravity.LEFT
                itemView.time.gravity = Gravity.LEFT
            }
        }

        // Populate message, date, sender in the views
        private fun setMessage(message: String, showTime: Boolean, time: String, isMe: Boolean, otherName: String) {
            itemView.message.text = message
            itemView.time.text = time
            itemView.name.text = if (isMe) "나" else otherName
            if (showTime) itemView.time.visibility = View.VISIBLE
            else itemView.time.visibility = View.GONE
        }
    }

    /**
     * Flag animation on the views
     */
    private fun flagAnimation(itemView: View, position: Int) {
        val translateAnim = AnimationUtils.loadAnimation(itemView.context, R.anim.item_animation_from_bottom)
        translateAnim.fillAfter = true
        translateAnim.isFillEnabled = true
        translateAnim.fillBefore = false
        translateAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                itemView.linearLayout.alpha = 0f
                itemView.name.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation) {
                itemView.linearLayout.animate().alpha(1.0f).duration = 1000
                setAnimationStatus(position, false)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        itemView.name.startAnimation(translateAnim)
    }


}