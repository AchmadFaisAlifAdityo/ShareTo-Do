package com.example.sharetodo.activity

import Database.MyTask
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
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_editask.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UpdateTaskActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var judul: EditText
    lateinit var item: EditText
    var layoutList: LinearLayout? = null
    private var lisItem: ArrayList<ItemLIst> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editask)
        layoutList = findViewById(R.id.layout_list_upd)
        getAndSetDataUpdate()
        bt_Submit.setOnClickListener {
            saveData()
        }
    }

    private fun addList(){
        val v: View = layoutInflater.inflate(R.layout.card_layout_upd, null, false)

        val listItem = v.findViewById(R.id.edt_list) as EditText
        val deleteList = v.findViewById(R.id.bt_deletelist) as ImageView

        deleteList.setOnClickListener { removeView(v) }
        layoutList!!.addView(v)
    }

    private fun removeView(view: View) {
        layoutList!!.removeView(view)
    }

/*    private fun checkandRead(): Boolean {
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

    private fun readList(){

    }*/

    private fun getAndSetDataUpdate() {
   /*     val v: View = layoutInflater.inflate(R.layout.card_layout_upd, null, false)*/
        judul = findViewById(R.id.edt_update_judul)
        item = findViewById(R.id.edt_list_upd)

        if (intent.extras != null) {
            judul.setText(intent.getStringExtra("Judul"))
            /*for (i in intent.getStringExtra("ItemList")!!.indices){
                val
                item.setText(intent.getStringExtra("ItemList"))
                layoutList!!.addView(v)
            }*/
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveData(){
        val ref = FirebaseDatabase.getInstance().getReference("MyTask")
        val myTaskId = ref.push().key
        val username = auth.toString()
        val sdf = SimpleDateFormat("HH:mm a")
        val cal = Calendar.getInstance()
        val waktu = sdf.format(cal.time)
        val myTask = MyTask(username, myTaskId, judul.toString(), waktu, ArrayList())

        if(myTaskId != null){
            ref.child(myTaskId).setValue(myTask).addOnCompleteListener{
                Toast.makeText(applicationContext, "Data berhasil di-update", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

