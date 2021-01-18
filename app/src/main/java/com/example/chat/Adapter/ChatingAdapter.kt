package com.example.chat.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chat.R
import com.example.chat.data.Movie
import com.example.chat.data.profilemale

class ChatingAdapter : RecyclerView.Adapter<ChatingAdapter.ChatingItemHolder>() {
    private lateinit var callback: (profilemale) -> Unit
    private var items: ArrayList<profilemale> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatingItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_linear, parent, false
        )

        return ChatingItemHolder(view).also {
            it.itemView.setOnClickListener { position ->
                callback(items[it.adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: ChatingAdapter.ChatingItemHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


    class ChatingItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<TextView>(R.id.chatmessage_tv_nickname)
        private val chatmsg = itemView.findViewById<TextView>(R.id.chatmessage_tv_message)
        private val profile = itemView.findViewById<ImageView>(R.id.profile)
        private val year = itemView.findViewById<TextView>(R.id.chatmessage_tv_date)

        fun bind(chat: profilemale) {
            with(chat) {
                Glide.with(itemView).load(image)
                    .placeholder(R.drawable.ic_default)
                    .into(profile)



            }
        }
    }


}
