package com.example.rainbowdish.screens

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mydatabaseapp.adapters.ProductAdapter
import com.example.mydatabaseapp.dao.DatabaseDAO
import com.example.rainbowdish.R

class ProductsShow: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_show)

        val header: TextView = findViewById(R.id.header_title)
        header.text = "Продукты"

        val iconProducts = findViewById<ImageView>(R.id.iconProducts)
        iconProducts.setImageResource(R.drawable.products_active)
        // Инициализация RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewProducts)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // 2 карточки в строке

        // Получение списка продуктов из базы данных
        val databaseDAO = DatabaseDAO(this)
        val productList = databaseDAO.getAllProducts()

        // Установка адаптера для RecyclerView
        val adapter = ProductAdapter(this, productList)
        recyclerView.adapter = adapter

    }
}