package com.example.mydatabaseapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
private const val DATABASE_VERSION = 2
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "my_database.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Создание таблиц
        db.execSQL(
            "CREATE TABLE Product (" +
                    "ID_Product INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Product_Name TEXT NOT NULL," +
                    "Kcal REAL NOT NULL," +
                    "Protein REAL NOT NULL," +
                    "Fat REAL NOT NULL," +
                    "Carbonhydrates REAL NOT NULL," +
                    "VitA REAL," + // Добавьте оставшиеся поля
                    "VitB1 REAL," +
                    "VitB2 REAL," +
                    "VitB3 REAL," +
                    "VitB5 REAL," +
                    "VitB6 REAL," +
                    "VitB7 REAL," +
                    "VitB9 REAL," +
                    "VitB12 REAL," +
                    "VitC REAL," +
                    "VitD REAL," +
                    "VitE REAL," +
                    "VitK REAL," +
                    "K REAL," +
                    "Ca REAL," +
                    "Mg REAL," +
                    "P REAL," +
                    "Fe REAL," +
                    "I REAL," +
                    "Zn REAL," +
                    "F REAL," +
                    "AVGWeight REAL," +
                    "ProductIMG TEXT" +
                    ");"
        )

        db.execSQL(
            "CREATE TABLE Recipe (" +
                    "ID_Pecipe INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Name_R TEXT NOT NULL," +
                    "Time_R TEXT," +
                    "Desc_R TEXT NOT NULL," +
                    "Weight REAL NOT NULL," +
                    "Portions INTEGER," +
                    "RecipeIMG TEXT" +
                    ");"
        )

        db.execSQL(
            "CREATE TABLE Recipe_Product (" +
                    "ID_Recipe_Product INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Recipe_ID INTEGER," +
                    "Product_ID INTEGER," +
                    "Quantity REAL," +
                    "FOREIGN KEY(Recipe_ID) REFERENCES Recipe(ID_Pecipe)," +
                    "FOREIGN KEY(Product_ID) REFERENCES Product(ID_Product)" +
                    ");"
        )

        db.execSQL(
            "CREATE TABLE Record (" +
                    "ID_Record INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Date TEXT," +
                    "Product_ID INTEGER," +
                    "Quantity REAL," +
                    "FOREIGN KEY(Product_ID) REFERENCES Product(ID_Product)" +
                    ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Обновление базы данных
        db.execSQL("DROP TABLE IF EXISTS Recipe_Product")
        db.execSQL("DROP TABLE IF EXISTS Record")
        db.execSQL("DROP TABLE IF EXISTS Recipe")
        db.execSQL("DROP TABLE IF EXISTS Product")
        onCreate(db) // Воссоздать таблицы
    }
}
