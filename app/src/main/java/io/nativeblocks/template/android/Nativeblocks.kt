package io.nativeblocks.template.android

import android.content.Context
import android.util.Log
import io.nativeblocks.core.api.NativeblocksEdition
import io.nativeblocks.core.api.NativeblocksManager
import io.nativeblocks.core.api.provider.logger.INativeLogger
import io.nativeblocks.foundation.FoundationProvider
import io.nativeblocks.template.android.integration.consumer.action.MyAppActionProvider
import io.nativeblocks.template.android.integration.consumer.block.MyAppBlockProvider
import io.nativeblocks.wandkit.LiveKit

fun initNativeblocks(context: Context) {
    NativeblocksManager.initialize(
        applicationContext = context,
        edition = NativeblocksEdition.Cloud(
            endpoint = BuildConfig.NATIVEBLOCKS_API_URL,
            apiKey = BuildConfig.NATIVEBLOCKS_API_KEY,
            developmentMode = true
        )
    )
    FoundationProvider.provide()

    /* to enable hot-reload and get live update in debug mode */
    NativeblocksManager.getInstance().wandKit(LiveKit())

    /* if you need localization
     NativeblocksManager.getInstance().setLocalization("EN")
    */

    MyAppBlockProvider.provideBlocks()
    MyAppActionProvider.provideActions(alert = Alert(context))

    NativeblocksManager.getInstance().provideEventLogger("APP_LOGGER", AppLogger())
}

fun destroyNativeblocks() {
    NativeblocksManager.getInstance().destroy()
}

class AppLogger : INativeLogger {
    override fun log(eventName: String, parameters: Map<String, String>) {
        Log.d(eventName, "log: $parameters")
    }
}
