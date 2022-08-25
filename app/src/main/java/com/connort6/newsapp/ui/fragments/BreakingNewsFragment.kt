package com.connort6.newsapp.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.connort6.newsapp.R
import com.connort6.newsapp.adapters.NewsRecViewAdapter
import com.connort6.newsapp.databinding.BottomSheetFilterBinding
import com.connort6.newsapp.databinding.FragmentBreakingNewsBinding
import com.connort6.newsapp.other.Constants.Companion.categoryList
import com.connort6.newsapp.other.Constants.Companion.countryMap
import com.connort6.newsapp.other.ResponseWrapper
import com.connort6.newsapp.ui.viewmodels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {
    private val TAG = "BreakingNewsFragment"
    private val mainViewModel: MainViewModel by viewModels()
    private val newsRecViewAdapter: NewsRecViewAdapter = NewsRecViewAdapter()
    private lateinit var binding: FragmentBreakingNewsBinding
    private var countryChanged: Boolean = false
    private var countryCode: String = "us"
    private var currentCategory: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycleView = binding.rvBreakingNews
        val layoutManager = LinearLayoutManager(requireActivity())
        recycleView.layoutManager = layoutManager
        recycleView.adapter = newsRecViewAdapter

        val progressbar = binding.progressbar

        var countryList: ArrayList<String> = ArrayList()
        for ((key, value) in countryMap) {
            countryList.add(key)
        }

        countryList.sortBy { it }

        binding.cvFilter.setOnClickListener {
            val dialog = BottomSheetDialog(requireActivity())
            val bottomSheetBinding = BottomSheetFilterBinding.inflate(layoutInflater)
            val currentCountryName = countryMap.filterValues { it == countryCode }.keys.first()
            val tvCountry = bottomSheetBinding.tvAutoCountry
            val tvCategory = bottomSheetBinding.tvAutoCategory
            tvCountry.text =
                Editable.Factory.getInstance().newEditable(currentCountryName)
            if (currentCategory != null) {
                tvCategory.text = Editable.Factory.getInstance().newEditable(currentCategory)
            }

            val countryAdapter = ArrayAdapter(
                requireActivity(),
                R.layout.item_filter_method,
                R.id.tv_method_filter,
                countryList
            )
            tvCountry.setAdapter(countryAdapter)
            tvCountry.setOnItemClickListener { _, _, position, _ ->
                val selection = countryList[position]
                countryCode = countryMap[selection]!!
                mainViewModel.getBreakingNews(countryCode = countryCode, currentCategory)
                countryChanged = true
                dialog.dismiss()
            }

            val categoryAdapter = ArrayAdapter(
                requireActivity(),
                R.layout.item_filter_method,
                R.id.tv_method_filter,
                categoryList
            )
            tvCategory.setAdapter(categoryAdapter)
            tvCategory.setOnItemClickListener { _, _, position, _ ->
                currentCategory = categoryList[position]
                mainViewModel.getBreakingNews(countryCode = countryCode, currentCategory)
                dialog.dismiss()
            }

            val cvReset = bottomSheetBinding.cvResetFilters

            cvReset.setOnClickListener {
                countryCode = "us"
                currentCategory = null
                mainViewModel.getBreakingNews(countryCode = countryCode, currentCategory)
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
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
        val cvViewMore = binding.cvViewMore

        cvViewMore.setOnClickListener {
            mainViewModel.getBreakingNews(countryCode, currentCategory)
        }


        mainViewModel.breakingNewsML.observe(viewLifecycleOwner, Observer { responseWrapper ->
            if (responseWrapper.status == ResponseWrapper.LOADING) {
                if (countryChanged) {
                    newsRecViewAdapter.differ.submitList(null)
                }
                countryChanged = false
                progressbar.visibility = View.VISIBLE
            } else if (responseWrapper.status == ResponseWrapper.ERROR) {
                if (responseWrapper.message != null) {
                    Log.e(TAG, responseWrapper.message)
                }
                progressbar.visibility = View.GONE
            } else if (responseWrapper.status == ResponseWrapper.SUCCESS) {
                progressbar.visibility = View.GONE
                val list = responseWrapper.news?.articles?.toList()
                newsRecViewAdapter.differ.submitList(list)
                if (responseWrapper.news?.totalResults == responseWrapper.news?.articles?.size) {
                    cvViewMore.visibility = View.GONE
                } else {
                    cvViewMore.visibility = View.VISIBLE
                }
            }
        })
    }
}