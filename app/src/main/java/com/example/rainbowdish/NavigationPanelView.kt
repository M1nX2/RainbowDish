package com.example.rainbowdish


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.rainbowdish.screens.StatsShow
import com.example.rainbowdish.screens.ParametrsShow
import com.example.rainbowdish.screens.ProductsShow
import com.example.rainbowdish.screens.RecipesShow

class NavigationPanelView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    init {
        init(context)
    }

    private fun init(context: Context) {
        // Подключаем layout
        LayoutInflater.from(context).inflate(R.layout.downpanel_layout, this, true)

        // Инициализация кнопок
        val buttonParametrs = findViewById<LinearLayout>(R.id.buttonParametrs)
        val buttonProducts = findViewById<LinearLayout>(R.id.buttonProducts)
        val buttonStats = findViewById<LinearLayout>(R.id.buttonStats)
        val buttonRecipes = findViewById<LinearLayout>(R.id.buttonRecipes)




        val iconRecipes = findViewById<ImageView>(R.id.iconRecipes)
        // Навешиваем обработчики событий
        buttonParametrs.setOnClickListener {

            navigateTo(context, ParametrsShow::class.java)
        }

        buttonProducts.setOnClickListener {

            navigateTo(context, ProductsShow::class.java)
        }

        buttonStats.setOnClickListener {

            navigateTo(context, StatsShow::class.java)
        }

        buttonRecipes.setOnClickListener {
            iconRecipes.setImageResource(R.drawable.recipes_active)
            navigateTo(context, RecipesShow::class.java)
        }

    }

    private fun navigateTo(context: Context, destinationClass: Class<*>) {
        // Создаем Intent для перехода на другую активность
        val intent = Intent(context, destinationClass)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(intent)

        if (context is Activity) {
            context.overridePendingTransition(0, 0)
        }
    }
}
