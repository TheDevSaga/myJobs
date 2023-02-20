package com.example.myjobs.utils

import android.util.Patterns

fun String.isValidEmail():Boolean{
    val p = Patterns.EMAIL_ADDRESS
    return  this.contains(p.toRegex())
}