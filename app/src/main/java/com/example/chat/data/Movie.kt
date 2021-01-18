package com.example.chat.data

data class Movie(

    val actor: String, //배우

    val director: String, // 연기자

    val image: String, // 포스터

    val link: String, // 웹링크

    val pubDate: String, // 개봉날

    val title: String, // 제목

    val userRating: String, // 평점(별점)

    val items: List<Movie>, // 전체를 한 묶음으로

)