package com.example.lostfound

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lostfound.databinding.ActivityCreateBinding
import com.example.lostfound.entities.Post
import com.example.lostfound.entities.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


class CreateActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            vrstaImovine = parent.getItemAtPosition(position).toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        vrstaImovine = "Razno"
    }

    private lateinit var binding :  ActivityCreateBinding
    private var imageUri: Uri? = null
    private lateinit var storage: StorageReference
    private lateinit var username: String
    private val databaseRegionURL = "https://lostfound-c1e57-default-rtdb.europe-west1.firebasedatabase.app"
    private lateinit var databaseReference : DatabaseReference
    private lateinit var adresaSlike: String
    private lateinit var vrstaImovine: String
    private lateinit var status: String
    private lateinit var foundOrLost : String

    private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        user = if(Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("user", User::class.java) as User
        }
        else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<User>("user") as User
        }
        foundOrLost = intent.getStringExtra("posts").toString()
        status = intent.getStringExtra("status").toString()
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Postavljanje username na korisnićko ime od logiranog korisnika
        username = user.username.toString()

        val spinner: Spinner = findViewById(R.id.s_VrstaImovine)
        spinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.property_type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        initVars()
        registerClickEvents()
    }

    //Postavljanje instance na Firebase storage i referencu na bazu gdje će se spremati objave
    private fun initVars() {
        storage = FirebaseStorage.getInstance().reference
        databaseReference = FirebaseDatabase.getInstance(databaseRegionURL).getReference(foundOrLost)
    }

    private fun registerClickEvents() {
        binding.btnCancel.setOnClickListener {
            finish()
        }

        //Metoda koja se aktivira kada korisnik klikne na gumb 'Add picture', definira tip 'Image' i poziva funkciju
        // launch(intent)
        binding.btnAddPicture.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            pickImage.launch(intent)
        }

        //Metoda koja je zaslužna da sprema objavu u bazu
        //Provjerava se unos da bismo vidjeli je li sve uspješno unešeno
        //Kod kreirenja objave također se stavlja ključ tako da svaka objava ima različiti ključ
        //System.currentTimeMillis predstavlja trenutno vrijeme u milisekundama, to se koristi kako bismo vidjeli
        // kada je objava kreirana
        binding.btnSubmit.setOnClickListener {
            if(provjeriUnos()) {
                dodajSliku()
            }
        }


    }
    //Metoda koja se koristi za dodavanje objave u bazu. Kod dodavanje objave svaka objava ima nasumični ID tako da ne bi moglo
    // biti isti naziv ili ID za više objava. Kod dodavanja objave još se definiraju naslov, korisničko ime, opis, adresaslike,
    // status i vrsta imoveine.
    private fun dodajObjavu() {
        val id = databaseReference.push().key!!

        val post = Post(
            id,
            binding.etTitle.text.toString(),
            System.currentTimeMillis(),
            username,
            binding.etDescription.text.toString(),
            adresaSlike,
            status,
            vrstaImovine = this.vrstaImovine
        )

        databaseReference.child(id).setValue(post)
            .addOnSuccessListener {
                val topic = binding.etTitle.text.toString()
                FirebaseMessaging.getInstance().subscribeToTopic(topic)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("FCM", "Successfully subscribed to topic: $topic")
                        }
                    }
                Toast.makeText(this, "Unijet je novi post", Toast.LENGTH_SHORT).show()
                binding.etTitle.text.clear()
                binding.etDescription.text.clear()
                finish()
            }.addOnFailureListener{err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }

    //Metoda koja služi za učitavanje slike u ImageView. Korisnik odabire sliku koju želi staviti iz galerije.
    private fun dodajSliku(){
        val imageData = imageUri
        val imageName: StorageReference = storage.child("images/" + imageData!!.lastPathSegment)
        imageName.putFile(imageData).addOnSuccessListener {
            imageName.downloadUrl.addOnSuccessListener { uri ->
                adresaSlike = uri.toString()
                dodajObjavu()
            }
        }
    }

    //Funkcija koja provjerava jesu li ispravno unesene sve vrijednosti
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

    //Funkcija koja sprema sliku u firebase storage
    //Sve slike se spremaju u folder '/images' gdje onda svaka slika ima svoj naziv
    //imageUri sadrži URI od slike
    val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK){
            imageUri = result.data?.data!!
            Picasso.get().load(imageUri).fit().into(binding.ivPicture)
            //binding.ivPicture.setImageURI(imageUri)
        }
    }
}