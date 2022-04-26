package com.seiko.lightnovel.component.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

abstract class LifecycleLayout(
    context: Context,
    attrs: AttributeSet? = null
) : CustomLayout(context, attrs), LifecycleOwner, ViewModelStoreOwner {

    private val lifecycle by lazy(LazyThreadSafetyMode.NONE) {
        LifecycleRegistry(this)
    }

    private val viewModelStoreInner by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelStore()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        when (visibility) {
            View.VISIBLE -> {
                lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
            }
            View.GONE, View.INVISIBLE -> {
                lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        viewModelStoreInner.clear()
    }

    override fun getViewModelStore(): ViewModelStore = viewModelStoreInner

    override fun getLifecycle(): Lifecycle = lifecycle
}
