package com.example.rainbowdish.screens

import android.os.Bundle
import com.example.rainbowdish.R
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
class ParametrsAdd: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parametrs_add)
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.rkh7f1gp1t3))
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.re3f727n404p))
//        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.rfgxs5pkc89))
//        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.r94nuvv00u1o))
    }
}