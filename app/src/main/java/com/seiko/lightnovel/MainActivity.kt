package com.seiko.lightnovel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.navigation.createGraph
import com.seiko.lightnovel.component.navigation.NavHostLayout
import com.seiko.lightnovel.route.Route
import com.seiko.lightnovel.route.route

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
}
