package com.example.whatsappclone.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.whatsappclone.R
import com.example.whatsappclone.ui.channel_list.TabsAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


// TODO: Use a viewmodel
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity : AppCompatActivity = activity as AppCompatActivity
        val view = view

        val toolbar: Toolbar = view!!.findViewById(R.id.toolbar)
        activity.setSupportActionBar(toolbar)


        val tabLayout : TabLayout = view.findViewById(R.id.tabs)
        val chatTab: TabLayout.Tab = tabLayout.getTabAt(2)!!
        viewPager = view.findViewById(R.id.view_pager)
        viewPager.adapter = TabsAdapter(this)
        val tabTitles = mapOf(0 to "", 1 to "chats", 2 to "status", 3 to "calls")

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
            if (position == 0) {
                tab.setIcon(R.drawable.ic_camera_alt_black_24dp)
            }
            viewPager.setCurrentItem(tab.position, true)
        }.attach()

        tabLayout.selectTab(chatTab)

        val colors = resources.getColorStateList(R.color.tab_icon, activity!!.theme)

        for (i in 0 until tabLayout.tabCount) {
            val tab: TabLayout.Tab = tabLayout.getTabAt(i)!!
            var icon = tab.icon
            if (icon != null) {
                icon = DrawableCompat.wrap(icon)
                DrawableCompat.setTintList(icon, colors)
            }
        }
    }
}
