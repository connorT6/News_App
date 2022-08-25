package com.connort6.newsapp.ui.viewmodels

import android.os.Handler
import android.os.Looper
import android.util.Log
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
    val searchNewsML: MutableLiveData<ResponseWrapper> = MutableLiveData()
    val topNewsML:MutableLiveData<ResponseWrapper> = MutableLiveData()

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) {
        viewModelScope.launch {
            breakingNewsML.postValue(ResponseWrapper())
            val res = mainRepository.getBreakingNews(countryCode)
            val data = processResponse(res)
            breakingNewsML.postValue(data)
        }
    }

    fun searchNews(keyword: String){
        viewModelScope.launch {
            searchNewsML.postValue(ResponseWrapper())
            val res = mainRepository.searchNews(keyword)
            val data = processResponse(res)
            searchNewsML.postValue(data)
        }
    }

    private fun processResponse(response: Response<News>): ResponseWrapper {
        if (response.isSuccessful){
            return ResponseWrapper(status = ResponseWrapper.SUCCESS, news = response.body())
        }
        return ResponseWrapper(status = ResponseWrapper.ERROR, message = response.message())
    }

}