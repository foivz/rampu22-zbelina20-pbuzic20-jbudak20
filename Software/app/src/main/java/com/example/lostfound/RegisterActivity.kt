package com.example.lostfound

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.lostfound.entities.User
import com.google.firebase.database.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var etEmail : EditText
    private lateinit var etName : EditText
    private lateinit var etPassword : EditText
    private lateinit var etPhoneNumber : EditText
    private lateinit var etUsername : EditText
    private lateinit var btnRegister : Button
    private val databaseRegionURL = "https://lostfound-c1e57-default-rtdb.europe-west1.firebasedatabase.app"

    private lateinit var dbRef : DatabaseReference
    lateinit var usersList : MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()

        usersList = mutableListOf()
        dbRef = FirebaseDatabase.getInstance(databaseRegionURL).getReference("users")

        etEmail = findViewById(R.id.etEmail)
        etName = findViewById(R.id.etName)
        etPassword = findViewById(R.id.etPassword)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etUsername = findViewById(R.id.etUsername)
        btnRegister = findViewById(R.id.btn_Register)

        btnRegister.setOnClickListener {
            registerUser()
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
    }

    private fun registerUser() {
        val email = etEmail.text.toString()
        val name = etName.text.toString()
        val password = etPassword.text.toString()
        val phoneNumber = etPhoneNumber.text.toString()
        val username = etUsername.text.toString()

        val isDataValid = validateEnteredData(email, name, password, phoneNumber, username)

        if(isDataValid) {
            val newUser = User(email, name, password, phoneNumber, username)

            dbRef.child(newUser.username!!).setValue(newUser)
                .addOnSuccessListener {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener{
                    Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show()
                }
        }else {
            return
        }
    }

    private fun validateEnteredData(email : String, name : String, password : String, phone : String, username : String) : Boolean{
        if(email.isBlank() || name.isBlank() || password.isBlank() || phone.isBlank() || username.isBlank()) {
            Toast.makeText(this, "Niste unijeli sve podatke!", Toast.LENGTH_SHORT).show()
            return false
        }
        else if(!email.contains("@") || email.count{ it == '@' } > 1 || !email.contains(".") || email.count{it == '.'} > 1)  {
            Toast.makeText(this, "Email adresa neispravna!", Toast.LENGTH_SHORT).show()
            return false
        }
        else {
            val usernameExists = checkIfUsernameExists(username)
            if(usernameExists) {
                Toast.makeText(this, "Korisničko ime zauzeto! Odaberite novo.", Toast.LENGTH_SHORT).show()
                return false
            }
            return true
        }
    }

    private fun checkIfUsernameExists(username : String) : Boolean {
        for(user in usersList) {
            if(user.username == username) {
                return true
            }
        }
        return false
    }
}