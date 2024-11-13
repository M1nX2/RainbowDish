//package com.example.mydatabaseapp
package com.example.rainbowdish


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mydatabaseapp.dao.ProductDAO
import com.example.mydatabaseapp.models.Product
import com.example.rainbowdish.R


class MainActivity : AppCompatActivity() {
    private lateinit var productDAO: ProductDAO

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productDAO = ProductDAO(this)

        // Получение всех продуктов
        val products = productDAO.getAllProducts()
        for (product in products) {
            Toast.makeText(this, "Product: ${product.productName}", Toast.LENGTH_SHORT).show()
        }
    }
}
