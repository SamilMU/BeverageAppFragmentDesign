package com.example.eticaret.entities

class Member(
    var tel_number : String,
    var password : String,
    var member_cart : List<MenuItem>
) {
}