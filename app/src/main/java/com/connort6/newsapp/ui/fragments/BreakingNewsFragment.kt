package com.connort6.newsapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.connort6.newsapp.R
import com.connort6.newsapp.adapters.NewsRecViewAdapter
import com.connort6.newsapp.databinding.FragmentBreakingNewsBinding
import com.connort6.newsapp.other.ResponseWrapper
import com.connort6.newsapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var newsRecViewAdapter: NewsRecViewAdapter
    private lateinit var binding: FragmentBreakingNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvBreakingNews.layoutManager = LinearLayoutManager(requireActivity())

        mainViewModel.breakingNewsML.observe(viewLifecycleOwner, Observer { responseWrapper ->
            if (responseWrapper.status == ResponseWrapper.LOADING){

            }else if(responseWrapper.status == ResponseWrapper.ERROR){

            }else if (responseWrapper.status == ResponseWrapper.SUCCESS){
                newsRecViewAdapter = NewsRecViewAdapter(responseWrapper.news?.articles ?: ArrayList())
                binding.rvBreakingNews.adapter = newsRecViewAdapter
                newsRecViewAdapter.notifyDataSetChanged()
            }
        })
    }
}