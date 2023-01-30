package com.example.lostfound.helpers

import android.util.Log
import com.example.lostfound.entities.Post
import com.example.lostfound.entities.PostSync
import com.google.firebase.database.*

object FirebaseService {
    private const val databaseRegionURL = "https://lostfound-c1e57-default-rtdb.europe-west1.firebasedatabase.app"
    private val dbRefPosts : DatabaseReference = FirebaseDatabase.getInstance(databaseRegionURL).getReference("posts")

    fun getAllPosts(callback : PostSync) {
        var postsList : MutableList<Post> = mutableListOf()
        dbRefPosts.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("DBError", "Neuspješno čitanje podataka: ", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for(p in snapshot.children) {
                        val post = p.getValue(Post::class.java)
                        postsList.add(post!!)
                    }
                    callback.getAllPosts(postsList)
                }
            }

        })
    }

}