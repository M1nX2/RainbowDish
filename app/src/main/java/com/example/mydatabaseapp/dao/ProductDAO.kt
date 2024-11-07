package com.example.mydatabaseapp.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.mydatabaseapp.DatabaseHelper
import com.example.mydatabaseapp.models.Product

class ProductDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    // Добавление продукта в базу данных
    fun addProduct(product: Product) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("Product_Name", product.productName)
            put("Kcal", product.kcal)
            put("Protein", product.protein)
            put("Fat", product.fat)
            put("Carbonhydrates", product.carbohydrates)
            put("VitA", product.vitA)
            put("VitB1", product.vitB1)
            put("VitB2", product.vitB2)
            put("VitB3", product.vitB3)
            put("VitB5", product.vitB5)
            put("VitB6", product.vitB6)
            put("VitB7", product.vitB7)
            put("VitB9", product.vitB9)
            put("VitB12", product.vitB12)
            put("VitC", product.vitC)
            put("VitD", product.vitD)
            put("VitE", product.vitE)
            put("VitK", product.vitK)
            put("K", product.k)
            put("Ca", product.ca)
            put("Mg", product.mg)
            put("P", product.p)
            put("Fe", product.fe)
            put("I", product.i)
            put("Zn", product.zn)
            put("F", product.f)
            put("AVGWeight", product.avgWeight)
            put("ProductIMG", product.productImg)
        }

        db.insert("Product", null, values)
        db.close()
    }

    // Получение всех продуктов из базы данных
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
                    carbohydrates = cursor.getDouble(5),
                    vitA = cursor.getDouble(6),
                    vitB1 = cursor.getDouble(7),
                    vitB2 = cursor.getDouble(8),
                    vitB3 = cursor.getDouble(9),
                    vitB5 = cursor.getDouble(10),
                    vitB6 = cursor.getDouble(11),
                    vitB7 = cursor.getDouble(12),
                    vitB9 = cursor.getDouble(13),
                    vitB12 = cursor.getDouble(14),
                    vitC = cursor.getDouble(15),
                    vitD = cursor.getDouble(16),
                    vitE = cursor.getDouble(17),
                    vitK = cursor.getDouble(18),
                    k = cursor.getDouble(19),
                    ca = cursor.getDouble(20),
                    mg = cursor.getDouble(21),
                    p = cursor.getDouble(22),
                    fe = cursor.getDouble(23),
                    i = cursor.getDouble(24),
                    zn = cursor.getDouble(25),
                    f = cursor.getDouble(26),
                    avgWeight = cursor.getDouble(27),
                    productImg = cursor.getString(28) // Заметьте, что мы добавили productImg
                )
                products.add(product)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return products
    }
}
