package com.example.rainbowdish

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mydatabaseapp.dao.DatabaseDAO
import com.example.mydatabaseapp.models.Product
import com.example.mydatabaseapp.models.Recipe
import com.example.mydatabaseapp.models.RecipeProduct
import com.example.mydatabaseapp.models.Record
import com.example.rainbowdish.R

class MainActivity : AppCompatActivity() {
    private lateinit var databaseDAO: DatabaseDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseDAO = DatabaseDAO(this)

        // Получение и вывод всех продуктов
        val products = databaseDAO.getAllProducts()
        for (product in products) {
            Toast.makeText(this, "Product (Product Table): ${product.productName}", Toast.LENGTH_SHORT).show()
        }

        // Получение и вывод всех рецептов
        val recipes = databaseDAO.getAllRecipes()
        for (recipe in recipes) {
            Toast.makeText(this, "Recipe (Recipe Table): ${recipe.nameR}", Toast.LENGTH_SHORT).show()
        }

        // Получение и вывод всех записей в Recipe_Product
        val recipeProducts = databaseDAO.getAllRecipeProducts()
        for (recipeProduct in recipeProducts) {
            Toast.makeText(this, "RecipeProduct (Recipe_Product Table): Recipe ID = ${recipeProduct.recipeId}, Product ID = ${recipeProduct.productId}", Toast.LENGTH_SHORT).show()
        }

        // Получение и вывод всех записей в Record
        val records = databaseDAO.getAllRecords()
        for (record in records) {
            Toast.makeText(this, "Record (Record Table): Date = ${record.date}, Product ID = ${record.productId}", Toast.LENGTH_SHORT).show()
        }
    }
}
