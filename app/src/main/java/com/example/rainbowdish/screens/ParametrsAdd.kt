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
import android.widget.ImageView
import android.widget.LinearLayout

class ParametrsAdd : AppCompatActivity() {
    private lateinit var heightEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var genderSpinner: Spinner
    private lateinit var goalSpinner: Spinner
    private lateinit var calculateButton: LinearLayout
    private lateinit var databaseDAO: DatabaseDAO
    private lateinit var userAdapter: UserAdapter

    private var userToEdit: User? = null // Переменная для хранения пользователя, если он был передан

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parametrs_add)

        val header: TextView = findViewById(R.id.header_title)
        header.text = "Параметры"

        // Инициализация элементов интерфейса
        heightEditText = findViewById(R.id.height)
        weightEditText = findViewById(R.id.weight)
        genderSpinner = findViewById(R.id.gender)
        goalSpinner = findViewById(R.id.goal)
        calculateButton = findViewById(R.id.calculate_button)

        val iconParametrs = findViewById<ImageView>(R.id.iconParametrs)
        iconParametrs.setImageResource(R.drawable.parametrs_active)

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

        val heightClearButton: ImageView = findViewById(R.id.height_clear)
        heightClearButton.setOnClickListener {
            val quantityTextView: TextView = findViewById(R.id.height)
            quantityTextView.setText("")
        }

        val weightClearButton: ImageView = findViewById(R.id.weight_clear)
        weightClearButton.setOnClickListener {
            val quantityTextView: TextView = findViewById(R.id.weight)
            quantityTextView.setText("")
        }

        // Инициализация базы данных и адаптера
        databaseDAO = DatabaseDAO(this)
        userAdapter = UserAdapter(this)

        // Проверяем, был ли передан объект User через Intent
        userToEdit = intent.getParcelableExtra("user")
        if (userToEdit != null) {
            // Если пользователь передан, заполняем поля его данными
            heightEditText.setText(userToEdit?.height.toString())
            weightEditText.setText(userToEdit?.weight.toString())

            // Подставляем значение для gender
            val genderPosition = genderAdapter.getPosition(userToEdit?.gender)
            genderSpinner.setSelection(genderPosition)

            // Подставляем значение для goal
            val goalPosition = goalAdapter.getPosition(userToEdit?.goal)
            goalSpinner.setSelection(goalPosition)
        }

        // Слушатель на кнопку "Рассчитать норму"
        calculateButton.setOnClickListener {
            val height = heightEditText.text.toString().toIntOrNull()
            val weight = weightEditText.text.toString().toIntOrNull()
            val gender = genderSpinner.selectedItem.toString()
            val goal = goalSpinner.selectedItem.toString()

            if (height != null && weight != null) {
                if (userToEdit != null) {
                    // Если пользователь передан, обновляем его данные
                    userToEdit?.height = height
                    userToEdit?.weight = weight
                    userToEdit?.gender = gender
                    userToEdit?.goal = goal

                    // Обновление данных в базе данных
                    databaseDAO.updateUser(userToEdit!!)
                } else {
                    // Если пользователя нет, создаем нового
                    val newUser = User(
                        id = 0, // ID может быть автогенерируемым
                        gender = gender,
                        weight = weight,
                        height = height,
                        goal = goal
                    )

                    // Добавление нового пользователя в базу данных
                    databaseDAO.addUser(newUser)
                }

                // Переход на экран отображения параметров
                val intent = Intent(this, ParametrsShow::class.java)
                startActivity(intent)
                finish() // Завершаем текущую активность
            } else {
                Toast.makeText(this, "Заполните все поля корректно", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
