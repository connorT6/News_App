package com.connort6.newsapp.repository

import android.util.Log
import com.connort6.newsapp.repository.remote.NewsApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class MainRepository(
    private val newsApi: NewsApi
) {

    suspend fun getBreakingNews(countryCode: String) =
        newsApi.getBreakingNews(countryCode = countryCode)

    suspend fun searchNews(keyword: String) =
        newsApi.searchAllNews(keyword = keyword)

    // cannot apply with the given given endpoint due to limitations
    //suspend fun getTopNews(language: String) = newsApi.searchAllNews(language = language)
}