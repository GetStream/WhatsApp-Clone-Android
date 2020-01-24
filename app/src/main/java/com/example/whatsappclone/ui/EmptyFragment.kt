package com.example.whatsappclone.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.whatsappclone.R
import com.example.whatsappclone.ui.home.EMPTY_TITLE
import com.example.whatsappclone.ui.home.TAB_TITLES

class EmptyFragment : Fragment(R.layout.fragment_empty) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(EMPTY_TITLE) }?.apply {
            val textView: TextView = view.findViewById(R.id.text1)
            textView.text = TAB_TITLES.getOrDefault(getInt(EMPTY_TITLE)-1, "camera")
        }
    }
}