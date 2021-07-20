
package com.example.sharetodo

import Database.MyTask
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
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

    private var lisItem: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_addtask)
        layoutList = findViewById(R.id.layout_list)

        auth = FirebaseAuth.getInstance()
        val currentuser = auth.currentUser

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        bt_addlist.setOnClickListener {
            addList()
        }

        if(currentuser != null) {
            bt_mytask.setOnClickListener {
                saveData()
            }
            bt_Everyone.setOnClickListener {
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

    /*fun checkandRead(){
        var result = true
        for (i in 0 until layoutList!!.childCount) {
            val listItem = layoutList!!.getChildAt(i)
            val edtList = listItem.findViewById<View>(R.id.edt_list) as EditText
            if (edtList.text.toString() != "") {

            } else {
                result = false
                break
            }

        }
    }*/

    private fun saveData(){
        val judul = edt_update_judul.text.toString().trim()

        val ref = FirebaseDatabase.getInstance().getReference("MyTask")
        val myTaskId = ref.push().key
        val currentUser = auth.currentUser
        val username = currentUser?.displayName.toString()
        val sdf = SimpleDateFormat("HH:mm a")
        val cal = Calendar.getInstance()
        val waktu = sdf.format(cal.time)
        val myTask = MyTask(username, myTaskId, judul, waktu)

        if(myTaskId != null){
            ref.child(myTaskId).setValue(myTask).addOnCompleteListener{
                Toast.makeText(applicationContext, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


