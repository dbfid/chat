package com.example.chat.data

data class profilemale(
    val mprofile: String, // 프로필 사진

    val mnickname: String, // 닉네임

    val mprofilemsg: String, // 메시지

    val mprofileyear: String, // 보낸 년도 시간

    val maleitem: List<profilefemale>,
)
