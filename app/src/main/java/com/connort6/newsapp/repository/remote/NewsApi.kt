package com.connort6.newsapp.repository.remote


import com.connort6.newsapp.data.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.connort6.newsapp.other.Constants.Companion.API_KEY

interface NewsApi {

    @GET("everything")
    suspend fun getNews(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("q") keyword: String = "keyword"
    ): Response<News>

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("country") countryCode: String? = null,
        @Query("category") category: String? = null
    ): Response<News>

    // This method is used for both searching and top news section
    // This endpoint does not support filter by country and category
    @GET("v2/everything")
    suspend fun searchAllNews(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("q") keyword: String = "keyword",
        @Query("language") language: String? = "en"
    ): Response<News>
}