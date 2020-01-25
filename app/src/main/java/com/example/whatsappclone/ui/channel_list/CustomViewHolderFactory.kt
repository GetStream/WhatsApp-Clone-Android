package com.example.whatsappclone.ui.channel_list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.getstream.sdk.chat.adapter.BaseChannelListItemViewHolder
import com.getstream.sdk.chat.adapter.ChannelListItemAdapter
import com.getstream.sdk.chat.adapter.ChannelViewHolderFactory


/*
Make the view holder factory return CustomChannelListItemViewHolder
 */
class CustomViewHolderFactory : ChannelViewHolderFactory() {
    override fun createChannelViewHolder(
        adapter: ChannelListItemAdapter,
        parent: ViewGroup,
        viewType: Int
    ): BaseChannelListItemViewHolder? { // get the style object
        val style = adapter.style
        // inflate the layout specified in the style
        val v = LayoutInflater.from(parent.context)
            .inflate(style.channelPreviewLayout, parent, false)
        // configure the viewholder
        val holder = CustomChannelListItemViewHolder(v)
        configureHolder(holder, adapter)
        // return..
        return holder
    }
}