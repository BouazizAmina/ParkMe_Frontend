package com.example.packme

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(imgUrl:String) {
    Glide.with(context).load(imgUrl).into(this)
}