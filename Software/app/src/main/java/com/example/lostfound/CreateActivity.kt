package com.example.lostfound

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

private const val TAG = "CreateActivity"
private const val PICK_PHOTO_CODE = 1234
@Suppress("DEPRECATION")
class CreateActivity : AppCompatActivity() {
    private  var photoUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        val btnaddPicture = findViewById<Button>(R.id.btn_addPicture)
        btnaddPicture.setOnClickListener{
            Log.i(TAG, "Open up image picker from device")
            val imagePickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            imagePickerIntent.type = "image/*"
            if(imagePickerIntent.resolveActivity(packageManager) != null){
                startActivityForResult(imagePickerIntent, PICK_PHOTO_CODE)
            }
        }

        val btnSubmit = findViewById<Button>(R.id.btn_Submit)
        btnSubmit.setOnClickListener{
            handleSubmitButtonClick()
        }
    }

    private fun handleSubmitButtonClick() {
        if(photoUri == null){
            Toast.makeText(this, "Nije izabrana nikakva slika", Toast.LENGTH_SHORT).show()
            return
        }
        val description = findViewById<EditText>(R.id.et_Description)
        if(description.text.isBlank()){
            Toast.makeText(this, "Niste unijeli opis izgubljene stvari", Toast.LENGTH_SHORT).show()
            return
        }
        //DODATI JOŠ AKO KORISNIK NIJE LOGIRAN DA NE MOŽE STAVLJATI OBJAVU
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_PHOTO_CODE){
            if(resultCode == Activity.RESULT_OK){
                photoUri = data?.data
                val imageView = findViewById<ImageView>(R.id.iv_picture)
                Log.i(TAG, "photoUri $photoUri")
                imageView.setImageURI(photoUri)
            }
        }
    }
}