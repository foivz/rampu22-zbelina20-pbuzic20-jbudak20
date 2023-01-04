package com.example.lostfound.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.lostfound.PostDetailActivity
import com.example.lostfound.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_edit_post.*
import kotlinx.android.synthetic.main.fragment_edit_post.view.*

class EditPostFragment : DialogFragment() {

    private val databaseRegionURL = "https://lostfound-c1e57-default-rtdb.europe-west1.firebasedatabase.app"
    private lateinit var postID : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView : View = inflater.inflate(R.layout.fragment_edit_post, container, false)

        postID = (activity as PostDetailActivity).PostKey

        rootView.btn_cancel.setOnClickListener {
            dismiss()
        }

        rootView.btn_delete.setOnClickListener {
            deletePost(postID)
        }

        rootView.btn_save.setOnClickListener {
            val title = et_title.text.toString()
            val desc = et_description.text.toString()
            val status = rootView.findViewById<RadioButton>(rg_statuses.checkedRadioButtonId).text.toString()

        }

        return rootView
    }

    private fun deletePost(postID : String) {

        val dbReference = FirebaseDatabase.getInstance(databaseRegionURL).getReference("posts").child(postID)
        val remove = dbReference.removeValue()

        remove.addOnSuccessListener {
            Toast.makeText(context, "Uspješno obrisana objava!", Toast.LENGTH_SHORT).show()
            this.requireActivity().finish()
        }
            .addOnFailureListener{
                Log.i("FAILURE", "Neuspješno brisanje!")
            }
    }

}