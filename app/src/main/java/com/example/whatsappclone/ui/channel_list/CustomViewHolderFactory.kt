package com.example.whatsappclone.ui.channel_list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.getstream.sdk.chat.MarkdownImpl
import com.getstream.sdk.chat.adapter.BaseChannelListItemViewHolder
import com.getstream.sdk.chat.adapter.ChannelListItemAdapter
import com.getstream.sdk.chat.adapter.ChannelViewHolderFactory
import com.getstream.sdk.chat.model.Channel

class CustomViewHolderFactory : ChannelViewHolderFactory() {

    private val CHANNEL_GENERAL = 0

    override fun getChannelViewType(channel: Channel?): Int {
        return CHANNEL_GENERAL
    }

    override fun createChannelViewHolder(
        adapter: ChannelListItemAdapter,
        parent: ViewGroup,
        viewType: Int
    ): BaseChannelListItemViewHolder? {
        return if (viewType == CHANNEL_GENERAL) {
            val style = adapter.style
            val v = LayoutInflater.from(parent.context)
                .inflate(style.channelPreviewLayout, parent, false)
            val holder = CustomChannelListItemViewHolder(v)
            holder.setStyle(style)
            holder.setMarkdownListener(MarkdownImpl.getMarkdownListener())
            holder.setChannelClickListener(adapter.channelClickListener)
            holder.setChannelLongClickListener(adapter.channelLongClickListener)
            holder.setUserClickListener(adapter.userClickListener)
            holder
        } else {
            null
        }
    }
}