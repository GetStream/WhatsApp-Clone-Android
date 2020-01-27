package com.example.whatsappclone.ui.channel

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.whatsappclone.R
import com.example.whatsappclone.databinding.FragmentChannelBinding
import com.getstream.sdk.chat.StreamChat
import com.getstream.sdk.chat.rest.User
import com.getstream.sdk.chat.view.MessageListView
import com.getstream.sdk.chat.viewmodel.ChannelViewModel
import com.getstream.sdk.chat.viewmodel.ChannelViewModelFactory


class ChannelFragment : Fragment(R.layout.fragment_channel) {

    private var viewModel: ChannelViewModel? = null
    private val args: ChannelFragmentArgs by navArgs()

    val TAG = ChannelFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_channel, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId === android.R.id.home) {
            Log.i(TAG, "Trying to press that freakign back button")
            findNavController().popBackStack(R.id.homeFragment, false)
            return true
        }
        return false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity : AppCompatActivity = activity as AppCompatActivity
        val client = StreamChat.getInstance(activity.application)
        val view = view

        // we're using data binding in this example
        val binding:FragmentChannelBinding = DataBindingUtil.setContentView(activity, R.layout.fragment_channel)
        // most the business logic of the chat is handled in the ChannelViewModel view model
        binding.lifecycleOwner = this

        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar!!.setDisplayShowHomeEnabled(true)
        activity.supportActionBar!!.setDisplayShowTitleEnabled(false)

        var channel = client.channel(args.channelType, args.channelId)
        val factory = ChannelViewModelFactory(activity.application, channel)
        val viewModel: ChannelViewModel by viewModels { factory }

        // connect the view model
        binding.viewModel = viewModel
        binding.messageList.setViewModel(viewModel!!, this)
        binding.messageInputView.setViewModel(viewModel!!, this)

        val messageList : MessageListView = view!!.findViewById(R.id.messageList)

        val otherUsers: List<User> = channel.channelState.otherUsers
        binding.avatarGroup.setChannelAndLastActiveUsers(channel, otherUsers, messageList.style)
        binding.channelName.text = channel.name

    }
}
