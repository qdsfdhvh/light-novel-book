package com.seiko.lightnovel.component.loading

import android.app.Activity
import android.view.ViewGroup
import android.widget.FrameLayout

class GlobalLoader(
    activity: Activity,
    private val adapter: Adapter,
) {
    private val wrapper: FrameLayout by lazy(LazyThreadSafetyMode.NONE) {
        activity.findViewById(android.R.id.content)
    }

    fun showState(state: LoadingState) {
        adapter.showState(wrapper, state)
    }

    interface Adapter {
        fun showState(convertView: ViewGroup, state: LoadingState)
    }
}
