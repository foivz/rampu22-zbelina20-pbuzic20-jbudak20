package com.example.lostfound.Adapters


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

class PostAdapter(val posts: ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val username : TextView = view.findViewById(R.id.username)
        val text : TextView = view.findViewById(R.id.text)
        val photo: ImageView = view.findViewById(R.id.photo)
        val title : TextView = view.findViewById(R.id.title)
        val currentTime : TextView = view.findViewById(R.id.tvRelativeTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.post_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = posts[position].title
        holder.currentTime.text = DateUtils.getRelativeTimeSpanString(posts[position].creationTimeMs)
        holder.username.text = posts[position].username
        holder.text.text = posts[position].text
        Picasso.get().load(posts[position].photo).into(holder.photo)
    }

    override fun getItemCount(): Int = posts.size

}