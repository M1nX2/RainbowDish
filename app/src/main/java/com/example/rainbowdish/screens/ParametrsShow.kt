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
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.rq67t23gde9r))
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.rh1t72hr6gs7))
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.r2beq0v9iv9))
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.r0ay96i5ez9ac))
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.rimpywthfktm))
    }
}