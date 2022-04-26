package com.seiko.lightnovel.component.koin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.ViewModelOwnerDefinition
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.component.KoinScopeComponent
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

inline fun <reified T : ViewModel> KoinScopeComponent.viewModel(
    qualifier: Qualifier? = null,
    noinline owner: ViewModelOwnerDefinition = {
        ViewModelOwner.from(this as ViewModelStoreOwner, this as? SavedStateRegistryOwner)
    },
    noinline parameters: ParametersDefinition? = null
): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        getViewModel(qualifier, owner, parameters)
    }
}

inline fun <reified T : ViewModel> KoinScopeComponent.getViewModel(
    qualifier: Qualifier? = null,
    noinline owner: ViewModelOwnerDefinition = {
        ViewModelOwner.from(this as ViewModelStoreOwner, this as? SavedStateRegistryOwner)
    },
    noinline parameters: ParametersDefinition? = null,
): T {
    return getViewModel(qualifier, T::class, owner, parameters = parameters)
}

fun <T : ViewModel> KoinScopeComponent.getViewModel(
    qualifier: Qualifier? = null,
    clazz: KClass<T>,
    owner: ViewModelOwnerDefinition = {
        ViewModelOwner.from(this as ViewModelStoreOwner, this as? SavedStateRegistryOwner)
    },
    parameters: ParametersDefinition? = null,
): T {
    return scope.getViewModel(qualifier, owner, clazz, parameters = parameters)
}
