package com.example.mydatabaseapp.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rainbowdish.R
import com.example.mydatabaseapp.models.Product
import com.example.rainbowdish.screens.ProductAdd
import kotlin.math.roundToInt

class ProductAdapter(private val context: Context, private val products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product_card, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.productName.text = product.productName
        holder.kcal.text = "${product.kcal.roundToInt()} кк"
        holder.protein.text = "${product.protein} г"
        holder.fat.text = "${product.fat} г"
        holder.carbohydrates.text = "${product.carbohydrates} г"

        holder.productImage.setImageResource(R.drawable.krest)

        // Установка изображения продукта, если есть
        if (!product.productImg.isNullOrEmpty()) {
            val imageResourceId = context.resources.getIdentifier(product.productImg, "drawable", context.packageName)
            if (imageResourceId != 0) {
                holder.productImage.setImageResource(imageResourceId)
            }
        }

        // Установка клика для перехода на другую активность
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductAdd::class.java)
            intent.putExtra("product", product) // Передаём объект
            context.startActivity(intent)

            if (context is Activity) {
                context.overridePendingTransition(0, 0)
            }
        }

    }

    override fun getItemCount(): Int = products.size

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.product_name)
        val kcal: TextView = view.findViewById(R.id.product_kcal)
        val protein: TextView = view.findViewById(R.id.product_protein)
        val fat: TextView = view.findViewById(R.id.product_fat)
        val carbohydrates: TextView = view.findViewById(R.id.product_carbohydrates)
        val productImage: ImageView = view.findViewById(R.id.product_image)
    }
}
