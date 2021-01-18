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

fun Calendar.toHHmma(): String{ // 이게 그러니까 채팅 칠때 시간을 함수형으로 써두어서 나타낸거잖아 따로 써놔서
    val dateFormat = SimpleDateFormat("hh:mm a")// 프로퍼티 상수 프로퍼티 명 그다음 앞에껄 프로프티명에 담아주고 그걸 다시 반환 시켜주는데 포멧형식을 time으로 해줬네 ㅇㅎ
    return dateFormat.format(this.time) // 여기가 반환하는 부분
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