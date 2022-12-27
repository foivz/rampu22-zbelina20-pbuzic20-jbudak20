package com.example.lostfound.adapters

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.lostfound.R
import com.example.lostfound.entities.Comment

class CommentAdapter(private var listComment: MutableList<Comment>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val commentView : View = LayoutInflater.from(parent.context).inflate(R.layout.row_comment, parent, false)
        return  CommentViewHolder(commentView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.imageUser.setImageResource(R.drawable.ic_baseline_account_circle_24)
        holder.username.text = listComment[position].uName
        holder.comment.text = listComment[position].content
    }

    override fun getItemCount(): Int = listComment.size


    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val imageUser : ImageView
        val username : TextView
        val comment : TextView

        init {
            imageUser = view.findViewById(R.id.iv_user_comment)
            username = view.findViewById(R.id.tv_user_comment)
            comment = view.findViewById(R.id.tv_comment)
        }
    }


}