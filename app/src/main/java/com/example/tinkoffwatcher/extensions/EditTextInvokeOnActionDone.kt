package com.example.tinkoffwatcher.extensions

import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.invokeOnActionDone(callback: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            callback.invoke()
        }
        false
    }
}