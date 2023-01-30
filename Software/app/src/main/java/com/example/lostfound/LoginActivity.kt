package com.example.lostfound

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.lostfound.R.layout.activity_login
import com.example.lostfound.entities.User
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername : EditText
    private lateinit var etPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var brojTelefona : String

    //Koristi se referenca na bazu podataka za pristup njezinim podacima
    private lateinit var dbRef : DatabaseReference
    private val databaseRegionURL = "https://lostfound-c1e57-default-rtdb.europe-west1.firebasedatabase.app"

    lateinit var usersList: MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_login)
        FirebaseApp.initializeApp(this)
        FirebaseAnalytics.getInstance(this).setSessionTimeoutDuration(20000)

        supportActionBar?.hide()

        dbRef = FirebaseDatabase.getInstance(databaseRegionURL).getReference("users")
        usersList = mutableListOf()

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btn_Login)

        btnLogin.setOnClickListener {
            val isLoginSuccessful = loginUser()
            if(isLoginSuccessful) {

                //Ukoliko je korisnik uspješno ulogiran, korisnika se prosljeđuje na "PostsActivity"
                //Također, prosljeđuje se i cijeli objekt tipa "User" kako bi dalje u aplikaciji znali
                //koji korisnik je ulogiran i koristi aplikaciju
                val user = getUser(etUsername.text.toString())
                val intent = Intent(this, PostsActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
            }
            else {
                return@setOnClickListener
            }
        }

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("DBError", "Neuspješno čitanje podataka: ", error.toException())
            }

            //Popunjava se lista "usersList" sa korisnicima koji su upisani u bazi podataka
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for(u in snapshot.children) {
                        val user = u.getValue(User::class.java)
                        usersList.add(user!!)
                    }
                }
            }
        })

        //Prosljeđuje se korisnika na registraciju
        val btnRegister = findViewById<Button>(R.id.btn_Register)
        btnRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    //Provjera jesu li uneseni svi potrebni podaci, i , ako jesu, validiraju se sa "validateData"
    private fun loginUser() : Boolean {
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()

        if(username.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Niste unijeli sve podatke", Toast.LENGTH_SHORT).show()
            return false
        }

        if(validateData(username, password)) {
            return true
        }

        return false
    }

    //Provjera jesu li uneseni podaci ispravni, odnosno, postoji li korisnik sa unesenim korisničkim
    //imenom i lozinkom
    private fun validateData(username : String, password : String) : Boolean {
        for(user in usersList) {
            if(user.username == username && user.password == password) {
                return true
            }
        }
        Toast.makeText(this, "Neispravni podaci", Toast.LENGTH_SHORT).show()
        return false
    }

    //Dohvaća korisnika iz liste svih korisnika prema korisničkom imenu
    private fun getUser(username : String) : User {
        for(user in usersList) {
            if(user.username == username) {
                return user
            }
        }
        return null!!
    }
}