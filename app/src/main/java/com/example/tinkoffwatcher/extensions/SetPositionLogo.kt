package com.example.tinkoffwatcher.extensions

import android.widget.ImageView
import com.example.tinkoffwatcher.data.Position
import com.squareup.picasso.Picasso

fun ImageView.setPositionLogo(position: Position) {
    val path = if (!position.isin.isNullOrBlank()) {
        "http://static.tinkoff.ru/brands/traiding/" + position.isin + "x160.png"
    } else {
        "http://static.tinkoff.ru/brands/traiding/" + position.ticker.substring(0, 3) + "x160.png" //first 3 symbols are real ticker (e.g. USD or EUR)
    }
    Picasso.get()
        .load(path).into(this)
}