package com.example.rainbowdish.screens

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.rainbowdish.R
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mydatabaseapp.models.Product
import kotlin.math.roundToInt

class ProductAdd : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_add)

        val header: TextView = findViewById(R.id.header_title)
        header.text = "Продукты"

        val iconProducts = findViewById<ImageView>(R.id.iconProducts)
        iconProducts.setImageResource(R.drawable.products_active)

        val product = intent.getParcelableExtra<Product>("product")
        // Получение данных из Intent
        if (product != null) {
            // Установка данных в UI
            findViewById<TextView>(R.id.product_name).text = product.productName
            findViewById<TextView>(R.id.kcal).text = "${product.kcal.toInt()} кк"
            findViewById<TextView>(R.id.protein).text = "${product.protein} г"
            findViewById<TextView>(R.id.fat).text = "${product.fat} г"
            findViewById<TextView>(R.id.carbohydrates).text = "${product.carbohydrates} г"

            // Витамины
            findViewById<TextView>(R.id.VitA).text = "A: ${product.vitA} мг"
            findViewById<TextView>(R.id.VitB1).text = "B1: ${product.vitB1} мг"
            findViewById<TextView>(R.id.VitB2).text = "B2: ${product.vitB2} мг"
            findViewById<TextView>(R.id.VitB5).text = "B5: ${product.vitB5} мг"
            findViewById<TextView>(R.id.VitB6).text = "B6: ${product.vitB6} мг"
            findViewById<TextView>(R.id.VitB9).text = "B9: ${product.vitB9} мг"
            findViewById<TextView>(R.id.VitC).text = "C: ${product.vitC} мг"
            findViewById<TextView>(R.id.VitE).text = "E: ${product.vitE} мг"
            findViewById<TextView>(R.id.VitK).text = "K: ${product.vitK} мг"

            // Минералы
            findViewById<TextView>(R.id.K).text = "K: ${product.k} мг"
            findViewById<TextView>(R.id.Ca).text = "Ca: ${product.ca} мг"
            findViewById<TextView>(R.id.Mg).text = "Mg: ${product.mg} мг"
            findViewById<TextView>(R.id.P).text = "P: ${product.p} мг"
            findViewById<TextView>(R.id.Fe).text = "Fe: ${product.fe} мг"
            findViewById<TextView>(R.id.I).text = "I: ${product.i} мг"
            findViewById<TextView>(R.id.Zn).text = "Zn: ${product.zn} мг"
            findViewById<TextView>(R.id.F).text = "F: ${product.f} мг"

            // Установка изображения
            val productImageView = findViewById<ImageView>(R.id.product_image)
            val imageResourceId = product.productImg?.let {
                resources.getIdentifier(it, "drawable", packageName)
            } ?: 0
            if (imageResourceId != 0) {
                findViewById<ImageView>(R.id.product_image).setImageResource(imageResourceId)
            } else {
                // Вставить дефолтное изображение или обработать ситуацию
                findViewById<ImageView>(R.id.product_image).setImageResource(R.drawable.krest)
            }

        }
    }
}