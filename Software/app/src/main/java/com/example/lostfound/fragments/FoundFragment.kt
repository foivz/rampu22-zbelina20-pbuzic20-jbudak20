package com.example.lostfound.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lostfound.CreateActivity
import com.example.lostfound.PostDetailActivity
import com.example.lostfound.PostsActivity
import com.example.lostfound.R
import com.example.lostfound.adapters.PostAdapter
import com.example.lostfound.databinding.FragmentFoundBinding
import com.example.lostfound.entities.Post
import com.example.lostfound.entities.User
import com.google.firebase.database.*


class FoundFragment : Fragment(), PostAdapter.ClickListener {
    private lateinit var dbRef : DatabaseReference
    private val databaseRegionURL = "https://lostfound-c1e57-default-rtdb.europe-west1.firebasedatabase.app"
    private lateinit var recyclerView : RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var posts : MutableList<Post>
    private lateinit var loggedInUser : User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFoundBinding.inflate(layoutInflater)

        loggedInUser = (activity as PostsActivity).user
        Log.i("LOGGED_USER", loggedInUser.username.toString())

        binding.fabCreateFoundPost.setOnClickListener{
            val intent = Intent(this@FoundFragment.requireContext(), CreateActivity::class.java)
            intent.putExtra("user", loggedInUser)
            intent.putExtra("status", "pronadeno")
            intent.putExtra("posts", "postsFound")
            startActivity(intent)
        }

        binding.fabFilterFoundPost.setOnClickListener{
            filterpretrazivanje("filtriranje", "")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_foundPosts)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        loadPost()

        super.onViewCreated(view, savedInstanceState)
        setupMenu()
    }

    private fun loadPost(){
        dbRef = FirebaseDatabase.getInstance(databaseRegionURL).getReference("postsFound")
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
                    posts.sortByDescending { it.creationTimeMs }
                    postAdapter = PostAdapter(this@FoundFragment, posts)
                    recyclerView.adapter = postAdapter
                }
            } //dohvat podataka
        })
    }

    override fun clickedItem(post: Post) {
        val intent = Intent(this@FoundFragment.requireContext(), PostDetailActivity::class.java)
        intent.putExtra("username", post.username)
        intent.putExtra("title", post.title)
        intent.putExtra("description", post.text)
        intent.putExtra("imagePost", post.photo.toString())
        intent.putExtra("PostKey", post.id)
        intent.putExtra("logged_user", loggedInUser.username.toString())
        intent.putExtra("status", post.status)
        startActivity(intent)
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            //inflateanje menu stavki unutar menu-a i pretraživanje objava
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
                val searchItem = menu.findItem(R.id.search)
                val btnBack = menu.findItem(R.id.btn_back)
                btnBack.isVisible = false
                val searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView
                val searchText= searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
                searchText.hint = "Upišite naslov objave..."

                //listener za korisnikove radnje unutar searchViewa
                searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    //funkcija koja se poziva kada se promijeni tekst koji korisnik upiše
                    // te se u skladu s time mijenjaju objave koje su prikazane
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onQueryTextChange(newText: String?) : Boolean{
                        filterpretrazivanje("pretrazivanje", newText)
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

    private fun filterpretrazivanje(s: String, newText: String?) {
        if(s == "pretrazivanje"){
            if(newText.isNullOrEmpty()){
                postAdapter.searchData(posts) //prikazivanje svih objavu kada je unos pretraživanja prazan
                return
            }


            dbRef.orderByChild("title").addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val searchPost = ArrayList<Post>()
                        for (i in snapshot.children) {
                            val post = i.getValue(Post::class.java)
                            if(post?.title?.lowercase().toString().contains(newText.lowercase()))
                            {
                                searchPost.add(post!!)
                            }
                        }
                        postAdapter.searchData(searchPost)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }else if(s == "filtriranje"){
            val stavke = arrayOf("Tehnologija", "Odjeća/Obuća", "Razno")
            val builder = AlertDialog.Builder(requireActivity())
            with(builder)
            {
                create()
                setTitle("Filtriraj izgubljenu imovinu:")
                setItems(stavke) { _, which ->
                    dbRef.orderByChild("vrstaImovine").equalTo(stavke[which]).addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val filterPosts: ArrayList<Post> = ArrayList()
                            for (postSnapshot in dataSnapshot.children) {
                                val post = postSnapshot.getValue(Post::class.java)
                                filterPosts.add(post!!)
                            }
                            postAdapter.searchData(filterPosts)
                            Toast.makeText(requireActivity(), "Filtrirano po: " + stavke[which], Toast.LENGTH_SHORT).show()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Failed to read value
                        }
                    })
                }
                    .setPositiveButton("Poništi filter") {_, _ ->
                        postAdapter.searchData(posts)
                        Toast.makeText(requireActivity(), "Filter poništen", Toast.LENGTH_SHORT).show()
                    }
                show()
            }
        }
    }
}