package com.example.mydatabaseapp.models

data class RecipeProduct(
    var idRecipeProduct: Int = 0,
    var recipeId: Int,
    var productId: Int,
    var quantity: Double
)
