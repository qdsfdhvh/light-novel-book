package com.seiko.lightnovel.component.loading

import android.app.Activity
import android.view.ViewGroup

class GlobalLoader(
    wrapperBuilder: () -> ViewGroup,
    private val adapter: Adapter,
) {

    constructor(activity: Activity, adapter: Adapter) : this(
        wrapperBuilder = { activity.findViewById(android.R.id.content) },
        adapter = adapter,
    )

    private val wrapper by lazy(LazyThreadSafetyMode.NONE) {
        wrapperBuilder()
    }

    fun showState(state: LoadingState) {
        adapter.showState(wrapper, state)
    }

    interface Adapter {
        fun showState(convertView: ViewGroup, state: LoadingState)
    }
}
