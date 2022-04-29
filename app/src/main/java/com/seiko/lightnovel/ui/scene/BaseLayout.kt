package com.seiko.lightnovel.ui.scene

import android.content.Context
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.seiko.lightnovel.component.view.LifecycleLayout
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.LifecycleScopeDelegate
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.getScopeId
import org.koin.core.component.getScopeName
import org.koin.core.scope.Scope

abstract class BaseLayout(context: Context) : LifecycleLayout(context), KoinScopeComponent {

    override val scope: Scope by layoutScope()

    protected val navController by lazy(LazyThreadSafetyMode.NONE) {
        findNavController(this)
    }
}

private fun findNavController(view: View): NavController {
    var parent = view.parent
    while (parent != null) {
        if (parent is NavHost) return parent.navController
        parent = parent.parent
    }
    throw RuntimeException("no find navController in $view")
}

private fun BaseLayout.layoutScope() = LifecycleScopeDelegate<BaseLayout>(
    this, this.getKoin()
) { koin ->
    koin.createScope(
        this.getScopeId(),
        this.getScopeName(),
        this,
    ).apply {
        (context as? AndroidScopeComponent)?.scope?.let { activityScope ->
            linkTo(activityScope)
        }
    }
}
