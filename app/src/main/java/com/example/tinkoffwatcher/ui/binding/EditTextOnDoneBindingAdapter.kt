package com.example.tinkoffwatcher.ui.binding

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.example.tinkoffwatcher.extensions.invokeOnActionDone

@BindingAdapter(value = ["onDone"])
fun EditText.onDone(callback: () -> Unit) {
    invokeOnActionDone(callback)
}