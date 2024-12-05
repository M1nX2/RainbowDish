package com.example.rainbowdish.adapters

import android.content.Context
import org.threeten.bp.LocalDate
import org.threeten.bp.DayOfWeek
import kotlin.math.roundToInt
import com.example.mydatabaseapp.models.Record
import com.example.mydatabaseapp.models.Product
import com.example.mydatabaseapp.adapters.NutritionData
import com.example.mydatabaseapp.dao.DatabaseDAO

/**
 * Адаптер для расчета данных о питании за неделю.
 */
class WeeklyNutritionAdapter(
    context: Context // Передаем контекст
) {
    // Инициализация DAO
    private val databaseDAO: DatabaseDAO = DatabaseDAO(context)

    // Инициализация данных
    private val foodRecords: List<Record> = databaseDAO.getAllRecords() // Получаем записи из базы данных
    private val products: List<Product> = databaseDAO.getAllProducts() // Получаем продукты из базы данных


    /**
     * Рассчитать данные о питании за указанную неделю.
     * @param today Дата, от которой рассчитывается неделя. По умолчанию используется текущая дата.
     * @return NutritionData Объект с данными о питании.
     */
    fun calculateWeeklyNutrition(today: LocalDate = LocalDate.now()): NutritionData {
        // Получение начальной и конечной даты недели
        val startOfWeek = today.with(DayOfWeek.MONDAY)
        val endOfWeek = today.with(DayOfWeek.SUNDAY)

        // Фильтруем записи за текущую неделю
        val weeklyRecords = foodRecords.filter { it.date in startOfWeek..endOfWeek }

        // Используем сводный объект для накопления результатов
        val totals = Totals()

        // Подсчет данных для недели
        weeklyRecords.forEach { record ->
            products.find { it.idProduct == record.productId }?.let { product ->
                val ratio = record.quantity / 100.0
                totals.addProduct(product, ratio)
            }
        }

        // Возврат итогового объекта NutritionData
        return totals.toNutritionData()
    }

    /**
     * Класс для накопления итоговых данных.
     */
    private class Totals {
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

        /**
         * Добавляет данные о продукте с учетом соотношения (веса).
         */
        fun addProduct(product: Product, ratio: Double) {
            totalCalories += product.kcal * ratio
            totalProtein += product.protein * ratio
            totalFat += product.fat * ratio
            totalCarbs += product.carbohydrates * ratio
            totalVitaminA += product.vitA * ratio
            totalVitaminB1 += product.vitB1 * ratio
            totalVitaminB2 += product.vitB2 * ratio
            totalVitaminB5 += product.vitB5 * ratio
            totalVitaminB6 += product.vitB6 * ratio
            totalVitaminB9 += product.vitB9 * ratio
            totalVitaminC += product.vitC * ratio
            totalVitaminE += product.vitE * ratio
            totalVitaminK += product.vitK * ratio
            totalK += product.k * ratio
            totalCa += product.ca * ratio
            totalMg += product.mg * ratio
            totalP += product.p * ratio
            totalFe += product.fe * ratio
            totalI += product.i * ratio
            totalZn += product.zn * ratio
            totalF += product.f * ratio
        }

        /**
         * Преобразует накопленные данные в объект NutritionData.
         */
        fun toNutritionData(): NutritionData {
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
    }
}
