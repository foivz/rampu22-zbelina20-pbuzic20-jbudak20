package com.example.lostfound.entities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.lostfound.R
import com.example.lostfound.helpers.MockLoginData

class Register : AppCompatActivity(){

    lateinit var username : String
    lateinit var password : String
    lateinit var name : String
    lateinit var phoneNumber : Number

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_registration)
        val registerButton: Button = findViewById(R.id.btn_Register)

        registerButton.setOnClickListener() {
            username = findViewById<EditText>(R.id.registration_username).toString()
            password = findViewById<EditText>(R.id.registration_password).toString()
            name = findViewById<EditText>(R.id.registration_name).toString()
            phoneNumber = findViewById<EditText>(R.id.registration_phonenumber).toString().toInt()
        }

        fun checkIfUserExists(username : String) : Boolean {
            val allUsers = MockLoginData.getLoginData()
            for(user in allUsers) {
                if(user.username == username) {
                    return true
                }
            }
            return false
        }

        fun registerUser(username : String, password : String, name : String, phoneNum : Number) {
            val newUser : User = User(username,password,name,phoneNum)
        }
    }
}