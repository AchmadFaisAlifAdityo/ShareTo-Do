
package com.example.sharetodo

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_addtask.*

class AddTaskActivity : AppCompatActivity() {
    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>? = null
    private var lisItem: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addtask)


        layoutManager = LinearLayoutManager(this)
        rv_list.layoutManager = layoutManager

        adapter = RecycleViewAdapter()
        rv_list.adapter = adapter

        lisItem =
    }
}


