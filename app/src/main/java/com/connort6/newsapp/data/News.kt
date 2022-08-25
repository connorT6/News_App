package com.connort6.newsapp.data

import com.connort6.newsapp.data.Article


// class for contain the json data received from the api
data class News(
    var articles: ArrayList<Article>,
    val status: String,
    val totalResults: Int,
    val message: String?
)