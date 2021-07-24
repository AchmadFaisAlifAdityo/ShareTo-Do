package com.example.sharetodo.adapter

import Database.MyTask
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import com.example.sharetodo.R
import com.example.sharetodo.activity.UpdateTaskActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyTaskAdapter(val mCtx : Context,val LayoutResid : Int, val myTaskList : List<MyTask>) : ArrayAdapter<MyTask> (mCtx,LayoutResid,myTaskList){

    lateinit var database : DatabaseReference
    lateinit var auth: FirebaseAuth

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(LayoutResid,null)

        val tvJudul : TextView = view.findViewById(R.id.tv_judultask)
        val tvAuthor : TextView = view.findViewById(R.id.tv_author)
        val tvWaktu : TextView = view.findViewById(R.id.tv_waktu)
        val menu: ImageView = view.findViewById(R.id.actioncard)
        val detail: CardView = view.findViewById(R.id.crd_view)

        val MyTask = myTaskList[position]
        tvJudul.text = MyTask.judul
        tvAuthor.text = MyTask.author
        tvWaktu.text = MyTask.waktu

        menu.setOnClickListener{
            popupMenu(it, MyTask)
        }

        return view
    }

    private fun popupMenu(v:View, mt: MyTask){
        val pMenu = PopupMenu(mCtx,v)
        pMenu.inflate(R.menu.option_menu)
        pMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.editlist -> {
                    //Statement untuk melakukann update
                    updateTask(mt)
                    true
                }
                R.id.deleteList -> {
                    //Statement untuk melakukan delete
                      deleteTask(mt.id.toString())
                      Toast.makeText(mCtx,"Anda Telah Menghapus " + mt.judul , Toast.LENGTH_SHORT).show()
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

    private fun updateTask(mt: MyTask){
        val intent = Intent(mCtx, UpdateTaskActivity::class.java)
        intent.putExtra("Id", mt.id)
        intent.putExtra("Judul", mt.judul)
       /* intent.putExtra("ItemList", mt.listItem)*/
        mCtx.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    private fun deleteTask(id: String){
        database = FirebaseDatabase.getInstance().getReference("MyTask")
        database.child(id).removeValue()

    }



}
