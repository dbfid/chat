package com.example.chat.data

data class profilefemale (
    val fprofile: String, // 프로필 사진

    val fnickname: String, // 닉네임

    val fprofilemsg: String, // 메시지

    val fprofileyear: String, // 보낸 년도 시간

    val femaleitem: List<profilefemale>,
)
