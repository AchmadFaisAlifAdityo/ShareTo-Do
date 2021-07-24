
package com.example.sharetodo.activity

import Database.MyTask
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sharetodo.R
import com.example.sharetodo.entity.ItemLIst
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_addtask.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddTaskActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database: FirebaseDatabase? = null
    var layoutList: LinearLayout? = null

    private var lisItem: ArrayList<ItemLIst> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_addtask)
        layoutList = findViewById(R.id.layout_list_upd)

        auth = FirebaseAuth.getInstance()
        val currentuser = auth.currentUser

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        if(currentuser != null) {
            bt_addlist.setOnClickListener{
                addList()
            }
            bt_mytask.setOnClickListener{
                checkandRead()
                saveData()
                val intent = Intent(this@AddTaskActivity, MyTaskActivity::class.java)
                startActivity(intent)

            }
            bt_Everyone.setOnClickListener{
                checkandRead()
                saveData()
            }
        }
    }

    private fun addList(){
        val v: View = layoutInflater.inflate(R.layout.card_layout, null, false)

        val listItem = v.findViewById(R.id.edt_list) as EditText
        val deleteList = v.findViewById(R.id.bt_deletelist) as ImageView

        deleteList.setOnClickListener { removeView(v) }
        layoutList!!.addView(v)
    }

    private fun removeView(view: View) {
        layoutList!!.removeView(view)
    }

    private fun checkandRead(): Boolean {
        lisItem.clear()
        var result = true
        for (i in 0 until layoutList!!.childCount) {

            val listItem = layoutList!!.getChildAt(i)
            val edtList = listItem.findViewById<View>(R.id.edt_list) as EditText
            val il = ItemLIst()

            if (edtList.text.toString() != "") {
                il.setItem(edtList.text.toString())

            } else {
                result = false
                break
            }

            lisItem.add(il)

        }

        if (lisItem.size == 0) {
            Toast.makeText(this, "Add Item List First!", Toast.LENGTH_SHORT).show()
        } else if (!result) {
            Toast.makeText(this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show()
        }

        return true
    }

    private fun saveData(){
        val judul = edt_update_judul.text.toString().trim()
        val ref = FirebaseDatabase.getInstance().getReference("MyTask")
        val myTaskId = ref.push().key
        val currentUser = auth.currentUser
        val username = currentUser?.displayName.toString()
        val sdf = SimpleDateFormat("HH:mm a")
        val cal = Calendar.getInstance()
        val waktu = sdf.format(cal.time)
        val myTask = MyTask(username, myTaskId, judul, waktu, lisItem)

        if(myTaskId != null){
            ref.child(myTaskId).setValue(myTask).addOnCompleteListener{
                Toast.makeText(applicationContext, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}



