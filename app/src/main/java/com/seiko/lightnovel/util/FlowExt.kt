package com.seiko.lightnovel.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

context(LifecycleOwner)
fun <T> Flow<T>.observer(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    action: suspend (T) -> Unit
): Job {
    return lifecycleScope.launch {
        repeatOnLifecycle(minActiveState) {
            collectLatest {
                action(it)
            }
        }
    }
}
