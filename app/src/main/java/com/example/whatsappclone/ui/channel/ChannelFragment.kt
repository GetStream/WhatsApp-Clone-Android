package com.example.whatsappclone.ui.channel

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.example.whatsappclone.R
import com.example.whatsappclone.databinding.FragmentChannelBinding
import com.getstream.sdk.chat.StreamChat
import com.getstream.sdk.chat.rest.User
import com.getstream.sdk.chat.view.AvatarGroupView
import com.getstream.sdk.chat.view.MessageListView
import com.getstream.sdk.chat.view.MessageListViewStyle
import com.getstream.sdk.chat.viewmodel.ChannelViewModel
import com.getstream.sdk.chat.viewmodel.ChannelViewModelFactory


class ChannelFragment : Fragment() {

    private var viewModel: ChannelViewModel? = null
    private var binding: FragmentChannelBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_channel, container, false)
        return layout
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_channel, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            findNavController().popBackStack()
        }
        return super.onOptionsItemSelected(menuItem)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity : AppCompatActivity = activity as AppCompatActivity
        val client = StreamChat.getInstance((activity as AppCompatActivity).application)
        val view = view

        // TODO: this should be handled by the viewmodel ideally
        val channelType = arguments!!.get("channel_type") as String
        val channelID = arguments!!.get("channel_id") as String

        // we're using data binding in this example
        binding = DataBindingUtil.setContentView(activity, R.layout.fragment_channel)
        // most the business logic of the chat is handled in the ChannelViewModel view model
        // TODO: check if the owner should be fragment or class
        binding!!.lifecycleOwner = this

        activity.setSupportActionBar(binding!!.toolbar)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar!!.setDisplayShowHomeEnabled(true)
        activity.supportActionBar!!.setDisplayShowTitleEnabled(false)

        var channel = client.channel(channelType, channelID)
        viewModel = ViewModelProviders.of(
            this,
            ChannelViewModelFactory(activity.application, channel)
        ).get(ChannelViewModel::class.java)


        // connect the view model
        // TODO: Remove ugly !!
        binding!!.viewModel = viewModel
        binding!!.messageList.setViewModel(viewModel!!, this)

        val messageList : MessageListView = view!!.findViewById(R.id.messageList)

        val otherUsers: List<User> = channel.channelState.otherUsers
        binding!!.avatarGroup.setChannelAndLastActiveUsers(channel, otherUsers, messageList.style)
        binding!!.channelName.text = channel.name
    }


}
