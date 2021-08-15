package com.futag.futag.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.futag.futag.R
import com.squareup.picasso.Picasso

fun ImageView.fetchImagesWithUrl(url: String?, progressDrawable: CircularProgressDrawable){
    Picasso.get()
        .load(url)
        .placeholder(progressDrawable)
        .error(R.drawable.error)
        .into(this)
}

fun placeholderProgressBar(context: Context): CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}