package com.example.lostfound.helpers

import com.example.lostfound.entities.Post

object MockDataLoader {
    fun getDemoData(): List<Post> = listOf(
        Post("Neki jako kul opis.")
    )
}