package io.nativeblocks.template.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import io.nativeblocks.core.api.NativeblocksError
import io.nativeblocks.core.api.NativeblocksFrame
import io.nativeblocks.core.api.NativeblocksLoading
import io.nativeblocks.core.api.NativeblocksManager
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initNativeblocks(this)

        lifecycleScope.launch {
            val scaffoldResult = NativeblocksManager.getInstance().getScaffold()
            if (scaffoldResult.isSuccess) {
                // register routes
                // val frameList = scaffoldResult.getOrNull()?.frames
            }
            if (scaffoldResult.isFailure) {
                // show proper error message
                // val errorMessage = scaffoldResult.exceptionOrNull()?.message
            }
        }

        setContent {
            NativeblocksFrame(
                frameRoute = "/",
                routeArguments = hashMapOf(),
                loading = {
                    NativeblocksLoading()
                },
                error = { message ->
                    NativeblocksError(message)
                },
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyNativeblocks()
    }
}