package com.example.whatsappclone.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.whatsappclone.R
import com.example.whatsappclone.ui.EmptyFragment
import com.example.whatsappclone.ui.channel_list.ChannelListFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


const val EMPTY_TITLE = "empty_title"
val TAB_TITLES = mapOf( 1 to "chats", 2 to "status", 3 to "calls")


class TabsAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment
        if (position == 1) {
            fragment = ChannelListFragment()
        } else {
            fragment = EmptyFragment()
            fragment.arguments = Bundle().apply {
                putInt(EMPTY_TITLE, position + 1)
            }
        }
        return fragment
    }
}


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
        val fragmentView = requireNotNull(view) {"View should not be null when calling onActivityCreated"}

        val toolbar: Toolbar = fragmentView.findViewById(R.id.toolbar)
        activity.setSupportActionBar(toolbar)

        val tabLayout : TabLayout = fragmentView.findViewById(R.id.tabs)
        val chatTab: TabLayout.Tab = tabLayout.getTabAt(2)!!
        viewPager = fragmentView.findViewById(R.id.view_pager)
        viewPager.adapter = TabsAdapter(childFragmentManager, lifecycle)

        // connect the tabs and view pager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
            if (position == 0) {
                tab.setIcon(R.drawable.ic_camera_alt_black_24dp)
            }
            viewPager.setCurrentItem(tab.position, true)
        }.attach()
        tabLayout.selectTab(chatTab)

        // handle tint for the camera icon
        val colors = resources.getColorStateList(R.color.tab_icon, activity.theme)

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
