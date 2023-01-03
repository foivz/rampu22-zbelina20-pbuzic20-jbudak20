package com.example.lostfound

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lostfound.adapters.CommentAdapter
import com.example.lostfound.entities.Comment
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post_detail.*

class PostDetailActivity : AppCompatActivity() {
    private lateinit var imagePost : ImageView
    private lateinit var imageUser : ImageView
    private lateinit var postTitle : TextView
    private lateinit var postDescription: TextView
    private lateinit var postComment: EditText
    private lateinit var btnAddComment: Button
    private lateinit var postUsername : TextView
    private lateinit var firebaseDatabase : DatabaseReference
    private lateinit var PostKey : String
    private lateinit var recyclerView : RecyclerView
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var comments : MutableList<Comment>
    private lateinit var btnEdit : Button
    private lateinit var status : TextView
    private lateinit var loggedInUser : String
    private val databaseRegionURL = "https://lostfound-c1e57-default-rtdb.europe-west1.firebasedatabase.app"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        initVars()

        loggedInUser = intent.getStringExtra("logged_user")!!
        val img = intent.getStringExtra("imagePost")
        Picasso.get().load(img).into(imagePost)
        val description = intent.getStringExtra("description")
        postDescription.text = description
        val title = intent.getStringExtra("title")
        postTitle.text = title
        val username = intent.getStringExtra("username")
        postUsername.text = username
        PostKey = intent.getStringExtra("PostKey").toString()
        status.text = intent.getStringExtra("status")

        if(loggedInUser != username) {
            btnEdit.visibility = View.INVISIBLE
        }

        initRc()

        btnAddComment.setOnClickListener{
            if(provjeriUnos()) {
                dodajKomentar()
            }
        }
    }

    private fun initRc() {
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        val commentRef : DatabaseReference = FirebaseDatabase.getInstance(databaseRegionURL).getReference("comments").child(PostKey)

        commentRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("DBError", "Neuspješno čitanje podataka: ", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    comments = arrayListOf()
                    for (p in snapshot.children) {
                        val comment = p.getValue(Comment::class.java)
                        comments.add(comment!!)
                    }
                    commentAdapter = CommentAdapter(comments)
                    recyclerView.adapter = commentAdapter
                }
            } //dohvat podataka
        })
    }

    private fun provjeriUnos(): Boolean {
        if(postComment.text.isEmpty()){
            Toast.makeText(this, "Niste unijeli komentar", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun initVars() {
        imagePost = findViewById(R.id.iv_postDetail)
        postTitle = findViewById(R.id.post_detail_title)
        postDescription = findViewById(R.id.post_detail_description)
        postComment = findViewById(R.id.post_detail_comment)
        btnAddComment = findViewById(R.id.btn_post_detail_add)
        postUsername = findViewById(R.id.post_detail_username)
        imageUser = findViewById(R.id.iv_post_detail_user)
        imageUser.setImageResource(R.drawable.ic_baseline_account_circle_24)
        firebaseDatabase = FirebaseDatabase.getInstance(databaseRegionURL).getReference("comments")
        recyclerView = findViewById(R.id.rv_comments)
        btnEdit = findViewById(R.id.btn_edit)
        status = findViewById(R.id.et_status)
    }

    private fun dodajKomentar() {
        val id = firebaseDatabase.push().key!!
        val comment = Comment(
            postComment.text.toString(),
            loggedInUser
        )
        firebaseDatabase.child(PostKey).child(id).setValue(comment)
            .addOnSuccessListener {
                Toast.makeText(this, "Unijet je novi komentar", Toast.LENGTH_SHORT).show()
                postComment.text.clear()
            }.addOnFailureListener {
                Toast.makeText(this, "Komentar nije uspješno dodan", Toast.LENGTH_SHORT).show()
            }
        //finish()
    }
}