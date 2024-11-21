package com.example.rainbowdish.screens

import android.os.Bundle
import android.widget.ImageView
import com.example.rainbowdish.R
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
class ParametrsShow: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parametrs_show)
        val iconParametrs = findViewById<ImageView>(R.id.iconParametrs)
        iconParametrs.setImageResource(R.drawable.parametrs_active)
    }
}