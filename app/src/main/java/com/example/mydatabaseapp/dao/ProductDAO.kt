package com.example.mydatabaseapp.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.mydatabaseapp.DatabaseHelper
import com.example.mydatabaseapp.models.Product

class ProductDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun addProduct(product: Product) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("Product_Name", product.productName)
            put("Kcal", product.kcal)
            put("Protein", product.protein)
            put("Fat", product.fat)
            put("Carbonhydrates", product.carbohydrates)
        }

        db.insert("Product", null, values)
        db.close()
    }

    fun getAllProducts(): List<Product> {
        val products = mutableListOf<Product>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM Product", null)

        if (cursor.moveToFirst()) {
            do {
                val product = Product(
                    idProduct = cursor.getInt(0),
                    productName = cursor.getString(1),
                    kcal = cursor.getDouble(2),
                    protein = cursor.getDouble(3),
                    fat = cursor.getDouble(4),
                    carbohydrates = cursor.getDouble(5)
                )
                products.add(product)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return products
    }
}
