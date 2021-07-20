package com.example.sharetodo

import Database.MyTask
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MyTaskAdapter(val mCtx : Context, val LayoutResid : Int, val myTaskList : List<MyTask>) : ArrayAdapter<MyTask> (mCtx,LayoutResid,myTaskList){

    lateinit var database : DatabaseReference

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(LayoutResid,null)

        val tvJudul : TextView = view.findViewById(R.id.tv_judultask)
        val tvAuthor : TextView = view.findViewById(R.id.tv_author)
        val tvWaktu : TextView = view.findViewById(R.id.tv_waktu)
        val menu: ImageView = view.findViewById(R.id.actioncard)
        val MyTask = myTaskList[position]
        tvJudul.text = MyTask.judul
        tvAuthor.text = MyTask.author
        tvWaktu.text = MyTask.waktu
        menu.setOnClickListener{
            popupMenu(it)
        }
        return view
    }

    private fun popupMenu(v:View){
        val pMenu = PopupMenu(mCtx,v)

        pMenu.inflate(R.menu.option_menu)
        pMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.editlist -> {
                    val intent : Intent = Intent(v.context, UpdateTaskActivity::class.java)
                    intent.putExtra("title","")
                    Toast.makeText(mCtx,"EditText di pencet", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.deleteList -> {
                    val judul = v.findViewById<EditText>(R.id.edt_update_judul)
                    AlertDialog.Builder(mCtx)
                        .setTitle("Delete")
                        .setMessage("Apakah kamu yakin ingin menghapusnya?")
                        .setPositiveButton("Yes"){
                            dialog, _->

                            dialog.dismiss()
                        }
                        .setNegativeButton("No"){
                            dialog, _->
                            dialog.dismiss()
                        }
                    Toast.makeText(mCtx,"Delete di pencet", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> true
            }
        }
        pMenu.show()
        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menu = popup.get(pMenu)
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            .invoke(menu, true)
    }

    fun deleteTask(judul: String){
        database = FirebaseDatabase.getInstance().getReference("MyTask")
        database.child(judul).removeValue()

    }
}
