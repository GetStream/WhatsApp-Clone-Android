package com.example.whatsappclone.ui.channel_list


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.whatsappclone.BuildConfig

import com.example.whatsappclone.R
import com.example.whatsappclone.databinding.FragmentChannelListBinding
import com.example.whatsappclone.ui.channel.ChannelFragmentArgs
import com.example.whatsappclone.ui.home.HomeFragment
import com.example.whatsappclone.ui.home.HomeFragmentDirections
import com.getstream.sdk.chat.StreamChat
import com.getstream.sdk.chat.adapter.ChannelListItemAdapter
import com.getstream.sdk.chat.enums.Filters
import com.getstream.sdk.chat.interfaces.ClientConnectionCallback
import com.getstream.sdk.chat.logger.StreamChatLogger
import com.getstream.sdk.chat.logger.StreamLoggerLevel
import com.getstream.sdk.chat.rest.User
import com.getstream.sdk.chat.viewmodel.ChannelListViewModel



class ChannelListFragment : Fragment(), ClientConnectionCallback {

    val TAG = ChannelListFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val logger = StreamChatLogger.Builder()
            .loggingLevel(if (BuildConfig.DEBUG) StreamLoggerLevel.ALL else StreamLoggerLevel.NOTHING)
            .build()

        // TODO: this code should be moved to a differnet lifecycle
        val configuration = StreamChat.Config(getActivity()!!.getApplicationContext(), "s2dxdhpxd94g")
        configuration.setLogger(logger)
        StreamChat.init(configuration)


        val client = StreamChat.getInstance(getActivity()!!.getApplication())
        val extraData = HashMap<String, Any>()
        extraData["name"] = "Paranoid Android"
        extraData["image"] = "https://bit.ly/2TIt8NR"
        val currentUser = User("empty-queen-5", extraData)
        // User token is typically provided by your server when the user authenticates
        client.setUser(
            currentUser,
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZW1wdHktcXVlZW4tNSJ9.RJw-XeaPnUBKbbh71rV1bYAKXp6YaPARh68O08oRnOU",this)
        Log.i(TAG, "ClientConnectionCallback SetUser started")

        // we're using data binding in this example
        val binding = FragmentChannelListBinding.inflate(inflater, container, false)

        // Specify the current fragment as the lifecycle owner
        binding.lifecycleOwner = getActivity()!!

        // most the business logic for chat is handled in the ChannelListViewModel view model
        val viewModel = ViewModelProviders.of(this).get(ChannelListViewModel::class.java)
        binding.viewModel = viewModel
        val  adapter =  ChannelListItemAdapter(activity)
       // adapter.setViewHolderFactory(CustomViewHolderFactory());
        binding.channelList.setViewModel(viewModel, this, adapter)

        // query all channels of type messaging
        val filter =
            Filters.and(Filters.eq("type", "messaging"), Filters.`in`("members", "empty-queen-5"))
        viewModel.setChannelFilter(filter)

        // click handlers for clicking a user avatar or channel
        binding.channelList.setOnChannelClickListener({ channel ->

            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToChannelFragment(channel.type, channel.id)
            )

        })
        binding.channelList.setOnUserClickListener({ user ->
            // open your user profile
        })


        return binding.root
    }

    override fun onSuccess(user: User?) {
        Log.i(TAG,String.format("ClientConnectionCallback Connection established for user %s", user!!.name) )
    }

    override fun onError(errMsg: String?, errCode: Int) {
        Log.e(TAG,
            String.format("ClientConnectionCallback Failed to establish websocket connection: message %s",
                errMsg
            )
        )
    }
}

class TabsAdapter(fragment: HomeFragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        var fragment: Fragment = EmptyFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt(ARG_OBJECT, position + 1)
        }
        if (position == 1) {
            fragment = ChannelListFragment()
        }
        return fragment
    }
}

private const val ARG_OBJECT = "object"

// Instances of this class are fragments representing a single
// object in our collection.
class EmptyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_empty, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val textView: TextView = view.findViewById(R.id.text1)
            textView.text = getInt(ARG_OBJECT).toString()
        }
    }
}
