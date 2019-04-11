package com.arctouch.codechallenge.home

import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.databinding.MovieItemBinding
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder

class HomeAdapter(
        private val movies: List<Movie>,
        private val movieImageUrlBuilder: MovieImageUrlBuilder,
        private val clickEvent: (id: Int) -> Unit
): PagedListAdapter<Movie, HomeAdapter.ViewHolder>(movieDiffCallback) {
//) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View, private val movieImageUrlBuilder: MovieImageUrlBuilder, private val binding: MovieItemBinding) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie, clickEvent: (id: Int) -> Unit) {
            binding.viewModel = HomeItemViewModel(movie, movieImageUrlBuilder, clickEvent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<MovieItemBinding>(LayoutInflater.from(parent.context), R.layout.movie_item, parent, false)
        return ViewHolder(binding.root, movieImageUrlBuilder, binding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position], clickEvent)


    companion object {
        val movieDiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

}
