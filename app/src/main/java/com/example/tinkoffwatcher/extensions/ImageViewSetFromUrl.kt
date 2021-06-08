package com.example.tinkoffwatcher.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter(value = ["setStockLogoFromIsin"])
fun ImageView.setImageUrl(isin: String) {
    if (isin.isNotBlank()) {
        val path = "http://static.tinkoff.ru/brands/traiding/" + isin + "x160.png"
        Picasso.get()
            .load(path).into(this)
    }
}