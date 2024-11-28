package com.example.rainbowdish.screens

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import com.example.rainbowdish.R
import androidx.appcompat.app.AppCompatActivity
import com.example.rainbowdish.adapters.UserAdapter
import com.example.mydatabaseapp.dao.DatabaseDAO

class ParametrsShow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Создаем экземпляр адаптера для пользователей
        val userAdapter = UserAdapter(this)

        // Проверяем, есть ли хотя бы один пользователь
        if (!userAdapter.hasUsers()) {
            // Если пользователей нет, открываем экран ParametrsAdd
            val intent = Intent(this, ParametrsAdd::class.java)
            startActivity(intent)
            finish() // Завершаем текущую активность
            return
        }

        // Если есть пользователи, продолжаем с текущим экраном
        setContentView(R.layout.parametrs_show)

        val iconParametrs = findViewById<ImageView>(R.id.iconParametrs)
        iconParametrs.setImageResource(R.drawable.parametrs_active)
    }
}
