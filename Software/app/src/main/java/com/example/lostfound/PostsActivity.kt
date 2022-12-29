package com.example.lostfound

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lostfound.Adapters.PostAdapter
import com.example.lostfound.entities.Post
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.post_feed.*

class PostsActivity : AppCompatActivity() {

    private lateinit var dbRef : DatabaseReference
    private val databaseRegionURL = "https://lostfound-c1e57-default-rtdb.europe-west1.firebasedatabase.app"
    var posts : ArrayList<Post> = ArrayList()
    var searchPosts : ArrayList<Post> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_feed)

        dbRef = FirebaseDatabase.getInstance(databaseRegionURL).getReference("posts")

        dbRef.addValueEventListener(object : ValueEventListener { //dohvat podataka
            override fun onCancelled(error: DatabaseError) {
                Log.w("DBError", "Neuspješno čitanje podataka: ", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for(p in snapshot.children) {
                        val post = p.getValue(Post::class.java)
                        posts.add(post!!)
                    }
                }
                searchPosts.addAll(posts)
            }
        })

        rvPosts.layoutManager = LinearLayoutManager(this)
        rvPosts.adapter = PostAdapter(searchPosts)
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
        val searchText= searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchText.hint = "Upišite naslov objave..."

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?) : Boolean{
                if(newText!!.isNotEmpty())
                {
                    searchPosts.clear()

                    val search = newText.lowercase()
                    posts.forEach(){
                        if(it.title?.lowercase()!!.contains(search))
                        {
                            searchPosts.add(it)
                        }
                    }
                    rvPosts.adapter?.notifyDataSetChanged()
                }
                else{
                    searchPosts.clear()
                    searchPosts.addAll(posts)
                    rvPosts.adapter?.notifyDataSetChanged()
                }
                return true
            }
        })
        return true
    }
}