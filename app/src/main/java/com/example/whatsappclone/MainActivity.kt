package com.example.whatsappclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    //TODO private val navController by lazy { findNavController(R.id.navHostFragment) } //1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
