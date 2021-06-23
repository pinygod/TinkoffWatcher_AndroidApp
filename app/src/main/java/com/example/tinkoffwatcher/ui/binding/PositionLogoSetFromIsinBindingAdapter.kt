package com.example.tinkoffwatcher.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.tinkoffwatcher.extensions.loadLogoFromIsin
import com.squareup.picasso.Picasso

@BindingAdapter(value = ["setIsin"])
fun ImageView.setIsin(isin: String) {
    loadLogoFromIsin(isin)
}