package com.example.lostfound.entities

import android.os.Parcel
import android.os.Parcelable

data class User(
    val email : String? = null,
    val name : String? = null,
    val password : String? = null,
    val phoneNumber : String? = null,
    val username : String? = null
) : Parcelable
{
    constructor(parcel : Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(email)
        dest.writeString(name)
        dest.writeString(password)
        dest.writeString(phoneNumber)
        dest.writeString(username)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(source: Parcel): User {
            return User(source)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}