package com.example.rainbowdish

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.mydatabaseapp.adapters.NutritionData
import com.example.mydatabaseapp.dao.DatabaseDAO
import com.example.rainbowdish.screens.ParametrsShow
import com.example.mydatabaseapp.adapters.calculateNutrition
import com.example.rainbowdish.adapters.UserAdapter
import com.example.rainbowdish.screens.ProductAdd
import com.example.rainbowdish.screens.ProductsShow


class MainActivity : AppCompatActivity() {
    private lateinit var databaseDAO: DatabaseDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        databaseDAO = DatabaseDAO(this)
        val userAdapter = UserAdapter(this)
        val user = userAdapter.getItem(0)
        Log.d("DEBUG", "Gender value: ${user.gender}")
        val nutritionData = calculateNutrition(user)

        // Выводим результат в лог
        Log.d("NutritionData", "Calculated: $nutritionData")

        // Выводим результат на экран
        Toast.makeText(this, "Результат: $nutritionData", Toast.LENGTH_LONG).show()
        databaseDAO.printDatabaseTables()
        val buttonGoToProductAdd: Button = findViewById(R.id.button_go_to_product_add)
        buttonGoToProductAdd.setOnClickListener {
            // Создаем Intent для перехода на экран ProductAdd
            val intent = Intent(this, ProductsShow::class.java)
            startActivity(intent)
        }
    }


}
