package com.example.lostfound.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lostfound.CreateActivity
import com.example.lostfound.PostDetailActivity
import com.example.lostfound.PostsActivity
import com.example.lostfound.R
import com.example.lostfound.adapters.PostAdapter
import com.example.lostfound.databinding.FragmentLostBinding
import com.example.lostfound.entities.Post
import com.example.lostfound.entities.User
import com.google.firebase.database.*


class LostFragment : Fragment(), PostAdapter.ClickListener {

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
        val bind = FragmentLostBinding.inflate(layoutInflater)

        //Dohvaća se prijavljen korisnik
        loggedInUser = (activity as PostsActivity).user
        Log.i("LOGGED_USER", loggedInUser.username.toString())

        //Floating action button, klikom na njega otvara se CreateActivity
        bind.fabCreatePost.setOnClickListener{
            val intent = Intent(this@LostFragment.requireContext(), CreateActivity::class.java)
            intent.putExtra("user", loggedInUser)
            startActivity(intent)
        }

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rvPosts)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        loadPost()
    }

    //Funkcija koja dohvaća sve objave iz baze
    //Dodaje se instanca te referenca u bazi na posts
    //Za povezivanje dohvaćenih podataka s layoutom koristi se PostAdapter
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
                    postAdapter = PostAdapter(this@LostFragment, posts)
                    recyclerView.adapter = postAdapter
                }
            } //dohvat podataka
        })
    }

    //Funkcija koja se koristi kako bi se dohvatio selektirana objava
    //Korisnik kada klikne na objavu vodi ga na aktivnost PostDetail
    //U intentu se dodaje podaci o objavi
    override fun clickedItem(post: Post) {
        val intent = Intent(this@LostFragment.requireContext(), PostDetailActivity::class.java)
        intent.putExtra("username", post.username)
        intent.putExtra("title", post.title)
        intent.putExtra("description", post.text)
        intent.putExtra("imagePost", post.photo.toString())
        intent.putExtra("PostKey", post.id)
        intent.putExtra("logged_user", loggedInUser.username.toString())
        intent.putExtra("status", post.status)
        startActivity(intent)
    }
}
