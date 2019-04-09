package com.arctouch.codechallenge.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.BaseActivity
import com.arctouch.codechallenge.databinding.HomeActivityBinding
import com.arctouch.codechallenge.detail.DetailActivity
import com.arctouch.codechallenge.di.injector
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : BaseActivity() {

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
        viewModel.movies.observe(this, Observer {
            it?.let { moviesWithGenres ->
                recyclerView.adapter = HomeAdapter(moviesWithGenres, ::goToDetails)
                progressBar.visibility = View.GONE
            }
        })
    }

    private fun goToDetails(id: Int) {
        Toast.makeText(this, "id = $id", Toast.LENGTH_LONG).show()
        val intent = Intent(this, DetailActivity::class.java).apply {
            this.putExtra(movieId, id)
        }
        startActivity(intent)
    }

    companion object {
        val movieId = "MOVIE_ID"
    }
}
