package com.seiko.lightnovel.route

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.seiko.lightnovel.component.navigation.composable
import com.seiko.lightnovel.ui.scene.DetailLayout
import com.seiko.lightnovel.ui.scene.HomeLayout

fun NavGraphBuilder.route(context: Context) {
    composable(Route.Home) {
        HomeLayout(context)
    }
    composable(
        route = Route.Detail,
        arguments = listOf(
            navArgument("aid") { type = NavType.IntType },
        ),
    ) {
        DetailLayout(context, it.getInt("aid"))
    }
}

object Route {
    const val Home = "home"
    const val Detail = "detail/{aid}"

    const val initialRoute = Home
}
