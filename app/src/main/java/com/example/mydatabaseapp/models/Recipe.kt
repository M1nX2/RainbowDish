package com.example.mydatabaseapp.models

data class Recipe(
    var idRecipe: Int = 0,
    var nameR: String,
    var timeR: String?,
    var descR: String,
    var weight: Double,
    var portions: Int?,
    var recipeIMG: String?,  // Путь к изображению рецепта
    var ingredients: String?, // Ингредиенты рецепта
    var kcal: Double?, // Калории
    var protein: Double?, // Белки
    var fats: Double?, // Жиры
    var carbohydrates: Double?, // Углеводы
    var products: List<RecipeProduct> = listOf() // Список продуктов для рецепта
)
