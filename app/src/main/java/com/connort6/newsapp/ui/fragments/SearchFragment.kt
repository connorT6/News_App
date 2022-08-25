package com.connort6.newsapp.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.connort6.newsapp.R
import com.connort6.newsapp.adapters.NewsRecViewAdapter
import com.connort6.newsapp.databinding.BottomSheetFilterSearchBinding
import com.connort6.newsapp.databinding.FragmentSearchBinding
import com.connort6.newsapp.other.Constants
import com.connort6.newsapp.other.ResponseWrapper
import com.connort6.newsapp.ui.viewmodels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val TAG = "SearchFragment"
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private val newsRecViewAdapter: NewsRecViewAdapter = NewsRecViewAdapter()
    private var currentLanguage: String = "en"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycleView = binding.rvSearchNews
        val layoutManager = LinearLayoutManager(requireActivity())
        recycleView.layoutManager = layoutManager
        recycleView.adapter = newsRecViewAdapter
        val progressbar = binding.progressbar
        val textInput = binding.teKeyword
        val searchBt = binding.imbSearch

        searchBt.setOnClickListener {
            val keyword = textInput.text.toString()
            if (keyword != "") {
                mainViewModel.searchNews(keyword, language = currentLanguage)
            }
        }

        binding.cvFilter.setOnClickListener {
            val dialog = BottomSheetDialog(requireActivity())
            val bottomSheetBinding = BottomSheetFilterSearchBinding.inflate(layoutInflater)
            var languageList: ArrayList<String> = ArrayList()
            for ((key, value) in Constants.languageMap) {
                languageList.add(key)
            }
            languageList.sortBy { it }

            val currentLangName =
                Constants.languageMap.filterValues { it == currentLanguage }.keys.first()

            val tvLanguage = bottomSheetBinding.tvAutoLanguage
            tvLanguage.text = Editable.Factory.getInstance().newEditable(currentLangName)
            val languageAdapter = ArrayAdapter(
                requireActivity(),
                R.layout.item_filter_method,
                R.id.tv_method_filter,
                languageList
            )

            tvLanguage.setAdapter(languageAdapter)

            tvLanguage.setOnItemClickListener { _, _, position, _ ->
                val selection = languageList[position]
                currentLanguage = Constants.languageMap[selection]!!
                dialog.dismiss()
            }


            dialog.setContentView(bottomSheetBinding.root)
            dialog.show()
        }

        newsRecViewAdapter.onItemClick = {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchFragment_to_articleFragment,
                bundle
            )
        }
        val cvViewMore = binding.cvViewMore

        cvViewMore.setOnClickListener {
            mainViewModel.getNextPageSearch()
        }

        mainViewModel.searchNewsML.observe(viewLifecycleOwner, Observer {
            if (it.status == ResponseWrapper.LOADING) {
                progressbar.visibility = View.VISIBLE
            } else if (it.status == ResponseWrapper.ERROR) {
                if (it.message != null) {
                    Log.e(TAG, it.message)
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                }
                progressbar.visibility = View.GONE
            } else if (it.status == ResponseWrapper.SUCCESS) {
                progressbar.visibility = View.GONE
                val list = it.news?.articles?.toList()
                newsRecViewAdapter.differ.submitList(list)
                if (it.news?.totalResults == it.news?.articles?.size) {
                    cvViewMore.visibility = View.GONE
                } else {
                    cvViewMore.visibility = View.VISIBLE
                }
            }
        })


    }
}