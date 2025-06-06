package io.nativeblocks.template.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import io.nativeblocks.core.api.NativeblocksManager
import io.nativeblocks.core.frame.domain.model.NativeFrameRouteModel
import io.nativeblocks.template.android.navigation.NavigationGraph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val _frames = MutableStateFlow(emptyList<NativeFrameRouteModel>())

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        initNativeblocks(this)

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
            view.updatePadding(top = insets.top, bottom = insets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        lifecycleScope.launch {
            val scaffold = NativeblocksManager.getInstance().getScaffold()
            if (scaffold.isSuccess) {
                _frames.emit(scaffold.getOrNull()?.frames ?: listOf())
            }
            if (scaffold.isFailure) {
                val message = scaffold.exceptionOrNull()?.message ?: "App can not load, please try again later."
                Log.e("MainActivity", message)
            }
        }
        setContent {
            val frames by _frames.collectAsState()
            NavigationGraph(frames)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyNativeblocks()
    }
}