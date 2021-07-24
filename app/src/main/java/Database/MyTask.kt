package Database

import com.example.sharetodo.entity.ItemLIst

data class MyTask(
        val author: String,
        val id: String?,
        val judul: String,
        val waktu: String,
        val listItem: ArrayList<ItemLIst>

){

    constructor():this("","","","", ArrayList()){
    }


}