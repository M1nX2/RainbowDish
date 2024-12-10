package com.example.rainbowdish.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mydatabaseapp.adapters.NutritionData
import com.example.rainbowdish.R
import com.example.rainbowdish.adapters.UserAdapter
import com.example.mydatabaseapp.models.User
import com.example.mydatabaseapp.dao.DatabaseDAO
import com.example.rainbowdish.adapters.WeeklyNutritionAdapter

class ParametrsShow : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter
    private lateinit var weeklyNutritionAdapter: WeeklyNutritionAdapter

    private lateinit var currentNutritionData: NutritionData
    private lateinit var  user: User
    private lateinit var requiredNutritionData: NutritionData

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        // Создаем экземпляр адаптера для пользователей
        userAdapter = UserAdapter(this)
        weeklyNutritionAdapter = WeeklyNutritionAdapter(this)
        currentNutritionData =  weeklyNutritionAdapter.calculateDailyNutrition()
        if (!userAdapter.hasUsers()) {
            // Если пользователей нет, открываем экран ParametrsAdd
            val intent = Intent(this, ParametrsAdd::class.java)
            startActivity(intent)
            finish() // Завершаем текущую активность
            return
        }
        user = userAdapter.getItem(0)
        requiredNutritionData = NutritionData.calculateNutrition(user)

        // Проверяем, есть ли хотя бы один пользователь


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

        //витаминс
        findViewById<TextView>(R.id.pars_vit_a).text = "A: ${currentNutritionData.vitaminA}/${requiredNutritionData.vitaminA} мкг"
        findViewById<TextView>(R.id.pars_vit_b1).text = "B1: ${"%.1f".format(currentNutritionData.vitaminB1)}/${"%.1f".format(requiredNutritionData.vitaminB1)} мг"
        findViewById<TextView>(R.id.pars_vit_b2).text = "B2: ${"%.1f".format(currentNutritionData.vitaminB2)}/${"%.1f".format(requiredNutritionData.vitaminB2)} мг"
        findViewById<TextView>(R.id.pars_vit_b5).text = "B5: ${currentNutritionData.vitaminB5}/${requiredNutritionData.vitaminB5} мг"
        findViewById<TextView>(R.id.pars_vit_b6).text = "B6: ${"%.1f".format(currentNutritionData.vitaminB6)}/${"%.1f".format(requiredNutritionData.vitaminB6)} мг"
        findViewById<TextView>(R.id.pars_vit_b9).text = "B9: ${currentNutritionData.vitaminB9}/${requiredNutritionData.vitaminB9} мкг"
        findViewById<TextView>(R.id.pars_vit_c).text = "C: ${currentNutritionData.vitaminC}/${requiredNutritionData.vitaminC} мг"
        findViewById<TextView>(R.id.pars_vit_e).text = "E: ${currentNutritionData.vitaminE}/${requiredNutritionData.vitaminE} мг"
        findViewById<TextView>(R.id.pars_vit_k).text = "K: ${currentNutritionData.vitaminK}/${requiredNutritionData.vitaminK} мкг"

        // Минералы
        findViewById<TextView>(R.id.pars_k).text = "K: ${currentNutritionData.K}/${requiredNutritionData.K} мг"
        findViewById<TextView>(R.id.pars_ca).text = "Ca: ${currentNutritionData.Ca}/${requiredNutritionData.Ca} мг"
        findViewById<TextView>(R.id.pars_mg).text = "Mg: ${currentNutritionData.Mg}/${requiredNutritionData.Mg} мг"
        findViewById<TextView>(R.id.pars_p).text = "P: ${currentNutritionData.P}/${requiredNutritionData.P} мг"
        findViewById<TextView>(R.id.pars_fe).text = "Fe: ${currentNutritionData.Fe}/${requiredNutritionData.Fe} мг"
        findViewById<TextView>(R.id.pars_i).text = "I: ${currentNutritionData.I}/${requiredNutritionData.I} мкг"
        findViewById<TextView>(R.id.pars_zn).text = "Zn: ${currentNutritionData.Zn}/${requiredNutritionData.Zn} мг"
        findViewById<TextView>(R.id.pars_f).text = "F: ${currentNutritionData.F}/${requiredNutritionData.F} мг"

        val kcalProgressBar: ProgressBar = findViewById(R.id.pars_kcal_progressBar)
        val proteinProgressBar: ProgressBar = findViewById(R.id.pars_protein_progressBar)
        val fatsProgressBar: ProgressBar = findViewById(R.id.pars_fats_progressBar)
        val carbsProgressBar: ProgressBar = findViewById(R.id.pars_carbs_progressBar)

        findViewById<TextView>(R.id.pars_kcal_text).text = "${currentNutritionData.calories}/${requiredNutritionData.calories}"
        findViewById<TextView>(R.id.pars_protein_text).text = "${currentNutritionData.protein}/${requiredNutritionData.protein}"
        findViewById<TextView>(R.id.pars_fats_text).text = "${currentNutritionData.fat}/${requiredNutritionData.fat}"
        findViewById<TextView>(R.id.pars_carbs_text).text = "${currentNutritionData.carbs}/${requiredNutritionData.carbs}"

        kcalProgressBar.max = requiredNutritionData.calories
        proteinProgressBar.max = requiredNutritionData.protein
        fatsProgressBar.max = requiredNutritionData.fat
        carbsProgressBar.max = requiredNutritionData.carbs

        kcalProgressBar.progress = currentNutritionData.calories
        proteinProgressBar.progress = currentNutritionData.protein
        fatsProgressBar.progress = currentNutritionData.fat
        carbsProgressBar.progress = currentNutritionData.carbs

        val progressBarList: List<Pair<ProgressBar, Int>> = listOf(
            Pair(kcalProgressBar, currentNutritionData.calories),
            Pair(proteinProgressBar, currentNutritionData.protein),
            Pair(fatsProgressBar, currentNutritionData.fat),
            Pair(carbsProgressBar, currentNutritionData.carbs)
        )

        progressBarList.forEach { (progressBar, value) ->
            val percentage = value.toFloat() / progressBar.max * 100

            val drawableRes = when {
                percentage <= 30 -> R.drawable.progress_red
                percentage <= 60 -> R.drawable.progress_yellow
                else -> R.drawable.progress_green
            }
            progressBar.progress = value
            progressBar.progressDrawable = ContextCompat.getDrawable(progressBar.context, drawableRes)
        }
    }



}
