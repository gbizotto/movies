package com.arctouch.codechallenge.util.bindingadapter

import android.databinding.BindingAdapter
import android.util.Log
import android.widget.ImageView
import com.arctouch.codechallenge.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String?) {

    Log.v("BindingAdapter", "url = $url")
    url?.let {
        Glide
                .with(imageView)
                .load(it)
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(imageView)
    }
}