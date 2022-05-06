package com.seiko.lightnovel.component.insets

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

typealias InitialPadding = Rect
typealias InitialMargin = Rect

fun View.applyBottomWindowInsetForScrollingView(scrollingView: ViewGroup) {
    scrollingView.clipToPadding = false
    val scrollingViewPadding = scrollingView.recordInitialPaddingForView()
    doOnApplyWindowInsets { windowInsets, _, _ ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        scrollingView.updatePadding(bottom = scrollingViewPadding.bottom + insets.bottom)
    }
}

fun View.doOnApplyWindowInsets(
    block: (windowInsets: WindowInsetsCompat, padding: InitialPadding, margin: InitialMargin) -> Unit
) {
    val initialPadding = recordInitialPaddingForView()
    val initialMargin = recordInitialMarginForView()
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, windowInsets ->
        block(windowInsets, initialPadding, initialMargin)
        windowInsets
    }
    requestApplyInsetsWhenAttached()
}

fun View.recordInitialPaddingForView() =
    InitialPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)

fun View.recordInitialMarginForView() =
    (layoutParams as? ViewGroup.MarginLayoutParams)?.let {
        InitialMargin(it.leftMargin, it.topMargin, it.rightMargin, it.bottomMargin)
    } ?: InitialMargin(0, 0, 0, 0)

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}
