package com.example.lostfound.fragments


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
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
            intent.putExtra("status", "izgubljeno")
            intent.putExtra("posts", "posts")
            startActivity(intent)
        }

        //klik na gumb fabFilter poziva se funkcija filtriranje pomoću koje se izvršava
        // filtriranje objava po odabranom kriteriju
        bind.fabFilter.setOnClickListener{
            filtriranje()
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

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            //inflateanje menu stavki unutar menu-a i pretraživanje objava
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
                val searchItem = menu.findItem(R.id.search)
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
                            val searchList : ArrayList<Post> = ArrayList()
                            val search = newText?.lowercase().toString()
                            posts.forEach {
                                if(it.title?.lowercase()!!.contains(search))
                                {
                                    searchList.add(it)
                                }
                            }
                            postAdapter.searchData(searchList)
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

    //funkcija pomoću koje se stvara dijalog sa svim potrebnim informacijama
    //te se filtriraju objave
    @SuppressLint("NotifyDataSetChanged")
    private fun filtriranje() {
        val stavke = arrayOf("Tehnologija", "Odjeća/Obuća", "Razno")
        val builder = AlertDialog.Builder(requireActivity())
        with(builder)
        {
            create()
            setTitle("Filtriraj izgubljenu imovinu:")
            setItems(stavke) { _, which ->
                val filterPosts : ArrayList<Post> = ArrayList()
                posts.forEach {
                    if(it.vrstaImovine?.contains(stavke[which]) == true)
                    {
                        filterPosts.add(it)
                    }
                }
                postAdapter.searchData(filterPosts)
                Toast.makeText(requireActivity(), "Filtrirano po: " + stavke[which], Toast.LENGTH_SHORT).show()
            }
                .setPositiveButton("Poništi filter") {_, _ ->
                    postAdapter.searchData(posts)
                    Toast.makeText(requireActivity(), "Filter poništen", Toast.LENGTH_SHORT).show()
                }
            show()
        }
    }
}
