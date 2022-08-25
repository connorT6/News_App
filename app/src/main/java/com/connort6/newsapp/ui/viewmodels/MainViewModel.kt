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
    private var prevBreakingNewsCountry = "us"
    private var breakingNews: ResponseWrapper? = null
    private var breakingNewsCat: String? = null

    val searchNewsML: MutableLiveData<ResponseWrapper> = MutableLiveData()
    private var searchedNews: ResponseWrapper? = null
    private var oldKeyword: String? = null
    private var searchNewsPage = 1
    private var previousLang: String = "en"

    val topNewsML: MutableLiveData<ResponseWrapper> = MutableLiveData()
    //private var topNews: ResponseWrapper? = null

    init {
        getBreakingNews()
        viewModelScope.launch {
            //Constants.addDetailsToMap()
        }
    }

    fun getBreakingNews(countryCode: String = "us", category: String? = null) {
        if (prevBreakingNewsCountry != countryCode) {
            breakingNewsPage = 1
            breakingNews = null
            prevBreakingNewsCountry = countryCode
        }
        if (breakingNewsCat != category) {
            breakingNewsPage = 1
            breakingNews = null
            breakingNewsCat = category
        }

        viewModelScope.launch {
            breakingNewsML.postValue(ResponseWrapper())
            try {
                val res = mainRepository.getBreakingNews(countryCode, breakingNewsPage, category)
                val data = processResponse(res, breakingNews)
                breakingNews = data
                breakingNewsML.postValue(data)
            } catch (t: Throwable) {

            }

        }
    }

    fun searchNews(keyword: String, language: String = "en") {
        if (keyword != oldKeyword) {
            searchedNews = null
            searchNewsPage = 1
            oldKeyword = keyword
        }
        if (previousLang != language){
            searchedNews = null
            searchNewsPage = 1
            previousLang = language
        }

        viewModelScope.launch {
            searchNewsML.postValue(ResponseWrapper())
            try {
                val res = mainRepository.searchNews(keyword, language, searchNewsPage)
                val data = processResponse(res, searchedNews)
                searchedNews = data
                searchNewsML.postValue(data)
            } catch (t: Throwable) {

            }

        }
    }

    fun getNextPageSearch() {
        searchNewsPage++
        oldKeyword?.let { searchNews(it, previousLang) }
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