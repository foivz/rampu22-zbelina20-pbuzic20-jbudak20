package com.example.lostfound.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.lostfound.PostDetailActivity
import com.example.lostfound.R
import com.example.lostfound.entities.Post
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_edit_post.*
import kotlinx.android.synthetic.main.fragment_edit_post.view.*

class EditPostFragment : DialogFragment() {

    private val databaseRegionURL = "https://lostfound-c1e57-default-rtdb.europe-west1.firebasedatabase.app"
    private lateinit var postID : String
    private lateinit var username : String
    private lateinit var imagePath : String
    private lateinit var status: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView : View = inflater.inflate(R.layout.fragment_edit_post, container, false)

        //Dohvaćanje potrebnih podataka iz prethodne aktivnosti
        postID = (activity as PostDetailActivity).PostKey
        username = (activity as PostDetailActivity).postUsername.text.toString()
        imagePath = (activity as PostDetailActivity).imagePath
        status = (activity as PostDetailActivity).status.toString()



        //Ako korisnik odustane, pokreće se dismiss()
        rootView.btn_cancel.setOnClickListener {
            dismiss()
        }

        //Pozivanje funckije za brisanje
        rootView.btn_delete.setOnClickListener {
            deletePost(postID)
        }

        rootView.btn_save.setOnClickListener {

            //Dohvaćanje unesenih podataka
            val title = et_title.text.toString()
            val desc = et_description.text.toString()
            val radioGroup : RadioGroup = rootView.findViewById(R.id.rg_statuses)
            val selectedRadioButtonId : Int = radioGroup.checkedRadioButtonId

            //Ako su uneseni svi podaci, tada se objava izmjenjuje
            if(selectedRadioButtonId != -1 && !title.isBlank() && !desc.isBlank()) {
                val selectedRadioButton : RadioButton = rootView.findViewById(selectedRadioButtonId)
                val status = selectedRadioButton.text.toString()
                editPost(title,desc,status)
                this.requireActivity().finish()
            }
            else {
                Toast.makeText(context, "Niste unijeli podatke!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }
        return rootView
    }

    private fun provjeriStatus(status: String) : String {
        if(status == "izgubljeno"){
            return "posts"
        }else if(status == "pronadeno"){
            return "postsFound"
        }
        else
            return "posts"
    }

    private fun deletePost(postID : String) {
        val provjera = provjeriStatus(status)
        //Dohvaća se referenca na bazu podataka, na tablicu "posts" unutar baze, na element sa ID-om "postID" unutar te tablice
        val dbReference = FirebaseDatabase.getInstance(databaseRegionURL).getReference(provjera).child(postID)
        //Brisanje elementa
        val remove = dbReference.removeValue()

        remove.addOnSuccessListener {
            Toast.makeText(context, "Uspješno obrisana objava!", Toast.LENGTH_SHORT).show()
            this.requireActivity().finish()
        }
            .addOnFailureListener{
                Log.i("FAILURE", "Neuspješno brisanje!")
            }
    }

    private fun editPost(title : String, desc : String, status : String) {
        val provjera = provjeriStatus(status)
        //Dohvaća se referenca na bazu podataka, na tablicu "posts" unutar baze, na element sa ID-om "postID" unutar te tablice
         val dbReference = FirebaseDatabase.getInstance(databaseRegionURL).getReference(provjera).child(postID)
        //Kreira se novi objekt tipa "Post" sa unesenim podaicma
         val editedPost = Post(postID, title, System.currentTimeMillis(), username, desc, imagePath, status)
        //Kreirani objekt se zapisuje u bazu točnije. overwrite-a objavu sa postojećim ID-om
         dbReference.setValue(editedPost)
    }
}