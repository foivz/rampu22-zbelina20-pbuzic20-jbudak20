package com.example.lostfound

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lostfound.entities.Post

private const val TAG = "PostsActivity"
class PostsActivity : AppCompatActivity() {

    private lateinit var posts: MutableList<Post>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_feed)

        posts = mutableListOf();
    }
}