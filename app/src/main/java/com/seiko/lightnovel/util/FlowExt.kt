package com.seiko.lightnovel.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

context(LifecycleOwner)
fun <T> Flow<T>.observer(action: suspend (T) -> Unit): Job {
    return flowWithLifecycle(lifecycle).onEach(action).launchIn(lifecycleScope)
}
