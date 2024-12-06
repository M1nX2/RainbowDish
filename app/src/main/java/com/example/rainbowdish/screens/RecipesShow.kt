package com.example.rainbowdish.screens

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mydatabaseapp.dao.DatabaseDAO
import com.example.mydatabaseapp.models.Recipe
import com.example.rainbowdish.R
import com.example.rainbowdish.adapters.RecipeAdapter

class RecipesShow : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipes_show) // Ваш layout с RecyclerView

        val header: TextView = findViewById(R.id.header_title)
        header.text = "Рецепты"

        val iconRecipes = findViewById<ImageView>(R.id.iconRecipes)
        iconRecipes.setImageResource(R.drawable.recipes_active)

        // Инициализируем RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Пример списка рецептов
        val databaseDAO = DatabaseDAO(this)
        val recipes = databaseDAO.getAllRecipes()

        // Создаем и устанавливаем адаптер
        val adapter = RecipeAdapter(this, recipes) { recipe ->
            // Обработка клика на рецепт
            Toast.makeText(this, "Вы выбрали рецепт: ${recipe.nameR}", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
