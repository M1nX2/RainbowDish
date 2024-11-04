package com.example.mydatabaseapp.models

data class Recipe(
    var idRecipe: Int = 0,
    var nameR: String,
    var timeR: String?,
    var descR: String,
    var weight: Double,
    var portions: Int
)
