package com.example.rainbowdish.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.LinearLayout
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

                // Обработчик клика на линейный лэйаут, который отправит текущего пользователя в экран редактирования
                val editLayout = findViewById<LinearLayout>(R.id.parametrs_edit)
                editLayout.setOnClickListener {
                    // Отправка данных пользователя в ParametrsAdd
                    val editIntent = Intent(this, ParametrsAdd::class.java)
                    editIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    editIntent.putExtra("user", user) // Передача текущего пользователя
                    startActivity(editIntent)
                    overridePendingTransition(0, 0)
                }

                val productButton = findViewById<LinearLayout>(R.id.product_show)
                productButton.setOnClickListener{
                    val productIntent = Intent(this, ProductsShow::class.java)
                    productIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(productIntent)
                    overridePendingTransition(0, 0)
                }

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

//    здесь начались настины прикольные расчёты
    data class NutritionData(
        val calories: Double,
        val protein: Double,
        val fat: Double,
        val carbs: Double,
        val vitaminA: Double,  // мкг
        val vitaminB1: Double, // мг
        val vitaminB2: Double, //мг
        val vitaminB5: Double, //мг
        val vitaminB6: Double, //мг
        val vitaminB9: Double, // мкг
        val vitaminC: Double, // мг
        val vitaminE: Double, //
        val vitaminK: Double, //
        val K: Double, //
        val Ca: Double, // мг
        val Mg: Double, //
        val P: Double, //
        val Fe: Double, //
        val I: Double, // мг
        val Zn: Double, //
        val F: Double //
    )

    fun calculateNutrition(user: User): NutritionData {
        // Расчет BMR по формуле Миффлина-Сан Жеора
        val bmr = when (user.gender.lowercase()) {
            "male" -> 10 * user.weight + 6.25 * user.height - 5 * 30 + 5
            "female" -> 10 * user.weight + 6.25 * user.height - 5 * 30 - 161
            else -> throw IllegalArgumentException("Invalid sex. Use 'male' or 'female' НАХ ТРАНСОВ.")
        }

        // Коэффициенты активности
        val activityMultiplier = 1.55

        // Суточная норма калорий
        val maintenanceCalories = bmr * activityMultiplier

        // Процентное распределение БЖУ в зависимости от цели
        val (proteinPercent, fatPercent, carbPercent) = when (user.goal.lowercase()) {
            "поддержание" -> Triple(0.25, 0.25, 0.50)
            "снижение веса" -> Triple(0.30, 0.25, 0.45)
            "набор веса" -> Triple(0.25, 0.20, 0.55)
            else -> throw IllegalArgumentException("цель индивида.")
        }

        // Расчет граммов БЖУ
        val proteinCalories = maintenanceCalories * proteinPercent
        val fatCalories = maintenanceCalories * fatPercent
        val carbCalories = maintenanceCalories * carbPercent

        val proteinGrams = proteinCalories / 4
        val fatGrams = fatCalories / 9
        val carbGrams = carbCalories / 4

        // Рекомендации по витаминам и минералам
        val vitaminValues = when (user.gender.lowercase()) {
            "мужской" -> listOf(900.0, 1.2, 1.3, 5.0, 1.3, 400.0, 90.0, 15.0, 120.0, 4700.0, 1000.0, 400.0, 700.0, 8.0, 150.0, 11.0, 4.0)
            "женский" -> listOf(700.0, 1.1, 1.1, 5.0, 1.3, 400.0, 75.0, 15.0, 90.0, 4700.0, 1000.0, 310.0, 700.0, 18.0, 150.0, 8.0, 3.0)
            else -> throw IllegalArgumentException("Invalid sex ахахахахах секс.")
        }

// Извлекаем значения из списка
        val vitaminA = vitaminValues[0]
        val vitaminB1 = vitaminValues[1]
        val vitaminB2 = vitaminValues[2]
        val vitaminB5 = vitaminValues[3]
        val vitaminB6 = vitaminValues[4]
        val vitaminB9 = vitaminValues[5]
        val vitaminC = vitaminValues[6]
        val vitaminE = vitaminValues[7]
        val vitaminK = vitaminValues[8]
        val K = vitaminValues[9]
        val Ca = vitaminValues[10]
        val Mg = vitaminValues[11]
        val P = vitaminValues[12]
        val Fe = vitaminValues[13]
        val I = vitaminValues[14]
        val Zn = vitaminValues[15]
        val F = vitaminValues[16]


        return NutritionData(
            calories = maintenanceCalories,
            protein = proteinGrams,
            fat = fatGrams,
            carbs = carbGrams,
            vitaminA = vitaminA,
            vitaminB1 =vitaminB1,
            vitaminB2 = vitaminB2,
            vitaminB5 = vitaminB5,
            vitaminB6 = vitaminB6,
            vitaminB9 = vitaminB9,
            vitaminC = vitaminC,
            vitaminE = vitaminE,
            vitaminK = vitaminK,
            K = K,
            Ca = Ca,
            Mg = Mg,
            P = P,
            Fe = Fe,
            I = I,
            Zn = Zn,
            F = F
        )
    }
//    а здесь кончились

}
