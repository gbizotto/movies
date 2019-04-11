package com.arctouch.codechallenge.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.databinding.HomeActivityBinding
import com.arctouch.codechallenge.detail.DetailActivity
import com.arctouch.codechallenge.di.injector
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this, injector.injectHomeViewModel()).get(HomeViewModel::class.java)
    }

    private lateinit var binding: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.home_activity)
        binding.viewModel = viewModel
        observeViewModel(viewModel)
    }

    private fun observeViewModel(viewModel: HomeViewModel) {
        viewModel.movieList.observe(this, Observer {
            Log.v("lalal", "entrou em movieList" )
        })
        viewModel.movieList.observe(this, Observer {
            it?.let { moviesWithGenres ->
                val adap = HomeAdapter(moviesWithGenres, viewModel.movieImageUrlBuilder, ::goToDetails)
                adap.submitList(moviesWithGenres)
//                recyclerView.adapter = HomeAdapter(moviesWithGenres, viewModel.movieImageUrlBuilder, ::goToDetails)
                progressBar.visibility = View.GONE
            }
        })
    }

    private fun goToDetails(id: Int) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            this.putExtra(movieId, id)
        }
        startActivity(intent)
    }

    companion object {
        const val movieId = "MOVIE_ID"
    }
}
