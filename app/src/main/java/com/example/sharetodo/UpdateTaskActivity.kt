package com.example.sharetodo

import Database.MyTask
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_editask.*
import java.text.SimpleDateFormat
import java.util.*

class UpdateTaskActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var judul: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editask)

   /*    judul = findViewById<EditText>(R.id.edt_update_judul).text.toString()*/

        getSetDataAndUpdate()

        bt_Submit.setOnClickListener{
         /*   update_judul = update_judul.trim()*/
            val ref = FirebaseDatabase.getInstance().getReference("MyTask")
            val myTaskId = ref.push().key
            val currentUser = auth.currentUser
            val username = currentUser?.displayName.toString()
            val sdf = SimpleDateFormat("HH:mm a")
            val cal = Calendar.getInstance()
            val waktu = sdf.format(cal.time)
          /*  val myTask = MyTask(username, myTaskId, update_judul, waktu)*/
        }

    }

    private fun getSetDataAndUpdate() {
        if (intent.hasExtra("id") && intent.hasExtra("nama_pasien")) {
            // Mendapatkan nilai value pada put extra

            // Mendapatkan nilai value pada put extra
            val id = intent.getStringExtra("id")
            val judul = intent.getStringExtra("judul")

            // Mengubah text nama dan gender

            // Mengubah text nama dan gender
            /*       updata.setText(judul)*/

        }
    }
}