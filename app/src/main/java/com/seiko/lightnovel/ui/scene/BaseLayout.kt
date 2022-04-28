package com.seiko.lightnovel.ui.scene

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.seiko.lightnovel.component.view.LifecycleLayout
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

abstract class BaseLayout(context: Context) : LifecycleLayout(context), KoinScopeComponent {

    override val scope: Scope by lazy { createScope(this) }

    protected fun findNavController(): NavController {
        var parent = this.parent
        while (parent != null) {
            if (parent is NavHost) return parent.navController
            parent = parent.parent
        }
        throw RuntimeException("no find navController in $this")
    }

}