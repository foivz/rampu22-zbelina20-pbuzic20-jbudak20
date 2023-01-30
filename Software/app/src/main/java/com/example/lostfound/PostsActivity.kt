package com.example.lostfound

import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.lostfound.adapters.MainPagerAdapter
import com.example.lostfound.databinding.ActivityPostsBinding
import com.example.lostfound.entities.User
import com.example.lostfound.fragments.FoundFragment
import com.example.lostfound.fragments.LostFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.messaging.FirebaseMessaging

class PostsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPostsBinding
    lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        val mainPagerAdapter = MainPagerAdapter(supportFragmentManager, lifecycle)
        val firebaseMessaging = FirebaseMessaging.getInstance()
        firebaseMessaging.subscribeToTopic("new_posts")

        user = if(VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("user", User::class.java) as User
        }
        else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<User>("user") as User
        }

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