package com.example.lostfound

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.lostfound.adapters.MainPagerAdapter
import com.example.lostfound.databinding.ActivityPostsBinding
import com.example.lostfound.fragments.FoundFragment
import com.example.lostfound.fragments.LostFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PostsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPostsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val mainPagerAdapter = MainPagerAdapter(supportFragmentManager, lifecycle)

        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.drawable.ic_baseline_find_in_page_24,
                LostFragment::class
            )
        )
        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.drawable.ic_baseline_check_circle_24,
                FoundFragment::class
            )
        )
        super.onCreate(savedInstanceState)
        binding = ActivityPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewpager2 = findViewById<ViewPager2>(R.id.viewPager2)

        viewpager2.adapter = mainPagerAdapter
        TabLayoutMediator(tabLayout, viewpager2){ tab, position ->
            tab.setIcon(mainPagerAdapter.fragmentItems[position].iconRes)
        }.attach()

    }
}