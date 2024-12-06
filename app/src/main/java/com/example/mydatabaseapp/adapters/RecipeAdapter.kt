package com.example.rainbowdish.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mydatabaseapp.models.Recipe
import com.example.rainbowdish.R
import kotlin.math.roundToInt

class RecipeAdapter(
    private val recipes: List<Recipe>,
    private val onItemClickListener: (Recipe) -> Unit // Лямбда-функция для обработки кликов
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_card, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]

        // Устанавливаем данные из модели в элементы UI
        holder.nameTextView.text = recipe.nameR
        holder.timeTextView.text = recipe.timeR ?: "Не указано"
        holder.kcalTextView.text = "${recipe.kcal?.roundToInt()} кк"
        holder.proteinTextView.text = "${recipe.protein} г"
        holder.fatTextView.text = "${recipe.fats} г"
        holder.carbsTextView.text = "${recipe.carbohydrates} г"

        // Если нужно, загрузите изображение, например, через Glide или Picasso
        // Glide.with(holder.imageView.context).load(recipe.imageUrl).into(holder.imageView)

        // Устанавливаем обработчик клика на элемент карточки
        holder.itemView.setOnClickListener {
            onItemClickListener(recipe)
        }
    }

    override fun getItemCount(): Int = recipes.size

    // ViewHolder для элементов списка
    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Названия и данные рецепта
        val nameTextView: TextView = view.findViewById(R.id.recipe_name) // Название рецепта
        val timeTextView: TextView = view.findViewById(R.id.recipe_time) // Время приготовления
        val kcalTextView: TextView = view.findViewById(R.id.recipe_kcal) // Калории
        val proteinTextView: TextView = view.findViewById(R.id.recipe_protein) // Белки
        val fatTextView: TextView = view.findViewById(R.id.recipe_fat) // Жиры
        val carbsTextView: TextView = view.findViewById(R.id.recipe_carbohydrates) // Углеводы
        val imageView: ImageView = view.findViewById(R.id.product_image) // Изображение рецепта

        // Иконки для макроэлементов (если есть)
        val kcalIcon: ImageView = view.findViewById(R.id.rcx3jfp277dj23) // Иконка калорий
        val proteinIcon: ImageView = view.findViewById(R.id.rh2e1sxe3ko23) // Иконка белков
        val fatIcon: ImageView = view.findViewById(R.id.rrlrnedhzvz23) // Иконка жиров
        val carbsIcon: ImageView = view.findViewById(R.id.r8cwfoifqmow23) // Иконка углеводов
    }
}
