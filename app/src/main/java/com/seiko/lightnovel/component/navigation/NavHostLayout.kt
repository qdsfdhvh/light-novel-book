package com.seiko.lightnovel.component.navigation

import android.content.Context
import android.view.WindowInsets
import androidx.core.view.children
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.seiko.lightnovel.component.view.CustomLayout
import com.seiko.lightnovel.component.view.autoMeasure

class NavHostLayout(context: Context) : CustomLayout(context), NavHost {

    override val navController = NavController(context).apply {
        navigatorProvider.addNavigator(ViewNavigator(this@NavHostLayout))
    }

    init {
        fitsSystemWindows = false
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        children.forEach {
            it.autoMeasure()
        }
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        children.forEach {
            it.layout(0, 0)
        }
    }

    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        return insets
    }
}
