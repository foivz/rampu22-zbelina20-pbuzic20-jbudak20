package com.example.lostfound

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lostfound.Adapters.PostAdapter
import com.example.lostfound.entities.Post
import kotlinx.android.synthetic.main.post_feed.*

class PostsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_feed)
        val posts : ArrayList<Post> = ArrayList()
        for(i in 1..100){
            posts.add(Post("naslov","zbelina20" + i, "Lorem ipsum doler", "https://picsum.photos/600/300?random&" + i))
        }

        rvPosts.layoutManager = LinearLayoutManager(this)
        rvPosts.adapter = PostAdapter(posts)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.createPost){
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                Toast.makeText(this@PostsActivity, "Looking for $query", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?) : Boolean{
                Toast.makeText(this@PostsActivity, "Looking for $newText", Toast.LENGTH_SHORT).show()
                return false
            }
        })
        return true
    }
}