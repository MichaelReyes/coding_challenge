package com.app.tech.codingchallenge.core.utils

import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visible: Boolean?) {
    view.visibility = if (visible == true) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("visibilityInv")
fun setVisibilityInv(view: View, visible: Boolean?) {
    view.visibility = if (visible == true) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

@BindingAdapter("clickWithDebounce")
fun setDebounceClickListener(view: View, onClickListener: View.OnClickListener) {
    val debounceTime = 1000L
    var lastClickTime: Long = 0

    val clickWithDebounce: (view: View) -> Unit = {
        if (SystemClock.elapsedRealtime() - lastClickTime >= debounceTime) {
            onClickListener.onClick(
                it
            )
            lastClickTime = SystemClock.elapsedRealtime()
        }
    }

    view.setOnClickListener(clickWithDebounce)
}