package com.seiko.lightnovel.route

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.seiko.lightnovel.component.navigation.composable
import com.seiko.lightnovel.ui.scene.DetailLayout
import com.seiko.lightnovel.ui.scene.HomeLayout
import com.seiko.lightnovel.ui.scene.ReaderWenku8Layout

fun NavGraphBuilder.route(context: Context) {
    composable(Route.Home) {
        HomeLayout(context)
    }
    composable(
        route = Route.Detail.path,
        arguments = listOf(
            navArgument("aid") { type = NavType.IntType },
        ),
    ) {
        DetailLayout(context, it.getInt("aid"))
    }
    composable(
        route = Route.Reader,
        arguments = listOf(
            navArgument("vid") { type = NavType.IntType },
        ),
    ) {
        ReaderWenku8Layout(context, it.getInt("aid"), it.getInt("vid"))
    }
}

object Route {
    const val Home = "home"

    object Detail {
        const val path = "detail/{aid}"
        operator fun invoke(aid: Int): String = "detail/$aid"
    }

    object Reader {

        object Wenku8 {
            const val path = "reader/wenku8/{aid}/{vid}"
            operator fun invoke(aid: Int, vid: Int): String = "reader/wenku8/$aid/$vid"
        }
    }

    const val initialRoute = Home
}
