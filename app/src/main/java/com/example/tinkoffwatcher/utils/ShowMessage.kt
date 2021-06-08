package com.example.tinkoffwatcher.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showMessage(root: View, message: String) {
    Snackbar.make(root, message, Snackbar.LENGTH_LONG).show()
}