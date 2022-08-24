package com.connort6.newsapp.ui.viewmodels

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import com.connort6.newsapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

   fun testConnectivity(){
       Log.d("mvv", "working")
       mainRepository.call()
   }

}