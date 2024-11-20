package com.example.rainbowdish

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.mydatabaseapp.dao.DatabaseDAO
import com.example.rainbowdish.screens.ProductAdd


class MainActivity : AppCompatActivity() {
    private lateinit var databaseDAO: DatabaseDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        databaseDAO = DatabaseDAO(this)



        val buttonGoToProductAdd: Button = findViewById(R.id.button_go_to_product_add)
        buttonGoToProductAdd.setOnClickListener {
            // Создаем Intent для перехода на экран ProductAdd
            val intent = Intent(this, ProductAdd::class.java)
            startActivity(intent)
        }
    }
}
