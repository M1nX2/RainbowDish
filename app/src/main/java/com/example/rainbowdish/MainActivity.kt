package com.example.rainbowdish

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.mydatabaseapp.adapters.NutritionData
import com.example.mydatabaseapp.dao.DatabaseDAO
import com.example.rainbowdish.screens.ParametrsShow
import com.example.rainbowdish.adapters.UserAdapter
import com.example.rainbowdish.adapters.WeeklyNutritionAdapter
import com.example.rainbowdish.screens.ProductAdd
import com.example.rainbowdish.screens.ProductsShow
import com.jakewharton.threetenabp.AndroidThreeTen


class MainActivity : AppCompatActivity() {
    private lateinit var databaseDAO: DatabaseDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidThreeTen.init(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        databaseDAO = DatabaseDAO(this)

        Log.d("DatabaseDAO", databaseDAO.getAllRecords().toString())

        databaseDAO.printDatabaseTables()

        val intent = Intent(this, ParametrsShow::class.java)
        startActivity(intent)
    }


}
