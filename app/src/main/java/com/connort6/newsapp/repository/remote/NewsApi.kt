package com.connort6.newsapp.repository.remote


import com.connort6.newsapp.data.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getNews(
        @Query("apiKey") apiKey: String = "1957e2e6764c4db88ac5c9dd5f2e1a4e",
        @Query("q") keyword: String = "keyword"
    ): Response<News>
}