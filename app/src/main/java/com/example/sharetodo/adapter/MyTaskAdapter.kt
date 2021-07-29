package com.example.sharetodo.adapter

import Database.MyTask
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.sharetodo.R
import com.example.sharetodo.activity.DetailMyTaskActivity
import com.example.sharetodo.activity.UpdateTaskActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MyTaskAdapter(val mCtx: Context, val LayoutResid: Int, var myTaskList: List<MyTask>) :
    ArrayAdapter<MyTask>(mCtx, LayoutResid, myTaskList) {

    lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(LayoutResid, null)
        auth = FirebaseAuth.getInstance()
        val tvJudul: TextView = view.findViewById(R.id.tv_judultask)
        val tvAuthor: TextView = view.findViewById(R.id.tv_author)
        val tvWaktu: TextView = view.findViewById(R.id.tv_waktu)
        val menu: ImageView = view.findViewById(R.id.actioncard)

        val MyTask = myTaskList[position]
        tvJudul.text = MyTask.judul
        loadUsername(MyTask.author, tvAuthor)
        tvWaktu.text = MyTask.waktu
        view.setOnClickListener {
            val intent = Intent(mCtx, DetailMyTaskActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("Id", MyTask.id)
            bundle.putSerializable("Judul", MyTask.judul)
            bundle.putSerializable("Itemlist", MyTask.listItem)
            intent.putExtras(bundle)
            mCtx.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }

        menu.setOnClickListener {
            popupMenu(it, MyTask, position)
        }

        return view
    }

    private fun popupMenu(v: View, mt: MyTask, position: Int) {
        val pMenu = PopupMenu(mCtx, v)
        pMenu.inflate(R.menu.option_menu)
        pMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.editlist -> {
                    //Statement untuk melakukann update
                    updateTask(mt)
                    true
                }
                R.id.deleteList -> {
                    //Statement untuk melakukan delete
                    deleteTask(mt.id.toString())
                    val tmp = myTaskList.toMutableList().apply {
                        removeAt(0)
                    }
                    myTaskList = tmp
                    Toast.makeText(mCtx, "Anda Telah Menghapus " + mt.judul, Toast.LENGTH_SHORT)
                        .show()
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

    private fun updateTask(mt: MyTask) {
        val intent = Intent(mCtx, UpdateTaskActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("Id", mt.id)
        bundle.putSerializable("Judul", mt.judul)
        bundle.putSerializable("Itemlist", mt.listItem)
        intent.putExtras(bundle)
        mCtx.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    private fun deleteTask(taskId: String) {
        database = FirebaseDatabase.getInstance().reference
        database.child(taskId).child(taskId).removeValue()
        database.child("Tasks").child(taskId).removeValue()
        database.child("MyTask").child(auth.uid.toString()).child(taskId).removeValue()
        database.child("PublicTask").child(taskId).removeValue()

    }


    private fun loadUsername(uID: String, tvAuthor: TextView) {
        database = FirebaseDatabase.getInstance().getReference("profile")
        database.child(uID).child("Username")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        tvAuthor.text = snapshot.value.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

    }
}
