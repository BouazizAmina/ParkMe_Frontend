package com.example.packme

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule


fun ImageView.load(imgUrl:String) {
    Glide.with(context).load(imgUrl).into(this)
}