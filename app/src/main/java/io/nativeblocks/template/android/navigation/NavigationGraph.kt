package io.nativeblocks.template.android.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.nativeblocks.core.api.NativeblocksError
import io.nativeblocks.core.api.NativeblocksFrame
import io.nativeblocks.core.api.NativeblocksLoading
import io.nativeblocks.core.frame.domain.model.NativeFrameRouteModel

private const val STARTER_ROUTE = "/welcome"

@Composable
fun NavigationGraph(routes: List<NativeFrameRouteModel>) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = STARTER_ROUTE,
        modifier = Modifier,
    ) {
        composable(STARTER_ROUTE) {
            FrameScreen()
        }
        routes.filter { it.route != STARTER_ROUTE }.forEach {
            composable(it.route.orEmpty()) {
                FrameScreen()
            }
        }
    }
}

@Composable
private fun FrameScreen() {
    val currentRoute by Navigation.currentRoute.collectAsState()
    val currentRouteArguments by Navigation.currentRouteArguments.collectAsState()
    NativeblocksFrame(
        route = currentRoute,
        routeArguments = currentRouteArguments,
        loading = {
            NativeblocksLoading()
        },
        error = { message ->
            NativeblocksError(message)
            Log.e("NativeblocksFrame", message)
        },
    )
}
