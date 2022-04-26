package com.seiko.lightnovel.component.navigation

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

@Navigator.Name("view")
class ViewNavigator(private val container: ViewGroup) : Navigator<ViewNavigator.Destination>() {

    private val backStacks = ArrayDeque<Int>()

    private var currentLayoutId = 0

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ): NavDestination {
        val targetView = destination.viewBuilder(args ?: Bundle.EMPTY)
        container.addView(targetView)

        val prevTag = backStacks.lastOrNull()
        if (prevTag != null) {
            container.findViewWithTag<View?>(prevTag)?.isVisible = false
        }

        val tag = currentLayoutId++
        targetView.tag = tag
        backStacks.addLast(tag)
        return destination
    }

    override fun popBackStack(): Boolean {
        if (backStacks.isEmpty()) return false

        val tag = backStacks.removeLast()
        container.findViewWithTag<View>(tag)?.let { container.removeView(it) }

        val prevTag = backStacks.lastOrNull()
        if (prevTag != null) {
            container.findViewWithTag<View?>(prevTag)?.isVisible = true
        }
        return true
    }

    override fun createDestination(): Destination {
        return Destination(this) {
            throw IllegalStateException("ViewNavigator can't create destination")
        }
    }

    class Destination(
        navigator: Navigator<out NavDestination>,
        internal val viewBuilder: (Bundle) -> View,
    ) : NavDestination(navigator)
}
