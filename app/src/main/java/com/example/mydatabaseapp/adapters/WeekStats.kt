package com.example.rainbowdish.adapters

import org.threeten.bp.LocalDate // ВОТ ЭТО МНЕ НАДО
import org.threeten.bp.DayOfWeek // ВОТ ЭТО МНЕ НАДО
import kotlin.math.roundToInt
import com.example.mydatabaseapp.adapters.NutritionData
import com.example.mydatabaseapp.dao.DatabaseDAO
import com.example.mydatabaseapp.models.Record
import com.example.mydatabaseapp.models.Product
import java.time.LocalDate


val foodRecords = DatabaseDAO.getAllRecords()
val products = DatabaseDAO.getAllProducts()

// я не знаю где вызвать функцию
val nutritionData = calculateWeeklyNutrition(foodRecords, products)

fun calculateWeeklyNutrition(
    foodRecords: List<Record>,
    products: List<Product>,
    today: LocalDate = LocalDate.now() // а типа на старую неделю мы там будем передавать воскресенье как тебе идея илон маск
): NutritionData {

    val startOfWeek = today.with(DayOfWeek.MONDAY)
    val endOfWeek = today.with(DayOfWeek.SUNDAY)

    // Фильтруем записи за текущую неделю
    val weeklyRecords = foodRecords.filter { it.date in startOfWeek..endOfWeek }

    // Инициализация суммировочных переменных
    var totalCalories = 0.0
    var totalProtein = 0.0
    var totalFat = 0.0
    var totalCarbs = 0.0
    var totalVitaminA = 0.0
    var totalVitaminB1 = 0.0
    var totalVitaminB2 = 0.0
    var totalVitaminB5 = 0.0
    var totalVitaminB6 = 0.0
    var totalVitaminB9 = 0.0
    var totalVitaminC = 0.0
    var totalVitaminE = 0.0
    var totalVitaminK = 0.0
    var totalK = 0.0
    var totalCa = 0.0
    var totalMg = 0.0
    var totalP = 0.0
    var totalFe = 0.0
    var totalI = 0.0
    var totalZn = 0.0
    var totalF = 0.0

    // Суммирование КБЖУ и витаминов
    weeklyRecords.forEach { record ->
        val product = products.find { it.idProduct == record.productId }
        product?.let {
            val ratio = record.quantity / 100.0
            totalCalories += it.kcal * ratio
            totalProtein += it.protein * ratio
            totalFat += it.fat * ratio
            totalCarbs += it.carbohydrates * ratio
            totalVitaminA += it.vitA * ratio
            totalVitaminB1 += it.vitB1 * ratio
            totalVitaminB2 += it.vitB2 * ratio
            totalVitaminB5 += it.vitB5 * ratio
            totalVitaminB6 += it.vitB6 * ratio
            totalVitaminB9 += it.vitB9 * ratio
            totalVitaminC += it.vitC * ratio
            totalVitaminE += it.vitE * ratio
            totalVitaminK += it.vitK * ratio
            totalK += it.k * ratio
            totalCa += it.ca * ratio
            totalMg += it.mg * ratio
            totalP += it.p * ratio
            totalFe += it.fe * ratio
            totalI += it.i * ratio
            totalZn += it.zn * ratio
            totalF += it.f * ratio
        }
    }

    // Округляем значения
    return NutritionData(
        calories = totalCalories.roundToInt(),
        protein = totalProtein.roundToInt(),
        fat = totalFat.roundToInt(),
        carbs = totalCarbs.roundToInt(),
        vitaminA = totalVitaminA.roundToInt(),
        vitaminB1 = totalVitaminB1,
        vitaminB2 = totalVitaminB2,
        vitaminB5 = totalVitaminB5.roundToInt(),
        vitaminB6 = totalVitaminB6,
        vitaminB9 = totalVitaminB9.roundToInt(),
        vitaminC = totalVitaminC.roundToInt(),
        vitaminE = totalVitaminE.roundToInt(),
        vitaminK = totalVitaminK.roundToInt(),
        K = totalK.roundToInt(),
        Ca = totalCa.roundToInt(),
        Mg = totalMg.roundToInt(),
        P = totalP.roundToInt(),
        Fe = totalFe.roundToInt(),
        I = totalI.roundToInt(),
        Zn = totalZn.roundToInt(),
        F = totalF.roundToInt()
    )
}