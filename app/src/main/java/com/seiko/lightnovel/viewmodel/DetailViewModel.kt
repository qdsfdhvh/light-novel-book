package com.seiko.lightnovel.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

class DetailViewModel(
    private val aid: Int,
) : ViewModel() {

    fun init() {
        Log.d("DetailViewModel", "init $aid")
    }
}
