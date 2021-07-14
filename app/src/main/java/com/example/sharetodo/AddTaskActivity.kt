
package com.example.sharetodo

import Database.MyTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_addtask.*
import kotlinx.android.synthetic.main.activity_registration.*

class AddTaskActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database: FirebaseDatabase? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>? = null
    private var lisItem: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addtask)


        /*layoutManager = LinearLayoutManager(this)
        rv_list.layoutManager = layoutManager

        adapter = RecycleViewAdapter()
        rv_list.adapter = adapter*/
        auth = FirebaseAuth.getInstance()
        val currentuser = auth.currentUser

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        if(currentuser != null) {
            bt_mytask.setOnClickListener {
                saveData()
            }
        }
    }


    fun saveData(){
        val judul = edt_judul.text.toString().trim()

        val ref = FirebaseDatabase.getInstance().getReference("MyTask")
        val myTaskId = ref.push().key
        //val currentUser = auth.currentUser
        val myTask = MyTask(myTaskId, judul)

        if(myTaskId != null){
            ref.child(myTaskId).setValue(myTask).addOnCompleteListener{
                Toast.makeText(applicationContext, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


