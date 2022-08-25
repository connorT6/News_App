package com.connort6.newsapp.other

import com.connort6.newsapp.data.News

class ResponseWrapper(
    val status: Int = LOADING,
    val message: String? = null,
    val news: News? = null
) {
    companion object {
        const val LOADING = 1
        const val SUCCESS = 2
        const val ERROR = 3
    }

}