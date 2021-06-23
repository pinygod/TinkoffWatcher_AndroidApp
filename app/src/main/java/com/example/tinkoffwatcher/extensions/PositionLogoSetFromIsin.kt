package com.example.tinkoffwatcher.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

fun ImageView.loadLogoFromIsin(isin: String) {
    if (isin.isNotBlank()) {
        val path = "http://static.tinkoff.ru/brands/traiding/" + isin + "x160.png"
        Picasso.get()
            .load(path).into(this)
    }
}