package com.example.rainbowdish.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rainbowdish.R
import com.example.rainbowdish.adapters.UserAdapter
import com.example.mydatabaseapp.models.User
import com.example.mydatabaseapp.dao.DatabaseDAO

class ParametrsShow : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Создаем экземпляр адаптера для пользователей
        userAdapter = UserAdapter(this)

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

        val header: TextView = findViewById(R.id.header_title)
        header.text = "Параметры"

        // Настроим значения для отображения на лейауте
        val heightTextView = findViewById<TextView>(R.id.user_height)
        val weightTextView = findViewById<TextView>(R.id.user_weight)
        val genderTextView = findViewById<TextView>(R.id.user_gender)
        val goalTextView = findViewById<TextView>(R.id.user_goal)
        val iconParametrs = findViewById<ImageView>(R.id.iconParametrs)

        try {
            // Получаем первого пользователя
            val user = userAdapter.getItem(0)

            // Проверка на случай, если пользователь не существует
            if (user != null) {
                // Заполняем данные пользователя
                heightTextView.text = "${user.height} см"
                weightTextView.text = "${user.weight} кг"
                genderTextView.text = user.gender
                goalTextView.text = user.goal
                iconParametrs.setImageResource(R.drawable.parametrs_active)
            } else {
                // Если пользователь равен null, показываем сообщение
                Toast.makeText(this, "Ошибка: пользователь не найден.", Toast.LENGTH_LONG).show()
                finish() // Завершаем текущую активность
            }
        } catch (e: Exception) {
            // Обработка исключений, если что-то пошло не так
            Toast.makeText(this, "Ошибка загрузки данных: ${e.message}", Toast.LENGTH_LONG).show()
            finish() // Завершаем текущую активность
        }
    }
}
