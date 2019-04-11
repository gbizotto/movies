package com.arctouch.codechallenge.detail

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.databinding.ActivityDetailBinding
import com.arctouch.codechallenge.di.injector

class DetailActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this, injector.injectDetailViewModel()).get(DetailViewModel::class.java)
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()

        if (intent.hasExtra("MOVIE_ID")) {
            viewModel.init(intent.getIntExtra("MOVIE_ID", 0))
        }
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewModel = viewModel
    }
}
