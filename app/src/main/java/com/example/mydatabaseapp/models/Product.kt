package com.example.mydatabaseapp.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    var idProduct: Int = 0,
    var productName: String,
    var kcal: Double = 0.0,
    var protein: Double = 0.0,
    var fat: Double = 0.0,
    var carbohydrates: Double = 0.0,
    var vitA: Double = 0.0,
    var vitB1: Double = 0.0,
    var vitB2: Double = 0.0,
    var vitB5: Double = 0.0,
    var vitB6: Double = 0.0,
    var vitB9: Double = 0.0,
    var vitC: Double = 0.0,
    var vitE: Double = 0.0,
    var vitK: Double = 0.0,
    var k: Double = 0.0,
    var ca: Double = 0.0,
    var mg: Double = 0.0,
    var p: Double = 0.0,
    var fe: Double = 0.0,
    var i: Double = 0.0,
    var zn: Double = 0.0,
    var f: Double = 0.0,
    var avgWeight: Double? = null,
    var productImg: String? = null
) : Parcelable
