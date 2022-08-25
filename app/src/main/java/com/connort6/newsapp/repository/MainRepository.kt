package com.connort6.newsapp.repository

import com.connort6.newsapp.repository.remote.NewsApi
import kotlinx.coroutines.DelicateCoroutinesApi

@OptIn(DelicateCoroutinesApi::class)
class MainRepository(
    private val newsApi: NewsApi
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int, category: String? = null) =
        newsApi.getBreakingNews(countryCode = countryCode, pageNo = pageNumber, category = category)

    suspend fun searchNews(keyword: String, language: String, pageNumber: Int) =
        newsApi.searchAllNews(keyword = keyword, language, pageNumber)

    // cannot apply with the given given endpoint due to limitations
    //suspend fun getTopNews(language: String) = newsApi.searchAllNews(language = language)
}