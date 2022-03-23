package com.example.eticaret.entities

import com.example.eticaret.categories.Categories

class MenuItem(
    var item_name : String,
    var item_brand : String,
    var item_price : Double,
    var item_category : Categories,
    var item_desription : String,
    var item_pic_name: String
) {
}