package com.example.whatsappclone.ui.channel_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.whatsappclone.databinding.FragmentChannelListBinding
import com.example.whatsappclone.ui.home.HomeFragmentDirections
import com.getstream.sdk.chat.StreamChat
import com.getstream.sdk.chat.adapter.ChannelListItemAdapter
import com.getstream.sdk.chat.enums.Filters
import com.getstream.sdk.chat.rest.User
import com.getstream.sdk.chat.viewmodel.ChannelListViewModel

const val API_KEY = "s2dxdhpxd94g"
const val USER_ID = "empty-queen-5"
const val USER_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZW1wdHktcXVlZW4tNSJ9.RJw-XeaPnUBKbbh71rV1bYAKXp6YaPARh68O08oRnOU"

class ChannelListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        StreamChat.init(StreamChat.Config(inflater.context.applicationContext, API_KEY))

        val client = StreamChat.getInstance(inflater.context.applicationContext)
        val extraData = HashMap<String, Any>()
        extraData["name"] = "Paranoid Android"
        extraData["image"] = "https://bit.ly/2TIt8NR"

        // User token is typically provided by your server when the user authenticates
        client.setUser(User(USER_ID, extraData), USER_TOKEN)

        // we're using data binding in this example
        val binding = FragmentChannelListBinding.inflate(inflater, container, false)

        // Specify the current activity as the lifecycle owner
        binding.lifecycleOwner = this

        // most the business logic for chat is handled in the ChannelListViewModel view model
        val viewModel: ChannelListViewModel by viewModels()
        binding.viewModel = viewModel
        val  adapter =  ChannelListItemAdapter(activity)
        adapter.setViewHolderFactory(CustomViewHolderFactory())
        binding.channelList.setViewModel(viewModel, this, adapter)

        // query all channels of type messaging
        val filter =
            Filters.and(Filters.eq("type", "messaging"), Filters.`in`("members", "empty-queen-5"))
        viewModel.setChannelFilter(filter)

        // click handlers for clicking a user avatar or channel
        binding.channelList.setOnChannelClickListener { channel ->
            findNavController().navigate(
                HomeFragmentDirections.navHomeToChannel(channel.type, channel.id)
            )
        }

        return binding.root
    }
}


