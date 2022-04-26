package com.seiko.lightnovel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.seiko.lightnovel.component.navigation.NavHostLayout
import com.seiko.lightnovel.component.navigation.composable
import com.seiko.lightnovel.ui.DetailLayout
import com.seiko.lightnovel.ui.HomeLayout

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostLayout = NavHostLayout(this)
        setContentView(navHostLayout)

        val navController = navHostLayout.navController
        navController.setLifecycleOwner(this)
        navController.setOnBackPressedDispatcher(onBackPressedDispatcher)

        val graph = navController.createGraph(
            startDestination = "home",
        ) {
            composable("home") {
                HomeLayout(this@MainActivity)
            }
            composable(
                route = "detail/{key}",
                arguments = listOf(
                    navArgument("key") { type = NavType.StringType },
                ),
            ) {
                DetailLayout(this@MainActivity, it.getString("key")!!)
            }
        }
        navController.setGraph(graph, startDestinationArgs = null)
    }
}
