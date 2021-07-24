package com.example.sharetodo.entity


class ItemLIst {

    var itemlist: String = ""

    constructor()

    fun setItem(itemList: String) {
        this.itemlist = itemList
    }

    fun getItem(): String {
        return itemlist
    }

}