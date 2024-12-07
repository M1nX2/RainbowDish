package com.example.rainbowdish.screens

import android.os.Build
import android.os.Bundle
import org.threeten.bp.LocalDate
import org.threeten.bp.DayOfWeek
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.rainbowdish.adapters.WeeklyNutritionAdapter// чето я не поняла че к чему че не так
import com.example.mydatabaseapp.adapters.NutritionData
import com.example.mydatabaseapp.models.User
import com.example.rainbowdish.R
import com.example.rainbowdish.adapters.UserAdapter
import androidx.core.content.ContextCompat
import org.threeten.bp.format.DateTimeFormatter


class StatsShow : AppCompatActivity() {

    private lateinit var weeklyNutritionAdapter: WeeklyNutritionAdapter

    private lateinit var currentNutritionData: NutritionData

    private lateinit var userAdapter: UserAdapter
    private lateinit var  user: User
    private lateinit var dailyNutritionData: NutritionData
    private lateinit var requiredNutritionData: NutritionData

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statistics)

        val header: TextView = findViewById(R.id.header_title)
        header.text = "Статистика"

        //устанавливаю дату
        val today = LocalDate.now()
        val date_text: String = getCurrentWeekRange()
        findViewById<TextView>(R.id.date_textView).text = date_text

        weeklyNutritionAdapter = WeeklyNutritionAdapter(this)
        currentNutritionData =  weeklyNutritionAdapter.calculateWeeklyNutrition()
        userAdapter = UserAdapter(this)
        user = userAdapter.getItem(0)
        dailyNutritionData = NutritionData.calculateNutrition(user)
        requiredNutritionData = NutritionData.calculateWeeklyNutrition(dailyNutritionData)


        val imageViewVitA: ImageView = findViewById(R.id.imageViewOverlay_VitA)
        val imageViewVitB: ImageView = findViewById(R.id.imageViewOverlay_VitB)
        val imageViewVitC: ImageView = findViewById(R.id.imageViewOverlay_VitC)
        val imageViewVitE: ImageView = findViewById(R.id.imageViewOverlay_VitE)
        val imageViewVitK: ImageView = findViewById(R.id.imageViewOverlay_VitK)
        val imageViewCa: ImageView = findViewById(R.id.imageViewOverlay_Ca)
        val imageViewF: ImageView = findViewById(R.id.imageViewOverlay_F)
        val imageViewFe: ImageView = findViewById(R.id.imageViewOverlay_Fe)
        val imageViewI: ImageView = findViewById(R.id.imageViewOverlay_I)
        val imageViewK: ImageView = findViewById(R.id.imageViewOverlay_K)
        val imageViewMg: ImageView = findViewById(R.id.imageViewOverlay_Mg)
        val imageViewP: ImageView = findViewById(R.id.imageViewOverlay_P)
        val imageViewZn: ImageView = findViewById(R.id.imageViewOverlay_Zn)

        val imageViewMap = mapOf(
            "VitA" to imageViewVitA,
            "VitB" to imageViewVitB,
            "VitC" to imageViewVitC,
            "VitE" to imageViewVitE,
            "VitK" to imageViewVitK,
            "Ca" to imageViewCa,
            "F" to imageViewF,
            "Fe" to imageViewFe,
            "I" to imageViewI,
            "K" to imageViewK,
            "Mg" to imageViewMg,
            "P" to imageViewP,
            "Zn" to imageViewZn
        )


        //женя прости что функция внутри
        fun updateProgressBars(
            currentNutritionData: NutritionData,
            requiredNutritionData: NutritionData
        ) {
            // Привязка ProgressBar и TextView
            val kcalProgressBar: ProgressBar = findViewById(R.id.stat_kcal_progressBar)
            val proteinProgressBar: ProgressBar = findViewById(R.id.stat_protein_progressBar)
            val fatsProgressBar: ProgressBar = findViewById(R.id.stat_fats_progressBar)
            val carbsProgressBar: ProgressBar = findViewById(R.id.stat_carbs_progressBar)

            findViewById<TextView>(R.id.stats_kcal_text).text = "${currentNutritionData.calories}/${requiredNutritionData.calories}"
            findViewById<TextView>(R.id.stats_protein_text).text = "${currentNutritionData.protein}/${requiredNutritionData.protein}"
            findViewById<TextView>(R.id.stats_fats_text).text = "${currentNutritionData.fat}/${requiredNutritionData.fat}"
            findViewById<TextView>(R.id.stats_carbs_text).text = "${currentNutritionData.carbs}/${requiredNutritionData.carbs}"

            // Установка максимальных значений
            kcalProgressBar.max = requiredNutritionData.calories
            proteinProgressBar.max = requiredNutritionData.protein
            fatsProgressBar.max = requiredNutritionData.fat
            carbsProgressBar.max = requiredNutritionData.carbs

            // Установка прогресса
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

        val iconStats = findViewById<ImageView>(R.id.iconStats)
        iconStats.setImageResource(R.drawable.statistics_active)

        updateAllImageViewsVisibility(currentNutritionData, requiredNutritionData, imageViewMap)
        updateProgressBars(currentNutritionData, requiredNutritionData)

        findViewById<ImageView>(R.id.strelka_nazad_kalendar).setOnClickListener {

            // Вычисляем дату неделю назад
            val weekAgo = today.minusDays(7)
            // Вызываем вашу функцию с датой неделю назад
            currentNutritionData =  weeklyNutritionAdapter.calculateWeeklyNutrition(weekAgo)
            val date_text: String = getCurrentWeekRange(weekAgo)
            findViewById<TextView>(R.id.date_textView).text = date_text
            updateAllImageViewsVisibility(currentNutritionData, requiredNutritionData, imageViewMap)
            updateProgressBars(currentNutritionData, requiredNutritionData)
        }

        findViewById<ImageView>(R.id.strelka_vpered_kalendar).setOnClickListener {

            // Вычисляем дату неделю назад
            val weekAdvance = today.plusDays(7)
            // Вызываем вашу функцию с датой неделю назад
            currentNutritionData =  weeklyNutritionAdapter.calculateWeeklyNutrition(weekAdvance)
            val date_text: String = getCurrentWeekRange(weekAdvance)
            findViewById<TextView>(R.id.date_textView).text = date_text
            updateAllImageViewsVisibility(currentNutritionData, requiredNutritionData, imageViewMap)
            updateProgressBars(currentNutritionData, requiredNutritionData)
        }
    }

    // Витамины B
    val bVitamins = listOf(
        "VitA", "VitB1", "VitB2",
        "VitB5", "VitB6", "VitB9"
    )

    // Все остальные элементы
    val elements = listOf(
        "VitC", "VitE", "VitK",
        "K", "Ca", "Mg", "P", "Fe", "I", "Zn", "F"
    )

    fun updateAllImageViewsVisibility(
        currentNutritionData: NutritionData,
        requiredNutritionData: NutritionData,
        imageViewMap: Map<String, ImageView>
    ) {
        // Перебираем все элементы
        for (element in elements) {
            // Получаем текущее значение и требуемое значение для этого элемента
            val currentValue = getElementValue(currentNutritionData, element)
            val requiredValue = getElementValue(requiredNutritionData, element)

            // Получаем ImageView для этого элемента из карты
            val imageView = imageViewMap[element]

            // Если ImageView существует, обновляем его видимость
            imageView?.alpha = if (currentValue >= requiredValue) 1f else 0f
        }

        // Проверка для витаминов группы B
        val allBGroupVitaminsMet = bVitamins.all { vitamin ->
            getElementValue(currentNutritionData, vitamin) >= getElementValue(requiredNutritionData, vitamin)
        }
        val bGroupVisibility = if (allBGroupVitaminsMet) 1f else 0f
        imageViewMap["VitB"]?.alpha = bGroupVisibility
    }

    // Функция для получения значения для конкретного элемента
    fun getElementValue(nutritionData: NutritionData, element: String): Double {
        return when (element) {
            "VitA" -> nutritionData.vitaminA.toDouble()
            "VitB1" -> nutritionData.vitaminB1
            "VitB2" -> nutritionData.vitaminB2
            "VitB5" -> nutritionData.vitaminB5.toDouble()
            "VitB6" -> nutritionData.vitaminB6
            "VitB9" -> nutritionData.vitaminB9.toDouble()
            "VitC" -> nutritionData.vitaminC.toDouble()
            "VitE" -> nutritionData.vitaminE.toDouble()
            "VitK" -> nutritionData.vitaminK.toDouble()
            "K" -> nutritionData.K.toDouble()
            "Ca" -> nutritionData.Ca.toDouble()
            "Mg" -> nutritionData.Mg.toDouble()
            "P" -> nutritionData.P.toDouble()
            "Fe" -> nutritionData.Fe.toDouble()
            "I" -> nutritionData.I.toDouble()
            "Zn" -> nutritionData.Zn.toDouble()
            "F" -> nutritionData.F.toDouble()
            else -> 0.0
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentWeekRange(today: LocalDate = LocalDate.now()): String {

        // Вычисляем дату понедельника текущей недели
        val startOfWeek = today.with(DayOfWeek.MONDAY)

        // Вычисляем дату воскресенья текущей недели
        val endOfWeek = today.with(DayOfWeek.SUNDAY)

        // Формат для вывода дат
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        // Возвращаем диапазон в виде строки
        return "${startOfWeek.format(formatter)} - ${endOfWeek.format(formatter)}"
    }
}