package com.example.rainbowdish.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.mydatabaseapp.dao.DatabaseDAO
import com.example.mydatabaseapp.models.User
import com.example.rainbowdish.R

class UserAdapter(private val context: Context) : BaseAdapter() {

    private val databaseDAO = DatabaseDAO(context)

    // Список пользователей
    private var users: List<User> = databaseDAO.getAllUsers()

    override fun getCount(): Int {
        return users.size
    }

    override fun getItem(position: Int): Any {
        return users[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.parametrs_show, parent, false)

        val user = users[position]

        // Настроим значения для отображения на лейауте
        val heightTextView = view.findViewById<TextView>(R.id.user_height)
        val weightTextView = view.findViewById<TextView>(R.id.user_weight)
        val genderTextView = view.findViewById<TextView>(R.id.user_gender)
        val goalTextView = view.findViewById<TextView>(R.id.user_goal)


        // Заполняем данные пользователя
        heightTextView.text = "${user.height} см"
        weightTextView.text = "${user.weight} кг"
        genderTextView.text = user.gender
        goalTextView.text = user.goal


        return view
    }

    // Метод для проверки наличия пользователей в базе данных
    fun hasUsers(): Boolean {
        return users.isNotEmpty()
    }
}
