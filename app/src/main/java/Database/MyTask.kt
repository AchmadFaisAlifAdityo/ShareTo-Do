package Database

data class MyTask(
        //val author: String,
        val author: String,
        val id: String?,
        val judul: String,
        val waktu: String
     /*   val listItem: ArrayList<String>*/

){

    constructor():this("","","",""/*, ArrayList()*/){
    }
}