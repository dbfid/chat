package com.example.chat.Extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

fun Calendar.isToday(): Boolean{
    val calendar = Calendar.getInstance()
    return this.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) && this.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && this.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
}

fun Calendar.isYesterday(): Boolean{
    val calendar = Calendar.getInstance()
    return this.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) -1 && this.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && this.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
}

fun Calendar.toDateLong(): String{
    val dateFormat = SimpleDateFormat("dd MMMM, yyyy")
    return dateFormat.format(this.time)
}

fun Calendar.toHHmma(): String{
    val dateFormat = SimpleDateFormat("hh:mm a")
    return dateFormat.format(this.time)
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit){
    this.addTextChangedListener(object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?){
            afterTextChanged.invoke(editable.toString())
        }
    })
}