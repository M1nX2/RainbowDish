package com.example.rainbowdish

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.util.TypedValue
import com.example.mydatabaseapp.models.Recipe
import com.example.rainbowdish.R
import com.bumptech.glide.Glide
import com.example.mydatabaseapp.dao.DatabaseDAO
import kotlin.math.roundToInt

class RecipeShowPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_show_page)  // Set the layout


        val header: TextView = findViewById(R.id.header_title)
        header.text = "Рецепты"

        val iconRecipes = findViewById<ImageView>(R.id.iconRecipes)
        iconRecipes.setImageResource(R.drawable.recipes_active)
        // Get the Recipe object passed via intent (assuming it's passed from another activity)
        val recipe = intent.getParcelableExtra<Recipe>("recipe")

        // If the recipe is null, handle the case gracefully
        if (recipe != null) {
            fillRecipeData(recipe)
        } else {
            // Handle error or fallback logic here
        }
    }

    private fun fillRecipeData(recipe: Recipe) {
        // Find views by ID
        val kcalTextView: TextView = findViewById(R.id.kcal)
        val fatTextView: TextView = findViewById(R.id.fat)
        val proteinTextView: TextView = findViewById(R.id.protein)
        val carbohydratesTextView: TextView = findViewById(R.id.carbohydrates)
        val vesTextView: TextView = findViewById(R.id.ves)
        val timeTextView: TextView = findViewById(R.id.recipe_time)
        val productImageView: ImageView = findViewById(R.id.product_image)

        // Set the data from the recipe object to the views
        kcalTextView.text = "${recipe.kcal?.roundToInt()} кк"
        fatTextView.text = "${recipe.fats?.roundToInt()} гр"
        proteinTextView.text = "${recipe.protein?.roundToInt()} гр"
        carbohydratesTextView.text = "${recipe.carbohydrates?.roundToInt()} гр"
        vesTextView.text =
            "${recipe.weight.roundToInt()} г"  // Assuming "weight" is the amount, update if needed
        timeTextView.text = "${recipe.timeR ?: "Не указано"}"

        // Load the image if it's available (using Glide or any image loading library)
        if (recipe.recipeIMG != null) {
            // Assuming recipeIMG contains a drawable resource name (not a full URL)
            val resId = resources.getIdentifier(recipe.recipeIMG, "drawable", packageName)
            if (resId != 0) {
                Glide.with(this)
                    .load(resId)
                    .into(productImageView)
            }
        } else {
            // Load a default image if recipeIMG is null
            productImageView.setImageResource(R.drawable.krest)
        }

        var totalVitA = 0.0
        var totalVitB1 = 0.0
        var totalVitB2 = 0.0
        var totalVitB5 = 0.0
        var totalVitB6 = 0.0
        var totalVitB9 = 0.0
        var totalVitC = 0.0
        var totalVitE = 0.0
        var totalVitK = 0.0
        var totalK = 0.0
        var totalCa = 0.0
        var totalMg = 0.0
        var totalP = 0.0
        var totalFe = 0.0
        var totalI = 0.0
        var totalZn = 0.0
        var totalF = 0.0

        val databaseDAO = DatabaseDAO(this)

        val recipeProducts = databaseDAO.getRecipeProductsForRecipe(recipe.idRecipe)

        for (recipeProduct in recipeProducts) {
            // Fetch the Product object for the current RecipeProduct
            val product = databaseDAO.getProductById(recipeProduct.productId)

            // Calculate the total nutritional values by multiplying the product's nutritional values by the quantity
            if (product != null) {
                val quantity = recipeProduct.quantity / 100

                // Sum the vitamins for the current product
                totalVitA += product.vitA * quantity
                totalVitB1 += product.vitB1 * quantity
                totalVitB2 += product.vitB2 * quantity
                totalVitB5 += product.vitB5 * quantity
                totalVitB6 += product.vitB6 * quantity
                totalVitB9 += product.vitB9 * quantity
                totalVitC += product.vitC * quantity
                totalVitE += product.vitE * quantity
                totalVitK += product.vitK * quantity
                totalK += product.k * quantity
                totalCa += product.ca * quantity
                totalMg += product.mg * quantity
                totalP += product.p * quantity
                totalFe += product.fe * quantity
                totalI += product.i * quantity
                totalZn += product.zn * quantity
                totalF += product.f * quantity
            }
        }
        findViewById<TextView>(R.id.VitA).text = "A: ${"%.1f".format(totalVitA)} мкг"
        findViewById<TextView>(R.id.VitB1).text = "B1: ${"%.1f".format(totalVitB1)} мг"
        findViewById<TextView>(R.id.VitB2).text = "B2: ${"%.1f".format(totalVitB2)} мг"
        findViewById<TextView>(R.id.VitB5).text = "B5: ${"%.1f".format(totalVitB5)} мг"
        findViewById<TextView>(R.id.VitB6).text = "B6: ${"%.1f".format(totalVitB6)} мг"
        findViewById<TextView>(R.id.VitB9).text = "B9: ${"%.1f".format(totalVitB9)} мкг"
        findViewById<TextView>(R.id.VitC).text = "C: ${"%.1f".format(totalVitC)} мг"
        findViewById<TextView>(R.id.VitE).text = "E: ${"%.1f".format(totalVitE)} мг"
        findViewById<TextView>(R.id.VitK).text = "K: ${"%.1f".format(totalVitK)} мкг"
        findViewById<TextView>(R.id.K).text = "K: ${"%.1f".format(totalK)} мг"
        findViewById<TextView>(R.id.P).text = "P: ${"%.1f".format(totalP)} мг"
        findViewById<TextView>(R.id.Zn).text = "Zn: ${"%.1f".format(totalZn)} мг"
        findViewById<TextView>(R.id.Ca).text = "Ca: ${"%.1f".format(totalCa)} мг"
        findViewById<TextView>(R.id.Fe).text = "Fe: ${"%.1f".format(totalFe)} мг"
        findViewById<TextView>(R.id.F).text = "F: ${"%.1f".format(totalF)} мг"
        findViewById<TextView>(R.id.Mg).text = "Mg: ${"%.1f".format(totalMg)} мг"
        findViewById<TextView>(R.id.I).text = "I: ${"%.1f".format(totalI)} мкг"

        val ingredientList = recipe.ingredients?.split("//") ?: emptyList()
        val parentLayout = findViewById<LinearLayout>(R.id.ingredients)
        parentLayout.removeAllViews()

        if (ingredientList.isNotEmpty()) {
            // Функция для создания строки ингредиентов
            fun createRow(elements: List<String>): LinearLayout {
                val rowLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    gravity = Gravity.CENTER_HORIZONTAL
                }

                elements.forEach { ingredient ->
                    val parts = ingredient.split("-")
                    if (parts.size == 2) {
                        val name = parts[0].trim()
                        val quantity = parts[1].trim()

                        // Загружаем layout карточки
                        val ingredientView = layoutInflater.inflate(R.layout.ingredient_item, rowLayout, false)

                        // Настраиваем данные карточки
                        val nameTextView = ingredientView.findViewById<TextView>(R.id.ingredient_name)
                        val quantityTextView = ingredientView.findViewById<TextView>(R.id.ingredient_quantity)

                        nameTextView.text = name
                        quantityTextView.text = quantity

                        // Добавляем карточку в строку
                        rowLayout.addView(ingredientView)
                    }
                }
                return rowLayout
            }

            // Разбиваем список ингредиентов
            val firstRow = ingredientList.take(4) // Первые 4 элемента
            val secondRow = ingredientList.drop(4).take(4) // Оставшиеся
            val thirdRow = ingredientList.drop(8)

            // Создаем первую строку и добавляем в parentLayout
            val firstRowLayout = createRow(firstRow)
            parentLayout.addView(firstRowLayout)

            // Если есть элементы для второй строки, добавляем её
            if (secondRow.isNotEmpty()) {
                val parentLayout2 = findViewById<LinearLayout>(R.id.ingredients2)
                val secondRowLayout = createRow(secondRow)
                parentLayout2.addView(secondRowLayout)
                //parentLayout.addView(secondRowLayout)
            }

            if (thirdRow.isNotEmpty()) {
                val parentLayout3 = findViewById<LinearLayout>(R.id.ingredients3)
                val thirdRowLayout = createRow(thirdRow)
                parentLayout3.addView(thirdRowLayout)
            }
        }





        val recipeSteps = recipe.descR.split("//") // Разделяем шаги из descR
        val stepsContainer = findViewById<LinearLayout>(R.id.steps) // Контейнер для шагов
        stepsContainer.removeAllViews() // Очищаем контейнер перед заполнением

        if (recipeSteps.isNotEmpty()) {
            // Функция для создания карточки шага
            fun createStepCard(stepNumber: Int, stepDescription: String): LinearLayout {
                val stepLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(20, 10, 20, 10) // Отступы между шагами
                    }
                }

                // Горизонтальный контейнер для номера шага и изображения
                val headerLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }

                // Иконка шага
                val stepIcon = ImageView(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    setImageResource(R.drawable.eclipse_step) // Замените на свой ресурс
                }
                headerLayout.addView(stepIcon)

                // Номер шага
                val stepNumberText = TextView(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        marginStart = 10 // Отступ от иконки
                    }
                    text = "Шаг $stepNumber:"
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f) // Размер текста
                    setTextColor(Color.parseColor("#1A1C15"))
                }
                headerLayout.addView(stepNumberText)

                // Добавляем заголовок шага в макет
                stepLayout.addView(headerLayout)

                // Текст описания шага
                val stepDescriptionText = TextView(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(30, 5, 10, 10) // Отступы вокруг текста
                    }
                    text = stepDescription.trim()
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f) // Компактный размер текста
                    setTextColor(Color.parseColor("#5D6054"))
                }
                stepLayout.addView(stepDescriptionText)

                return stepLayout
            }

            // Добавляем шаги в контейнер
            recipeSteps.forEachIndexed { index, step ->
                val stepCard = createStepCard(index + 1, step)
                stepsContainer.addView(stepCard)
            }
        }


    }

}

