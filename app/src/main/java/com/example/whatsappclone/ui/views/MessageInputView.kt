package com.example.whatsappclone.ui.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.whatsappclone.databinding.ViewMessageInputBinding
import com.getstream.sdk.chat.interfaces.MessageSendListener
import com.getstream.sdk.chat.rest.Message
import com.getstream.sdk.chat.rest.interfaces.MessageCallback
import com.getstream.sdk.chat.rest.response.MessageResponse
import com.getstream.sdk.chat.view.MessageInputView
import com.getstream.sdk.chat.viewmodel.ChannelViewModel


/**
 * Basic message input view. Handles:
 * - Typing event
 * - Send message
 *
 * Doesn't handle more complex use cases like audio, video, file uploads, slash commands, editing text, threads or replies.
 * Stream's core API supports all of those though, so it's relatively easy to add
 *
 * When the user typed some text we change the microphone icon into a send button and hide the video button.
 */
class MessageInputView: ConstraintLayout
{

    constructor(context: Context) : super(context){
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet):    super(context, attrs){
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?,    defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    fun init(context: Context) {

    }
    // TODO

}