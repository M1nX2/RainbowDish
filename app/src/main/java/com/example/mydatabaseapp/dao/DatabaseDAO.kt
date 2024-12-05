package com.example.mydatabaseapp.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.icu.text.AlphabeticIndex
import android.util.Log
import com.example.mydatabaseapp.DatabaseHelper
import com.example.mydatabaseapp.models.Product
import com.example.mydatabaseapp.models.Recipe
import com.example.mydatabaseapp.models.RecipeProduct
import com.example.mydatabaseapp.models.Record
import com.example.mydatabaseapp.models.User
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class DatabaseDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)


    companion object {
        private const val TABLE_USERS = "User"
        private const val COLUMN_ID = "Id"
        private const val COLUMN_GENDER = "Gender"
        private const val COLUMN_WEIGHT = "Weight"
        private const val COLUMN_HEIGHT = "Height"
        private const val COLUMN_GOAL = "Goal"
    }
    // Получение всех продуктов из таблицы Product

    fun printDatabaseTables() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null)
        if (cursor.moveToFirst()) {
            do {
                val tableName = cursor.getString(0)
                Log.d("DatabaseTables", "Table: $tableName")
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
    }

    fun getAllProducts(): List<Product> {
        val products = mutableListOf<Product>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM Product ORDER BY Product_Name ASC", null)

        if (cursor.moveToFirst()) {
            do {
                val product = Product(
                    idProduct = cursor.getInt(cursor.getColumnIndexOrThrow("ID_Product")),
                    productName = cursor.getString(cursor.getColumnIndexOrThrow("Product_Name")),
                    kcal = cursor.getDouble(cursor.getColumnIndexOrThrow("Kcal")),
                    protein = cursor.getDouble(cursor.getColumnIndexOrThrow("Protein")),
                    fat = cursor.getDouble(cursor.getColumnIndexOrThrow("Fat")),
                    carbohydrates = cursor.getDouble(cursor.getColumnIndexOrThrow("Carbonhydrates")),
                    vitA = cursor.getDouble(cursor.getColumnIndexOrThrow("VitA")),
                    vitB1 = cursor.getDouble(cursor.getColumnIndexOrThrow("VitB1")),
                    vitB2 = cursor.getDouble(cursor.getColumnIndexOrThrow("VitB2")),
                    vitB5 = cursor.getDouble(cursor.getColumnIndexOrThrow("VitB5")),
                    vitB6 = cursor.getDouble(cursor.getColumnIndexOrThrow("VitB6")),
                    vitB9 = cursor.getDouble(cursor.getColumnIndexOrThrow("VitB9")),
                    vitC = cursor.getDouble(cursor.getColumnIndexOrThrow("VitC")),
                    vitE = cursor.getDouble(cursor.getColumnIndexOrThrow("VitE")),
                    vitK = cursor.getDouble(cursor.getColumnIndexOrThrow("VitK")),
                    k = cursor.getDouble(cursor.getColumnIndexOrThrow("K")),
                    ca = cursor.getDouble(cursor.getColumnIndexOrThrow("Ca")),
                    mg = cursor.getDouble(cursor.getColumnIndexOrThrow("Mg")),
                    p = cursor.getDouble(cursor.getColumnIndexOrThrow("P")),
                    fe = cursor.getDouble(cursor.getColumnIndexOrThrow("Fe")),
                    i = cursor.getDouble(cursor.getColumnIndexOrThrow("I")),
                    zn = cursor.getDouble(cursor.getColumnIndexOrThrow("Zn")),
                    f = cursor.getDouble(cursor.getColumnIndexOrThrow("F")),
                    avgWeight = cursor.getDouble(cursor.getColumnIndexOrThrow("AVGWeight")),
                    productImg = cursor.getString(cursor.getColumnIndexOrThrow("ProductIMG"))
                )
                products.add(product)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return products
    }

    // Получение всех рецептов из таблицы Recipe
    fun getAllRecipes(): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM Recipe", null)

        if (cursor.moveToFirst()) {
            do {
                val recipe = Recipe(
                    idRecipe = cursor.getInt(cursor.getColumnIndexOrThrow("ID_Pecipe")),
                    nameR = cursor.getString(cursor.getColumnIndexOrThrow("Name_R")),
                    timeR = cursor.getString(cursor.getColumnIndexOrThrow("Time_R")),  // может быть null
                    descR = cursor.getString(cursor.getColumnIndexOrThrow("Desc_R")),
                    weight = cursor.getDouble(cursor.getColumnIndexOrThrow("Weight")),
                    portions = cursor.getInt(cursor.getColumnIndexOrThrow("Portions"))
                )
                recipes.add(recipe)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return recipes
    }


    // Получение всех записей из таблицы Recipe_Product
    fun getAllRecipeProducts(): List<RecipeProduct> {
        val recipeProducts = mutableListOf<RecipeProduct>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM Recipe_Product", null)

        if (cursor.moveToFirst()) {
            do {
                val recipeProduct = RecipeProduct(
                    idRecipeProduct = cursor.getInt(cursor.getColumnIndexOrThrow("ID_Recipe_Product")),
                    recipeId = cursor.getInt(cursor.getColumnIndexOrThrow("Recipe_ID")),
                    productId = cursor.getInt(cursor.getColumnIndexOrThrow("Product_ID")),
                    quantity = cursor.getDouble(cursor.getColumnIndexOrThrow("Quantity"))
                )
                recipeProducts.add(recipeProduct)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return recipeProducts
    }

    // Получение всех записей из таблицы Record
    fun getAllRecords(): List<Record> {
        val records = mutableListOf<Record>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM Record", null)

        // Проверяем, есть ли записи в курсоре
        if (cursor.moveToFirst()) {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy") // Укажите формат вашей даты

            do {
                val date = cursor.getString(cursor.getColumnIndexOrThrow("Date"))
                val localDate = LocalDate.parse(date, formatter)

                val record = Record(
                    idRecord = cursor.getInt(cursor.getColumnIndexOrThrow("ID_Record")),
                    date = localDate,
                    productId = cursor.getInt(cursor.getColumnIndexOrThrow("Product_ID")),
                    quantity = cursor.getDouble(cursor.getColumnIndexOrThrow("Quantity"))
                )

                records.add(record)
            } while (cursor.moveToNext()) // Переходим к следующей строке
        } else {
            println("Таблица Record пуста.")
        }
        cursor.close()
        db.close()

        return records
    }

    fun addRecord(record: Record) {
        val db = dbHelper.writableDatabase

        // Создаем объект ContentValues для добавления данных в базу данных
        val values = ContentValues().apply {
            // Преобразуем дату в строку с нужным форматом
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val formattedDate = record.date.format(formatter)

            // Заполняем значения для записи в таблице
            put("Date", formattedDate)  // Сохраняем отформатированную дату
            put("Product_ID", record.productId)
            put("Quantity", record.quantity)
        }

        // Вставляем запись в таблицу Record
        val newRowId = db.insert("Record", null, values)

        // Логирование, если необходимо
        if (newRowId == -1L) {
            println("Ошибка при добавлении записи.")
        } else {
            println("Запись успешно добавлена. ID записи: $newRowId")
        }

        db.close()
    }


    // Получение всех пользователей из таблицы User
    fun getAllUsers(): List<User> {
        val users = mutableListOf<User>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM User", null)

        if (cursor.moveToFirst()) {
            do {
                val user = User(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("ID")),
                    gender = cursor.getString(cursor.getColumnIndexOrThrow("Gender")),
                    weight = cursor.getInt(cursor.getColumnIndexOrThrow("Weight")),
                    height = cursor.getInt(cursor.getColumnIndexOrThrow("Height")),
                    goal = cursor.getString(cursor.getColumnIndexOrThrow("Goal"))
                )
                users.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return users
    }

    // Метод для добавления пользователя
    fun addUser(user: User): Long {
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val contentValues = ContentValues().apply {
            put(COLUMN_GENDER, user.gender)
            put(COLUMN_WEIGHT, user.weight)
            put(COLUMN_HEIGHT, user.height)
            put(COLUMN_GOAL, user.goal)
        }

        // Вставка данных в таблицу User
        val userId = db.insert(TABLE_USERS, null, contentValues)

        // Закрываем базу данных после операции
        db.close()

        return userId
    }

    fun updateUser(user: User): Int {
        val db: SQLiteDatabase = dbHelper.writableDatabase

        // Создаем объект ContentValues с обновленными данными
        val contentValues = ContentValues().apply {
            put(COLUMN_GENDER, user.gender)
            put(COLUMN_WEIGHT, user.weight)
            put(COLUMN_HEIGHT, user.height)
            put(COLUMN_GOAL, user.goal)
        }

        // Обновляем запись в таблице User по ID
        val rowsUpdated = db.update(
            TABLE_USERS,
            contentValues,
            "$COLUMN_ID = ?",
            arrayOf(user.id.toString()) // Передаем ID пользователя для поиска нужной строки
        )

        // Закрываем базу данных
        db.close()

        return rowsUpdated // Возвращаем количество обновленных строк (обычно будет 1 или 0)
    }

}
