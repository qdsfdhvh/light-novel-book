package com.seiko.lightnovel.component.navigation

import android.os.Bundle
import android.view.View
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.get

fun NavGraphBuilder.composable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<String> = emptyList(),
    viewBuilder: (Bundle) -> View,
) {
    addDestination(
        ViewNavigator.Destination(
            provider[ViewNavigator::class],
            viewBuilder,
        ).apply {
            this.route = route
            arguments.forEach { (argumentName, argument) ->
                addArgument(argumentName, argument)
            }
            deepLinks.forEach { deepLink ->
                addDeepLink(deepLink)
            }
        }
    )
}
