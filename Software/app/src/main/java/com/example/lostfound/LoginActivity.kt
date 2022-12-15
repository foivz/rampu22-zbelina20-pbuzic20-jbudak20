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
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername : EditText
    private lateinit var etPassword : EditText
    private lateinit var btnLogin : Button

    private lateinit var dbRef : DatabaseReference
    private val databaseRegionURL = "https://lostfound-c1e57-default-rtdb.europe-west1.firebasedatabase.app"
    lateinit var usersList: MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_login)

        dbRef = FirebaseDatabase.getInstance(databaseRegionURL).getReference("users")
        usersList = mutableListOf()

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btn_Login)

        btnLogin.setOnClickListener {
            val isLoginSuccessful = loginUser()
            if(isLoginSuccessful) {
                //tu redirectati na sljedecu aktivnost
                Toast.makeText(this, "Uspješna prijava!", Toast.LENGTH_SHORT).show()
            }
            else {
                return@setOnClickListener
            }
        }

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("DBError", "Neuspješno čitanje podataka: ", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for(u in snapshot.children) {
                        val user = u.getValue(User::class.java)
                        usersList.add(user!!)
                    }
                }
            }
        })


        val btnRegister = findViewById<Button>(R.id.btn_Register)
        btnRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

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

    private fun validateData(username : String, password : String) : Boolean {
        for(user in usersList) {
            if(user.username == username && user.password == password) {
                return true
            }
        }
        Toast.makeText(this, "Neispravni podaci", Toast.LENGTH_SHORT).show()
        return false
    }
}