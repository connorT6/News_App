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

    fun call() {
        GlobalScope.launch(Dispatchers.IO){
            val response = newsApi.getNews()
            Log.d("mvv", response.isSuccessful.toString())
            if (response.isSuccessful){
                val news = response.body()
                if (news != null) {
                    Log.d("mvv", news.status)
                }
            }
        }
    }
}