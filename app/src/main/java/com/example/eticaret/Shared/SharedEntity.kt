package com.example.eticaret.Shared

import com.example.eticaret.categories.Categories
import com.example.eticaret.entities.Member
import com.example.eticaret.entities.MenuItem

object SharedEntity {

    var loggedIn : Boolean = false
    var mainUser : Member = Member("01111111111","#e&it1m", arrayListOf())
    var currentItem : MenuItem = MenuItem("","",0.0,Categories.SU,"","")
}