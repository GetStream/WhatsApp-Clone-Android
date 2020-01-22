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
        val tv_date = itemView.findViewById(R.id.tv_date) as TextView
        if (lastMessage == null) {
            tv_date.text = ""
            return
        }
        if (lastMessage.isToday) tv_date.text = lastMessage.time else tv_date.text =
            dateFormat.format(lastMessage.createdAt)
    }

}