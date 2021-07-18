package Database

import com.google.firebase.database.ValueEventListener

data class MyTask(
        val author: String,
        val id: String?,
        val judul: String,
        val waktu: String
){
    constructor():this("","","", ""){

    }
}