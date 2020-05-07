package com.example.whatsappclone.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.whatsappclone.R

private const val ARG_NANME = "ARG_NANME"
class EmptyFragment : Fragment(R.layout.fragment_empty) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.text1).text = arguments?.getString(ARG_NANME)
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String): EmptyFragment = EmptyFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_NANME, name)
            }
        }
    }
}