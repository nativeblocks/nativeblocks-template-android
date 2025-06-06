package io.nativeblocks.template.android.navigation

import io.nativeblocks.compiler.type.NativeAction
import io.nativeblocks.compiler.type.NativeActionFunction
import io.nativeblocks.compiler.type.NativeActionParameter
import io.nativeblocks.compiler.type.NativeActionProp
import io.nativeblocks.compiler.type.NativeActionValuePicker
import io.nativeblocks.compiler.type.NativeActionValuePickerOption
import java.net.URLDecoder

@NativeAction(
    name = "Navigator",
    keyType = "NAVIGATOR",
    description = "Controls navigation across application routes",
    version = 1,
    versionName = "1.0.0"
)
class Navigator {

    @NativeActionParameter
    data class Parameter(
        @NativeActionProp(
            valuePicker = NativeActionValuePicker.DROPDOWN,
            valuePickerOptions = [
                NativeActionValuePickerOption("push", "Push to new route"),
                NativeActionValuePickerOption("pop", "Go back"),
                NativeActionValuePickerOption("popToRoot", "Go back to root"),
                NativeActionValuePickerOption("replace", "Replace current route")
            ]
        )
        var actionType: String = "push",
        @NativeActionProp
        var destinationRoute: String = "/",
    )

    @NativeActionFunction
    fun navigate(param: Parameter) {
        when (param.actionType) {
            "push" -> {
                navigateToPush(param.destinationRoute)
            }
            "pop" -> {
                Navigation.pop()
            }
            "popToRoot" -> {
                while (Navigation.routeStack.value.size > 1) {
                    Navigation.pop()
                }
            }
            "replace" -> {
                Navigation.pop()
                navigateToPush(param.destinationRoute)
            }
        }
    }

    private fun navigateToPush(destination: String) {
        val parsedNavigation = parseRouteAndParams(destination)
        Navigation.push(parsedNavigation.first, parsedNavigation.second)
    }

    private fun parseRouteAndParams(route: String): Pair<String, HashMap<String, String>> {
        // Handle query params: /page?dy1=1&dy2=2
        if (route.contains("?")) {
            val parts = route.split("?", limit = 2)
            val baseRoute = parts[0]
            val queryString = parts[1]
            val queryParams = hashMapOf<String, String>()

            queryString.split("&").forEach { paramPair ->
                val keyValue = paramPair.split("=", limit = 2)
                if (keyValue.size == 2) {
                    val key = keyValue[0].trim()
                    val value = try {
                        URLDecoder.decode(keyValue[1], "UTF-8")
                    } catch (e: Exception) {
                        keyValue[1]
                    }
                    queryParams[key] = value
                }
            }
            return baseRoute to queryParams
        }

        // Handle dynamic path params: /page/1/2/3/...
        val pathSegments = route.trim('/').split('/')
        if (pathSegments.size > 1) {
            val baseRoute = "/" + pathSegments.first()
            val queryParams = hashMapOf<String, String>()
            pathSegments.drop(1).forEachIndexed { idx, value ->
                queryParams["dynamic${idx + 1}"] = value
            }
            return baseRoute to queryParams
        }

        return route to hashMapOf()
    }
}