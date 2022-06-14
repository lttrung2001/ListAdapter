package com.ltbth.listadapter

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {
    private var userAdapter: UserAdapter = UserAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val changeButton = findViewById<Button>(R.id.btn_change)
        val rcvUser = findViewById<RecyclerView>(R.id.rcv).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
        userAdapter.submitList(getUsers())
        changeButton.setOnClickListener {
            userAdapter.submitList(getChanged())
        }

        val divider: RecyclerView.ItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        rcvUser.addItemDecoration(divider)


        val callback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val a: Int = viewHolder.adapterPosition
                val b: Int = target.adapterPosition
                Collections.swap(userAdapter.currentList, a, b)
                userAdapter.notifyItemMoved(a, b)
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos: Int = viewHolder.adapterPosition
                userAdapter.currentList.removeAt(pos)
                userAdapter.notifyItemRemoved(pos)
                Toast.makeText(this@MainActivity, "User deleted", Toast.LENGTH_LONG)
                    .show()
            }
        }
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(rcvUser)
    }

    private fun getChanged(): List<User> {
        var i = 100
        val users = arrayListOf<User>()
        users.addAll(userAdapter.currentList)
        for (item in users) {
            i = i.inc()
            item.name = "Username $i"
        }
        Toast.makeText(this@MainActivity, "Each user id plus 100", Toast.LENGTH_LONG)
            .show()
        return users
    }

    private fun getUsers(): MutableList<User> {
        val users = arrayListOf<User>()
        for (i in 1..20) {
            users.add(User(R.drawable.me, "Username $i"))
        }
        return users
    }
}