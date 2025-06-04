package io.nativeblocks.template.android

import android.content.Context
import android.widget.Toast
import io.nativeblocks.compiler.type.NativeAction
import io.nativeblocks.compiler.type.NativeActionData
import io.nativeblocks.compiler.type.NativeActionFunction
import io.nativeblocks.compiler.type.NativeActionParameter
import io.nativeblocks.compiler.type.NativeActionProp
import io.nativeblocks.compiler.type.NativeActionValuePicker
import io.nativeblocks.compiler.type.NativeActionValuePickerOption

@NativeAction(
    name = "Alert",
    keyType = "ALERT",
    version = 1,
    versionName = "1.0.0",
    description = "A simple alert action"
)
class Alert(private val context: Context) {

    @NativeActionParameter
    data class Parameter(
        @NativeActionData
        val message: String,
        @NativeActionProp(
            valuePicker = NativeActionValuePicker.DROPDOWN,
            valuePickerOptions = [
                NativeActionValuePickerOption("0", "Short"),
                NativeActionValuePickerOption("1", "LONG")
            ],
            defaultValue = "0"
        )
        val duration: Int
    )

    @NativeActionFunction
    fun show(parameter: Parameter) {
        Toast.makeText(context, parameter.message, parameter.duration).show()
    }
}