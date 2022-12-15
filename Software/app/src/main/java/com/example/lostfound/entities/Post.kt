package com.example.lostfound.entities

import com.google.firebase.database.PropertyName

data class Post(
    var title: String? = null,
    @get:PropertyName("creationTimeMs") @set:PropertyName("creationTimeMs") var creationTimeMs: Long = 0,
    var username: String? = null,
    var text: String? = null,
    @get:PropertyName("photo") @set:PropertyName("photo")var photo: String? = null)