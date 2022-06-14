package com.ltbth.listadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

class UserAdapter : ListAdapter<User, UserAdapter.UserViewHolder>(
    AsyncDifferConfig.Builder(UserDiffCallback())
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build()
) {
    private lateinit var users: List<User>

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.user_img)
        val textView: TextView = itemView.findViewById(R.id.user_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.apply {
            imageView.setImageResource(users[position].img)
            textView.text = users[position].name
        }
    }

    override fun submitList(list: List<User>?) {
        users = list?: arrayListOf()
        super.submitList(users)
    }

    override fun getItem(position: Int): User {
        return users[position]
    }
}