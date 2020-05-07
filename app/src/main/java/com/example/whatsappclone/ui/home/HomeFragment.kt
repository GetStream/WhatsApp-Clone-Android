package com.example.whatsappclone.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
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

private const val INDEX_OF_CAMERA = 0
private const val INDEX_OF_CHATS = 1
private const val INDEX_OF_STATUS = 2
private const val INDEX_OF_CALLS = 3
private const val NUM_TABS = 4
private val TAB_TITLES = mapOf(
    INDEX_OF_CAMERA to "camera",
    INDEX_OF_CHATS to "chats",
    INDEX_OF_STATUS to "status",
    INDEX_OF_CALLS to "calls"
)

class TabsAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = NUM_TABS
    override fun createFragment(position: Int): Fragment = if (position == INDEX_OF_CHATS) {
        ChannelListFragment()
    } else {
        EmptyFragment.newInstance(TAB_TITLES[position] ?: "")
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
        viewPager = fragmentView.findViewById(R.id.view_pager)
        viewPager.adapter = TabsAdapter(childFragmentManager, lifecycle)

        // connect the tabs and view pager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position == INDEX_OF_CAMERA) {
                tab.setIcon(R.drawable.ic_camera_alt_black_24dp)
                val colors = ResourcesCompat.getColorStateList(resources, R.color.tab_icon, activity.theme)
                tab.icon?.apply { DrawableCompat.setTintList(DrawableCompat.wrap(this), colors) }
            } else {
                tab.text = TAB_TITLES[position]
            }
            viewPager.setCurrentItem(tab.position, true)
        }.attach()
        tabLayout.getTabAt(INDEX_OF_CHATS)?.let { tabLayout.selectTab(it) }
    }
}
