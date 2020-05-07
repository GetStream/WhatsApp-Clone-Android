package com.example.whatsappclone.ui.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.whatsappclone.R
import com.example.whatsappclone.databinding.FragmentChannelBinding
import com.getstream.sdk.chat.StreamChat
import com.getstream.sdk.chat.rest.User
import com.getstream.sdk.chat.view.MessageListView
import com.getstream.sdk.chat.viewmodel.ChannelViewModel
import com.getstream.sdk.chat.viewmodel.ChannelViewModelFactory

class ChannelFragment : Fragment() {

    private val args: ChannelFragmentArgs by navArgs()
    lateinit var binding: FragmentChannelBinding

    // setup data binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // we're using data binding in this example
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_channel, container, false)
        return binding.root
    }

    // enable the options menu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_channel, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            findNavController().navigateUp()
            return true
        }
        return false
    }

    // setup the toolbar and viewmodel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity : AppCompatActivity = activity as AppCompatActivity

        // toolbar setup
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        val client = StreamChat.getInstance(activity.application)
        binding.lifecycleOwner = this
        val channel = client.channel(args.channelType, args.channelId)
        val factory = ChannelViewModelFactory(activity.application, channel)
        val viewModel: ChannelViewModel by viewModels { factory }

        // connect the view model
        binding.viewModel = viewModel
        binding.messageList.setViewModel(viewModel, this)
        binding.messageInputView.setViewModel(viewModel, this)

        view?.findViewById<MessageListView>(R.id.messageList)?.let {
            val otherUsers: List<User> = channel.channelState.otherUsers
            binding.avatarGroup.setChannelAndLastActiveUsers(channel, otherUsers, it.style)
        }

        binding.channelName.text = channel.name

    }
}
