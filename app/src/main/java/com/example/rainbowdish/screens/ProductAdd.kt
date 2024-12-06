package com.example.rainbowdish.screens

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.rainbowdish.R
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mydatabaseapp.dao.DatabaseDAO
import com.example.mydatabaseapp.models.Product
import com.example.mydatabaseapp.models.Record
import org.threeten.bp.LocalDate
import kotlin.math.roundToInt

class ProductAdd : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_add)

        val header: TextView = findViewById(R.id.header_title)
        header.text = "Продукты"

        val iconProducts = findViewById<ImageView>(R.id.iconProducts)
        iconProducts.setImageResource(R.drawable.products_active)

        val product = intent.getParcelableExtra<Product>("product")
        // Получение данных из Intent
        if (product != null) {
            // Установка данных в UI
            findViewById<TextView>(R.id.product_name).text = product.productName
            findViewById<TextView>(R.id.kcal).text = "${product.kcal.toInt()} кк"
            findViewById<TextView>(R.id.protein).text = "${product.protein} г"
            findViewById<TextView>(R.id.fat).text = "${product.fat} г"
            findViewById<TextView>(R.id.carbohydrates).text = "${product.carbohydrates} г"

            // Витамины
            findViewById<TextView>(R.id.VitA).text = "A: ${product.vitA} мкг"
            findViewById<TextView>(R.id.VitB1).text = "B1: ${product.vitB1} мг"
            findViewById<TextView>(R.id.VitB2).text = "B2: ${product.vitB2} мг"
            findViewById<TextView>(R.id.VitB5).text = "B5: ${product.vitB5} мг"
            findViewById<TextView>(R.id.VitB6).text = "B6: ${product.vitB6} мг"
            findViewById<TextView>(R.id.VitB9).text = "B9: ${product.vitB9} мкг"
            findViewById<TextView>(R.id.VitC).text = "C: ${product.vitC} мг"
            findViewById<TextView>(R.id.VitE).text = "E: ${product.vitE} мг"
            findViewById<TextView>(R.id.VitK).text = "K: ${product.vitK} мкг"

            // Минералы
            findViewById<TextView>(R.id.K).text = "K: ${product.k} мг"
            findViewById<TextView>(R.id.Ca).text = "Ca: ${product.ca} мг"
            findViewById<TextView>(R.id.Mg).text = "Mg: ${product.mg} мг"
            findViewById<TextView>(R.id.P).text = "P: ${product.p} мг"
            findViewById<TextView>(R.id.Fe).text = "Fe: ${product.fe} мг"
            findViewById<TextView>(R.id.I).text = "I: ${product.i} мкг"
            findViewById<TextView>(R.id.Zn).text = "Zn: ${product.zn} мг"
            findViewById<TextView>(R.id.F).text = "F: ${product.f} мг"

            // Установка изображения
            val productImageView = findViewById<ImageView>(R.id.product_image)
            val imageResourceId = product.productImg?.let {
                resources.getIdentifier(it, "drawable", packageName)
            } ?: 0
            if (imageResourceId != 0) {
                findViewById<ImageView>(R.id.product_image).setImageResource(imageResourceId)
            } else {
                // Вставить дефолтное изображение или обработать ситуацию
                findViewById<ImageView>(R.id.product_image).setImageResource(R.drawable.krest)
            }

            // Логика для Spinner
            val options = mutableListOf("Граммы")
            if (product.avgWeight != null && product.avgWeight!! > 0) {
                options.add("Штуки")
            }
            val spinner: Spinner = findViewById(R.id.spinner)
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter


            val productAddButton: LinearLayout = findViewById(R.id.product_add)
            productAddButton.setOnClickListener {
                addRecordToDatabase(product, spinner)
            }

            val quantityClearButton: ImageView = findViewById(R.id.quantity_clear)
            quantityClearButton.setOnClickListener {
                val quantityTextView: TextView = findViewById(R.id.rcnlt1dhflm)
                quantityTextView.setText("")
            }

            val backButton: ImageView = findViewById(R.id.back)
            backButton.setOnClickListener {
                finish()
            }

            val quantityEditText: EditText = findViewById(R.id.rcnlt1dhflm)
            val unitSpinner: Spinner = findViewById(R.id.spinner)

            // Для вычисления finalQuantity
            var finalQuantity: Double = 0.0

            // Слушатель изменений в EditText (количество)
            // Слушатель изменений в EditText (количество)
            quantityEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    // Получаем введенное количество
                    val quantityText = s.toString()
                    val quantity = quantityText.toDoubleOrNull() ?: 0.0

                    // Получаем выбранную единицу измерения из Spinner
                    val selectedUnit = unitSpinner.selectedItem.toString()

                    // Вычисляем finalQuantity в зависимости от выбранной единицы измерения
                    finalQuantity = if (selectedUnit == "Штуки" && product.avgWeight != null) {
                        quantity * product.avgWeight!!
                    } else {
                        quantity
                    }

                    // Отображаем finalQuantity (например, в другом TextView)
                    // Например:
                    // someTextView.text = "Вес: $finalQuantity"
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            // Слушатель изменений в Spinner (единица измерения)
            unitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // Получаем выбранную единицу измерения
                    val selectedUnit = parent.getItemAtPosition(position).toString()

                    // Получаем текущее количество из EditText
                    val quantityText = quantityEditText.text.toString()
                    val quantity = quantityText.toDoubleOrNull() ?: 0.0

                    // Пересчитываем finalQuantity
                    finalQuantity = if (selectedUnit == "Штуки" && product.avgWeight != null) {
                        quantity * product.avgWeight!!
                    } else {
                        quantity
                    }

                    // Отображаем finalQuantity (например, в другом TextView)
                    // someTextView.text = "Вес: $finalQuantity"
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }
    fun addRecordToDatabase(product: Product, spinner: Spinner) {
        val selectedUnit = spinner.selectedItem.toString()
        val quantityTextView: TextView = findViewById(R.id.rcnlt1dhflm)
        val quantity = quantityTextView.text.toString().toDoubleOrNull()

        if (quantity == null) {
            return
        }

        val finalQuantity = if (selectedUnit == "Штуки" && product.avgWeight != null) {
            quantity * product.avgWeight!!
        } else {
            quantity
        }

        val record = Record(
            idRecord = 0, // Или генерируйте автоматически, если база поддерживает автоинкремент
            date = LocalDate.now(),
            productId = product.idProduct, // Предположим, у продукта есть ID
            quantity = finalQuantity
        )

        val databaseDAO = DatabaseDAO(this)
        databaseDAO.addRecord(record)


        finish() // Закрыть активность после добавления записей
}
}