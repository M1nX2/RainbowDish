package com.example.rainbowdish.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rainbowdish.R
import com.example.mydatabaseapp.dao.DatabaseDAO
import com.example.mydatabaseapp.models.User
import com.example.rainbowdish.adapters.UserAdapter
import android.widget.ArrayAdapter

class ParametrsAdd : AppCompatActivity() {
    private lateinit var heightEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var genderSpinner: Spinner
    private lateinit var goalSpinner: Spinner
    private lateinit var calculateButton: Button
    private lateinit var databaseDAO: DatabaseDAO
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parametrs_add)

        // Инициализация элементов интерфейса
        heightEditText = findViewById(R.id.height)
        weightEditText = findViewById(R.id.weight)
        genderSpinner = findViewById(R.id.gender)
        goalSpinner = findViewById(R.id.goal)
        calculateButton = findViewById(R.id.calculate_button)

        // Установить адаптеры для Spinner
        val genderAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.gender_options,
            android.R.layout.simple_spinner_item
        )
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = genderAdapter

        val goalAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.goal_options,
            android.R.layout.simple_spinner_item
        )
        goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        goalSpinner.adapter = goalAdapter

        // Инициализация базы данных и адаптера
        databaseDAO = DatabaseDAO(this)
        userAdapter = UserAdapter(this)

        // Слушатель на кнопку "Рассчитать норму"
        calculateButton.setOnClickListener {
            val height = heightEditText.text.toString().toIntOrNull()
            val weight = weightEditText.text.toString().toIntOrNull()
            val gender = genderSpinner.selectedItem.toString()
            val goal = goalSpinner.selectedItem.toString()

            if (height != null && weight != null) {
                // Создание нового пользователя
                val user = User(
                    id = 0, // ID может быть автогенерируемым
                    gender = gender,
                    weight = weight,
                    height = height,
                    goal = goal
                )

                // Добавление пользователя в базу данных
                val userId = databaseDAO.addUser(user)

                val intent = Intent(this, ParametrsShow::class.java)
                startActivity(intent)
                finish() // Завершаем текущую активность
        }
    }
}
}
