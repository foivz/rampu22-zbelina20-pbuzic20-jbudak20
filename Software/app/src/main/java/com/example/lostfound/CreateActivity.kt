package com.example.lostfound

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lostfound.databinding.ActivityCreateBinding
import com.example.lostfound.entities.Post
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class CreateActivity : AppCompatActivity() {
    private lateinit var binding :  ActivityCreateBinding
    private var imageUri: Uri? = null
    private lateinit var storage: StorageReference
    private lateinit var username: String
    private val databaseRegionURL = "https://lostfound-c1e57-default-rtdb.europe-west1.firebasedatabase.app"
    private lateinit var databaseReference : DatabaseReference
    private lateinit var adresaSlike: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username = intent.getStringExtra("username").toString()


        initVars()
        registerClickEvents()
    }

    private fun initVars() {
        storage = FirebaseStorage.getInstance().reference
        databaseReference = FirebaseDatabase.getInstance(databaseRegionURL).getReference("posts")
    }

    private fun registerClickEvents() {
        binding.btnCancel.setOnClickListener {
            val intent = Intent(this, PostsActivity::class.java)
            startActivity(intent)
        }

        binding.btnAddPicture.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            pickImage.launch(intent)
        }

        binding.btnSubmit.setOnClickListener {
            if(provjeriUnos()) {
                val id = databaseReference.push().key!!
                val post = Post(
                    id,
                    binding.etTitle.text.toString(),
                    System.currentTimeMillis(),
                    username,
                    binding.etDescription.text.toString(),
                    adresaSlike
                )
                databaseReference.child(id).setValue(post)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Unijet je novi post", Toast.LENGTH_SHORT).show()
                        binding.etTitle.text.clear()
                        binding.etDescription.text.clear()
                        val intent = Intent(this, PostsActivity::class.java)
                        startActivity(intent)
                    }.addOnFailureListener{err ->
                        Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }


    }

    private fun provjeriUnos() : Boolean {
        if(imageUri == null){
            Toast.makeText(this, "Niste unijeli sliku izgubljene stvari", Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.etDescription.text.isBlank()){
            Toast.makeText(this, "Niste unijeli opis izgubljene stvari", Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.etTitle.text.isBlank()){
            Toast.makeText(this, "Niste unijeli naslov objave.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK){
            imageUri = result.data?.data
            val imageData = result.data?.data
            val imageName:StorageReference = storage.child("images/"+imageData!!.lastPathSegment)
            imageName.putFile(imageData).addOnSuccessListener {
                imageName.downloadUrl.addOnSuccessListener { uri ->
                    val hashMap: HashMap<String, String> = HashMap()
                    adresaSlike = uri.toString()
                    hashMap["photo"] = uri.toString()
                }.addOnFailureListener {
                    Toast.makeText(this, "Objava nije stavljena", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}