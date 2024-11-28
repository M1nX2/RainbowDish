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

    override fun getItem(position: Int): User {
        return users[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }


    // Метод для проверки наличия пользователей в базе данных
    fun hasUsers(): Boolean {
        return users.isNotEmpty()
    }

}
