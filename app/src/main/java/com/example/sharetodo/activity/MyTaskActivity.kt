package com.example.sharetodo.activity

import Database.MyTask
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sharetodo.R
import com.example.sharetodo.adapter.MyTaskAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_mytask.*

class MyTaskActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private lateinit var ref : DatabaseReference
    private lateinit var myTaskList : MutableList<MyTask>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mytask)
        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference()


        bt_addTask.setOnClickListener{
            this.startActivity(Intent(this, AddTaskActivity::class.java))
        }
        myTaskList = mutableListOf()

        ref.child("MyTask").child(auth.uid.toString()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    myTaskList.clear()
                    for(h in snapshot.children){
                        var myTask = MyTask()
                        myTask.id = h.key
                        myTaskList.add(myTask)
                        loadTasks(myTask)
                    }
                    val adapter = MyTaskAdapter(applicationContext, R.layout.item_mytask,myTaskList)
                    lv_Mytask.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private  fun loadTasks(myTask: MyTask){
        ref.child("Tasks").child(myTask.id.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val task = snapshot.getValue(MyTask::class.java)
                    if (task != null) {
                        myTask.judul = task.judul
                        myTask.author = task.author
                        myTask.listItem  = task.listItem
                        myTask.waktu = task.waktu
                    }
                    val adapter = MyTaskAdapter(applicationContext, R.layout.item_mytask,myTaskList)
                    lv_Mytask.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}