package com.example.rainbowdish.screens

import android.os.Bundle
import com.example.rainbowdish.R
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class ProductAdd: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_add)
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.rf362lghvknj))
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.rrg39ozowad))
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.ri6428c11o6))
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.rn1hvgpnv7bd))
    }
}