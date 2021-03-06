package com.seiko.lightnovel

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.navigation.createGraph
import com.seiko.lightnovel.component.navigation.NavHostLayout
import com.seiko.lightnovel.route.Route
import com.seiko.lightnovel.route.route
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.core.scope.Scope

class MainActivity : ComponentActivity(), AndroidScopeComponent {

    override val scope: Scope by activityScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWindowInsets()

        val navHostLayout = NavHostLayout(this)
        setContentView(navHostLayout)

        val navController = navHostLayout.navController
        navController.setLifecycleOwner(this)
        navController.setOnBackPressedDispatcher(onBackPressedDispatcher)

        val graph = navController.createGraph(startDestination = Route.initialRoute) {
            route(this@MainActivity)
        }
        navController.setGraph(graph, startDestinationArgs = null)
    }

    private fun setupWindowInsets() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        ViewCompat.getWindowInsetsController(window.decorView)?.let {
            it.isAppearanceLightStatusBars = true
            it.isAppearanceLightNavigationBars = true
        }
    }
}
