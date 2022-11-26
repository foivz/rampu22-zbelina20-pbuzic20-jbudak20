package com.example.lostfound

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lostfound.databinding.ActivityCreateBinding


class CreateActivity : AppCompatActivity() {
    private lateinit var binding :  ActivityCreateBinding
    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val btnaddPicture = findViewById<Button>(R.id.btn_addPicture)
        binding.btnAddPicture.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            pickImage.launch(intent)
        }

        binding.btnSubmit.setOnClickListener {
            provjeriUnos()
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun provjeriUnos() {
        if(photoUri == null){
            Toast.makeText(this, "Niste unijeli sliku izgubljene stvari", Toast.LENGTH_SHORT).show()
            return
        }
        val description = findViewById<EditText>(R.id.et_Description)
        if(description.text.isBlank()){
            Toast.makeText(this, "Niste unijeli opis izgubljene stvari", Toast.LENGTH_SHORT).show()
            return
        }
    }

    val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK){
            photoUri = result.data?.data
            binding.ivPicture.setImageURI(photoUri)
        }
    }
}