package com.example.lostfound

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.lostfound.entities.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var etEmail : EditText
    private lateinit var etName : EditText
    private lateinit var etPassword : EditText
    private lateinit var etPhoneNumber : EditText
    private lateinit var etUsername : EditText
    private lateinit var btnRegister : Button
    private val databaseURL = "https://lostfound-c1e57-default-rtdb.europe-west1.firebasedatabase.app"

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbRef = FirebaseDatabase.getInstance(databaseURL).getReference("users")

        etEmail = findViewById(R.id.etEmail)
        etName = findViewById(R.id.etName)
        etPassword = findViewById(R.id.etPassword)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etUsername = findViewById(R.id.etUsername)
        btnRegister = findViewById(R.id.btn_Register)

        btnRegister.setOnClickListener {
            registerUser()
        }
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
        else if(!email.contains("@") || email.count{ it == '@' } > 1 || !email.contains(".")) {
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
         TODO("Spojiti se na bazu, dohvatiti korisnika i usporediti")
    }
}