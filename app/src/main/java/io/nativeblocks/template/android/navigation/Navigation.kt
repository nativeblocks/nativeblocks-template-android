package io.nativeblocks.template.android.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class NavigationRoute(
    val route: String,
    val routeArguments: HashMap<String, String>
)

object Navigation {

    private val _currentRoute = MutableStateFlow("/")
    val currentRoute: StateFlow<String> get() = _currentRoute

    private val _currentRouteArguments = MutableStateFlow<HashMap<String, String>>(hashMapOf())
    val currentRouteArguments: StateFlow<HashMap<String, String>> get() = _currentRouteArguments

    private val _routeStack = MutableStateFlow<List<NavigationRoute>>(listOf())
    val routeStack: StateFlow<List<NavigationRoute>> get() = _routeStack

    fun push(destinationRoute: String, routeArguments: HashMap<String, String> = hashMapOf()) {
        _currentRoute.value = destinationRoute
        _currentRouteArguments.value = routeArguments

        val updatedStack = _routeStack.value.toMutableList()
        updatedStack.add(NavigationRoute(destinationRoute, routeArguments))
        _routeStack.value = updatedStack
    }

    fun pop() {
        val updatedStack = _routeStack.value.toMutableList()
        if (updatedStack.size > 1) {
            updatedStack.removeAt(updatedStack.lastIndex)
            _routeStack.value = updatedStack
            _currentRoute.value = updatedStack.lastOrNull()?.route ?: "/"
            _currentRouteArguments.value =
                updatedStack.lastOrNull()?.routeArguments ?: hashMapOf()
        }
    }
}

