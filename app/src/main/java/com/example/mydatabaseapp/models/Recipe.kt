package com.example.mydatabaseapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Recipe(
    var idRecipe: Int = 0,
    var nameR: String, // Название рецепта
    var timeR: String?, // Время приготовления (может быть null)
    var descR: String, // Описание рецепта
    var weight: Double, // Вес порции
    var portions: Int?, // Количество порций (может быть null)
    var recipeIMG: String?,  // Путь к изображению рецепта
    var ingredients: String?, // Ингредиенты рецепта
    var kcal: Double?, // Калории (может быть null)
    var protein: Double?, // Белки (может быть null)
    var fats: Double?, // Жиры (может быть null)
    var carbohydrates: Double?, // Углеводы (может быть null)
) : Parcelable
