
package com.example.sharetodo

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_layout.view.*

class RecycleViewAdapter(var c: Context) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {
    private var listItem: ArrayList<String> = ArrayList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = TODO()
        val title: EditText = TODO()
        var delete: ImageView

        init {
            checkBox = itemView.findViewById(R.id.cb_list)
            title = itemView.findViewById(R.id.edt_list)
            delete = itemView.findViewById(R.id.bt_deletelist)

        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecycleViewAdapter.ViewHolder, i: Int) {
        //holder.
    }

    override fun getItemCount(): Int {
        return listItem.count()
    }
}

