package com.example.mydatabaseapp.models

data class Product(
    var idProduct: Int = 0,
    var productName: String,
    var kcal: Double,
    var protein: Double,
    var fat: Double,
    var carbohydrates: Double
)
