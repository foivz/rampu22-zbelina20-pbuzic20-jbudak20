package com.example.lostfound.helpers

import com.example.lostfound.entities.User

object MockLoginData {
    fun getLoginData() : List<User> = listOf(

        User("ivan", "lozinka123", "Ivan", 123456),
        User("marin", "pw123", "Marin", 12345678)

    )


}