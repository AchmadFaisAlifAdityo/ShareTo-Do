package com.example.sharetodo

import Database.MyTask
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_addtask.*
import kotlinx.android.synthetic.main.activity_mytask.*

class MyTaskActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private lateinit var ref : DatabaseReference
    private lateinit var myTaskList : MutableList<MyTask>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mytask)
        ref = FirebaseDatabase.getInstance().getReference("MyTask")


        bt_addTask.setOnClickListener{
            this.startActivity(Intent(this, AddTaskActivity::class.java))
        }
        myTaskList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    myTaskList.clear()
                    for(h in snapshot.children){
                        val myTask = h.getValue(MyTask::class.java)
                        if(myTask != null){
                            myTaskList.add(myTask)
                        }
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