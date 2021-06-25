package com.example.tinkoffwatcher.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.tinkoffwatcher.data.Position
import com.example.tinkoffwatcher.extensions.setPositionLogo

@BindingAdapter(value = ["setTinkoffPosition"])
fun ImageView.setTinkoffPosition(position: Position) {
    setPositionLogo(position)
}