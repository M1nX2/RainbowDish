package com.example.mydatabaseapp.models

import org.threeten.bp.LocalDate


data class Record(
    var idRecord: Int = 0,
    var date: LocalDate,
    var productId: Int,
    var quantity: Double
)
