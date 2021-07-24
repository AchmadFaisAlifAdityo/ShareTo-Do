package com.example.sharetodo.adapter

import Database.MyTask
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.sharetodo.R

class PublicTaskAdapter(val mCtx : Context, val LayoutResid : Int, val publicTaskList : List<MyTask>) : ArrayAdapter<MyTask>(mCtx,LayoutResid,publicTaskList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(LayoutResid,null)

        val tvpJudul : TextView = view.findViewById(R.id.tvp_judultask)
        val tvpAuthor : TextView = view.findViewById(R.id.tvp_author)
        val tvpWaktu : TextView = view.findViewById(R.id.tvp_waktu)

        val MyTask = publicTaskList[position]

        tvpJudul.text = MyTask.judul
        tvpAuthor.text = MyTask.author
        tvpWaktu.text = MyTask.waktu

        return view
    }
}