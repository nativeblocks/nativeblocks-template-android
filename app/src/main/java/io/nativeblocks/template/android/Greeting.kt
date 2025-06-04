package io.nativeblocks.template.android

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.nativeblocks.compiler.type.NativeBlock
import io.nativeblocks.compiler.type.NativeBlockData
import io.nativeblocks.compiler.type.NativeBlockProp

@NativeBlock(
    name = "Greeting",
    keyType = "GREETING",
    version = 1,
    versionName = "1.0.0",
    description = "A simple greeting block"
)
@Composable
fun Greeting(
    @NativeBlockData
    name: String,
    @NativeBlockProp(defaultValue = "16")
    fontSize: TextUnit = 16.sp
) {
    val modifier: Modifier = Modifier
    Text(
        text = "Hello $name!",
        fontSize = fontSize,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting("Android")
}