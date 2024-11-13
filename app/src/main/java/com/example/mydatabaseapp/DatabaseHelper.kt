package com.example.mydatabaseapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "RainbowDish.db" // Имя существующей базы данных
        private const val DATABASE_VERSION = 1
    }

    private val databasePath: String = context.getDatabasePath(DATABASE_NAME).path

    init {
        // Проверка, существует ли уже база данных в приложении
        if (!File(databasePath).exists()) {
            try {
                copyDatabase(context)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @Throws(IOException::class)
    private fun copyDatabase(context: Context) {
        // Открытие исходного файла из assets и запись его в папку баз данных приложения
        context.assets.open(DATABASE_NAME).use { inputStream ->
            FileOutputStream(databasePath).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Пустая реализация, так как мы используем уже существующую базу данных
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Пустая реализация, если нет необходимости обновлять структуру
    }
}
