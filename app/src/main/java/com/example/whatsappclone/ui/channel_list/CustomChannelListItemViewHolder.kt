package com.example.whatsappclone.ui.channel_list

import android.view.View
import android.widget.TextView
import com.getstream.sdk.chat.R
import com.getstream.sdk.chat.adapter.ChannelListItemViewHolder
import com.getstream.sdk.chat.rest.response.ChannelState
import java.text.SimpleDateFormat

class CustomChannelListItemViewHolder( v : View) : ChannelListItemViewHolder(v) {

    val dateFormat = SimpleDateFormat("MM/dd/YY")

    override fun configLastMessageDate(channelState: ChannelState) {
        val lastMessage = channelState.lastMessage
        val tvDate = itemView.findViewById(R.id.tv_date) as TextView
        if (lastMessage == null) {
            tvDate.text = ""
            return
        }
        if (lastMessage.isToday) tvDate.text = lastMessage.time else tvDate.text =
            dateFormat.format(lastMessage.createdAt)
    }
}