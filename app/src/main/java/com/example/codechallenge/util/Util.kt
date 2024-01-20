package com.example.codechallenge.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("retry") {
            it()
        }
    }
    snackbar.show()
}


fun View.visibile(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}