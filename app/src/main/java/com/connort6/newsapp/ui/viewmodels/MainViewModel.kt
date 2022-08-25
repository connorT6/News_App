package com.connort6.newsapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connort6.newsapp.data.News
import com.connort6.newsapp.other.ResponseWrapper
import com.connort6.newsapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    var breakingNewsML: MutableLiveData<ResponseWrapper> = MutableLiveData()
    private var breakingNewsPage = 1
    private var breakingNews: ResponseWrapper? = null

    val searchNewsML: MutableLiveData<ResponseWrapper> = MutableLiveData()
    val topNewsML: MutableLiveData<ResponseWrapper> = MutableLiveData()
    private var topNews: ResponseWrapper? = null

    init {
        getBreakingNews()
    }

    fun getBreakingNews(countryCode: String = "us") {
        viewModelScope.launch {
            breakingNewsML.postValue(ResponseWrapper())
            val res = mainRepository.getBreakingNews(countryCode, breakingNewsPage)
            val data = processResponse(res, breakingNews)
            breakingNews = data
            breakingNewsML.postValue(data)
        }
    }

    fun searchNews(keyword: String) {
        viewModelScope.launch {
            searchNewsML.postValue(ResponseWrapper())
            val res = mainRepository.searchNews(keyword)
            val data = processResponse(res, topNews)
            topNews = data
            searchNewsML.postValue(data)
        }
    }

    private fun processResponse(
        response: Response<News>,
        oldResponse: ResponseWrapper?
    ): ResponseWrapper {
        if (response.isSuccessful) {
            breakingNewsPage++
            if (oldResponse == null) {
                return ResponseWrapper(status = ResponseWrapper.SUCCESS, news = response.body())
            }

            val oldArticleList = oldResponse.news?.articles
            response.body()?.articles?.let { oldArticleList?.addAll(it) }
            val news = response.body()
            if (oldArticleList != null && news != null) {
                news.articles = oldArticleList
            }
            return ResponseWrapper(status = ResponseWrapper.SUCCESS, news = news)
        }
        return ResponseWrapper(status = ResponseWrapper.ERROR, message = response.message())
    }

}