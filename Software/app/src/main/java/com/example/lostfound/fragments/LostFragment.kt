package com.example.lostfound.fragments


import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lostfound.CreateActivity
import com.example.lostfound.PostDetailActivity
import com.example.lostfound.R
import com.example.lostfound.adapters.PostAdapter
import com.example.lostfound.databinding.FragmentLostBinding
import com.example.lostfound.entities.Post
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.post_feed.*


class LostFragment : Fragment(), PostAdapter.ClickListener {

    private lateinit var dbRef : DatabaseReference
    private val databaseRegionURL = "https://lostfound-c1e57-default-rtdb.europe-west1.firebasedatabase.app"
    private lateinit var recyclerView : RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var posts : MutableList<Post>
    var searchPosts : ArrayList<Post> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bind = FragmentLostBinding.inflate(layoutInflater)

        bind.fabCreatePost.setOnClickListener{
            val intent = Intent(this@LostFragment.requireContext(), CreateActivity::class.java)
            startActivity(intent)
        }

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rvPosts)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        loadPost()

        super.onViewCreated(view, savedInstanceState)
        setupMenu()
    }



    private fun loadPost(){
        dbRef = FirebaseDatabase.getInstance(databaseRegionURL).getReference("posts")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("DBError", "Neuspješno čitanje podataka: ", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    posts = arrayListOf()
                    for (p in snapshot.children) {
                        val post = p.getValue(Post::class.java)
                        posts.add(post!!)
                    }
                    searchPosts.addAll(posts)
                    postAdapter = PostAdapter(this@LostFragment, searchPosts)
                    recyclerView.adapter = postAdapter
                }
            } //dohvat podataka
        })
    }

    override fun clickedItem(post: Post) {
        val intent = Intent(this@LostFragment.requireContext(), PostDetailActivity::class.java)
        intent.putExtra("username", post.username)
        intent.putExtra("title", post.title)
        intent.putExtra("description", post.text)
        intent.putExtra("imagePost", post.photo.toString())
        intent.putExtra("PostKey", post.id)
        startActivity(intent)
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
                val searchItem = menu.findItem(R.id.search)
                val searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView
                val searchText= searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
                searchText.hint = "Upišite naslov objave..."

                searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    @SuppressLint("NotifyDataSetChanged")
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
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}
