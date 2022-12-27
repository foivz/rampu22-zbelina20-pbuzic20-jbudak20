package com.example.lostfound.adapters

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lostfound.R
import com.example.lostfound.entities.Post
import com.squareup.picasso.Picasso

class PostAdapter(private var clickListener: ClickListener, private var listPosts : MutableList<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private lateinit var context: Context
    //private var listPosts : List<Post> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        context = parent.context
        val postview: View = LayoutInflater.from(context).inflate(R.layout.post_row, parent, false)
        return PostViewHolder(postview)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = listPosts.get(position)
        holder.title.text = listPosts[position].title
        holder.currentTime.text = DateUtils.getRelativeTimeSpanString(listPosts[position].creationTimeMs)
        holder.username.text = listPosts[position].username
        holder.text.text = listPosts[position].text
        Picasso.get().load(listPosts[position].photo).into(holder.photo)

        holder.itemView.setOnClickListener {
            clickListener.clickedItem(post)
        }
    }

    interface ClickListener{
        fun clickedItem(post : Post)
    }

    override fun getItemCount(): Int = listPosts.size

    class PostViewHolder(view: View): RecyclerView.ViewHolder(view){
        val username : TextView
        val text : TextView
        val photo: ImageView
        val title : TextView
        val currentTime : TextView

        init {
            username = view.findViewById(R.id.username)
            text = view.findViewById(R.id.text)
            photo = view.findViewById(R.id.photo)
            title = view.findViewById(R.id.title)
            currentTime = view.findViewById(R.id.tvRelativeTime)
        }
    }

}