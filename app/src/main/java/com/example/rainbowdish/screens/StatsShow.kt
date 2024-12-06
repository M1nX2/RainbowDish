package com.example.rainbowdish.screens

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.rainbowdish.adapters.WeeklyNutritionAdapter// чето я не поняла че к чему че не так
import com.example.mydatabaseapp.adapters.NutritionData
import com.example.mydatabaseapp.models.User
import com.example.rainbowdish.R
import com.example.rainbowdish.adapters.UserAdapter


class StatsShow : AppCompatActivity() {

    private lateinit var weeklyNutritionAdapter: WeeklyNutritionAdapter

    private lateinit var currentNutritionData: NutritionData

    private lateinit var userAdapter: UserAdapter
    private lateinit var  user: User
    private lateinit var requiredNutritionData: NutritionData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statistics)

        val header: TextView = findViewById(R.id.header_title)
        header.text = "Статистика"

        weeklyNutritionAdapter = WeeklyNutritionAdapter(this)
        currentNutritionData =  weeklyNutritionAdapter.calculateWeeklyNutrition()
        userAdapter = UserAdapter(this)
        user = userAdapter.getItem(0)
        requiredNutritionData = NutritionData.calculateNutrition(user)

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

        val iconStats = findViewById<ImageView>(R.id.iconStats)
        iconStats.setImageResource(R.drawable.statistics_active)

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

        updateAllImageViewsVisibility(currentNutritionData, requiredNutritionData, imageViewMap)
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
}