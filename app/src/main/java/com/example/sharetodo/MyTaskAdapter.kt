package com.example.sharetodo

import Database.MyTask
import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyTaskAdapter(val mCtx : Context, val LayoutResid : Int, val myTaskList : List<MyTask>) : ArrayAdapter<MyTask> (mCtx,LayoutResid,myTaskList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(LayoutResid,null)

        val tvJudul : TextView = view.findViewById(R.id.tv_judultask)
        val tvAuthor : TextView = view.findViewById(R.id.tv_author)
        val tvWaktu : TextView = view.findViewById(R.id.tv_waktu)

        val MyTask = myTaskList[position]

        tvJudul.text = MyTask.judul
        //tvAuthor.text = MyTask.author.toString()

        return view
    }
}