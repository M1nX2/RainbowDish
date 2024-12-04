package com.example.mydatabaseapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int = 0,
    var height: Int,
    var weight: Int,
    var gender: String,
    var goal: String
) : Parcelable
