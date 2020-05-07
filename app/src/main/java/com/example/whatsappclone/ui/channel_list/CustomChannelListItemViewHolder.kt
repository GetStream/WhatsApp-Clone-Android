package com.example.whatsappclone.ui.channel_list

import android.view.View
import android.widget.TextView
import com.getstream.sdk.chat.R
import com.getstream.sdk.chat.adapter.ChannelListItemViewHolder
import com.getstream.sdk.chat.rest.response.ChannelState
import java.text.SimpleDateFormat
import java.util.Locale

class CustomChannelListItemViewHolder(v: View) : ChannelListItemViewHolder(v) {

    private val dateFormat = SimpleDateFormat("MM/dd/yy", Locale.getDefault())
    private val tvDate: TextView by lazy { itemView.findViewById<TextView>(R.id.tv_date) }

    override fun configLastMessageDate(channelState: ChannelState) {
        val lastMessage = channelState.lastMessage
        tvDate.text = when {
            lastMessage == null -> ""
            lastMessage.isToday -> lastMessage.time
            else -> dateFormat.format(lastMessage.createdAt)
        }
    }
}