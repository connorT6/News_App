package com.connort6.newsapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.connort6.newsapp.R
import com.connort6.newsapp.databinding.ActivityMainBinding
import com.connort6.newsapp.other.Constants
import com.connort6.newsapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerMain.id) as NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.bottomNaviView.visibility = View.GONE

        mainViewModel.signedInML.observe(this, Observer {
            if (it) {
                binding.bottomNaviView.visibility = View.VISIBLE
                binding.bottomNaviView.setupWithNavController(navController)
                navController.navigate(R.id.action_loginFragment_to_breakingNewsFragment)
            }
        })

        Constants.initializeData()

    }
}