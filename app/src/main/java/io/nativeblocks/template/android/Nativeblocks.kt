package io.nativeblocks.template.android

import android.content.Context
import android.util.Log
import io.nativeblocks.core.api.NativeblocksEdition
import io.nativeblocks.core.api.NativeblocksManager
import io.nativeblocks.core.api.provider.logger.INativeLogger
import io.nativeblocks.core.api.provider.logger.LoggerEventLevel
import io.nativeblocks.foundation.FoundationProvider
import io.nativeblocks.template.android.integration.consumer.action.MyAppActionProvider
import io.nativeblocks.template.android.integration.consumer.block.MyAppBlockProvider
import io.nativeblocks.template.android.navigation.Navigator
import io.nativeblocks.wandkit.LiveKit

fun initNativeblocks(context: Context) {
    NativeblocksManager.initialize(
        applicationContext = context,
        edition = NativeblocksEdition.Cloud(
            endpoint = BuildConfig.NATIVEBLOCKS_API_URL,
            apiKey = BuildConfig.NATIVEBLOCKS_API_KEY,
            developmentMode = BuildConfig.DEBUG
        )
    )
    FoundationProvider.provide()

    /* to enable hot-reload and get live update in debug mode */
    if (BuildConfig.DEBUG) {
        NativeblocksManager.getInstance().wandKit(LiveKit(keepScreenOn = true, autoConnect = true))
    }

    /* if you need localization
     NativeblocksManager.getInstance().setLocalization("EN")
    */

    MyAppBlockProvider.provideBlocks()
    MyAppActionProvider.provideActions(
        alert = Alert(context),
        navigator = Navigator()
    )

    NativeblocksManager.getInstance().provideEventLogger("APP_LOGGER", AppLogger())
}

fun destroyNativeblocks() {
    NativeblocksManager.getInstance().destroy()
}

class AppLogger : INativeLogger {
    override fun log(
        level: LoggerEventLevel,
        event: String,
        message: String,
        parameters: Map<String, String>
    ) {
        val jsonBuilder = StringBuilder()
        jsonBuilder.appendLine("{")
        jsonBuilder.appendLine("  \"level\": \"${level.name}\",")
        jsonBuilder.appendLine("  \"event\": \"${event}\",")
        jsonBuilder.appendLine("  \"message\": \"${message.replace("\"", "\\\"")}\",")
        jsonBuilder.appendLine("  \"parameters\": {")
        val entries = parameters.entries.toList()
        for (i in entries.indices) {
            val entry = entries[i]
            val comma = if (i != entries.size - 1) "," else ""
            jsonBuilder.appendLine("    \"${entry.key}\": \"${entry.value.replace("\"", "\\\"")}\"$comma")
        }
        jsonBuilder.appendLine("  }")
        jsonBuilder.appendLine("}")

        val logTag = "NativeblocksLogger"
        val logMsg = jsonBuilder.toString()
        when (level) {
            LoggerEventLevel.ERROR -> Log.e(logTag, logMsg)
            LoggerEventLevel.WARNING -> Log.w(logTag, logMsg)
            LoggerEventLevel.INFO -> Log.i(logTag, logMsg)
        }
    }
}
