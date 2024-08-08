package com.piyushjt.icalc

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.apache.commons.math3.special.Gamma
import java.lang.Math.toDegrees
import java.lang.Math.toRadians
import kotlin.math.acos
import kotlin.math.acosh
import kotlin.math.asin
import kotlin.math.asinh
import kotlin.math.atan
import kotlin.math.atanh
import kotlin.math.cos
import kotlin.math.cosh
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sin
import kotlin.math.sinh
import kotlin.math.tan
import kotlin.math.tanh

class ViewModel : ViewModel() {

    // state
    private val _state = MutableStateFlow(State())
    val state : StateFlow<State> = _state

    // Event function
    fun event(event: Event){

        when (event) {

            // Set Value
            is Event.SetValue -> {
                _state.update {
                    it.copy(
                        value = event.value
                    )
                }
                event(Event.SetValueToShow(state.value.value?: "Error"))

                Log.d("set value", state.value.value?: "Null")
            }

            // Append Value
            is Event.AppendValue -> {
                // Only append if length is less than 9
                if (state.value.value!!.length <= 8) {
                    _state.update {
                        it.copy(
                            value = "${state.value.value}${event.value}"
                        )
                    }

                    event(Event.SetValueToShow(state.value.value?: "Error"))
                }
                Log.d("append value", state.value.value?: "Null")
            }

            // Set Value To Show
            is Event.SetValueToShow -> {

                // Defining value
                var givenValue = event.valueToShow.lowercase()

                // FORMATING VALUE TO BE SHOWN ON SCREEN

                // Limiting length of value if it contains e
                if (givenValue.contains("e")) {
                    var indexOfe = givenValue.indexOf('e')

                    while (givenValue.substring(0, indexOfe).length >7) {
                        givenValue = givenValue.substring(0, indexOfe-1) + givenValue.substring(indexOfe)
                        indexOfe = givenValue.indexOf('e')
                    }

                }
                else if (givenValue.contains('.') && !givenValue.contains('e')){

                    // Limiting length of value if it doesn't contain e
                    while (givenValue.length > 10){
                        givenValue = givenValue.substring(0, 10)
                    }

                    // Not showing . if it is not pressed by user
                    if (!state.value.isDotPressed) {
                        while (givenValue.contains('.') && givenValue.endsWith("0")) {
                            givenValue = givenValue.removeSuffix("0")
                            if (givenValue.endsWith('.')) {
                                givenValue = givenValue.removeSuffix(".")
                            }
                        }
                    }

                }

                // Formating value with commas
                val formattedValue = if(event.valueToShow == "Error") "Error"
                else formatWithCommas(givenValue, state.value.isDotPressed)


                // Defining text size based on value of length
                val textSize =
                    if(formattedValue.contains(',')) {
                        when (formattedValue.length) {
                            in 0..8 -> 68
                            9 -> 64
                            10 -> 60
                            11 -> 54
                            12 -> 50
                            else -> 50
                        }
                    } else if(formattedValue.contains('e')) {
                        when (formattedValue.length) {
                            in 0..8 -> 64
                            9 -> 52
                            10 -> 48
                            11 -> 44
                            12 -> 40
                            else -> 36
                        }
                    } else{
                        when (formattedValue.length) {
                            in 0..8 -> 68
                            9 -> 56
                            10 -> 52
                            11 -> 48
                            12 -> 44
                            else -> 40
                        }
                    }

                // Updating the state
                _state.update {
                    it.copy(
                        valueToShow = formattedValue,
                        textSize = textSize
                    )
                }
                Log.d("set value to show", state.value.valueToShow)
                Log.d("text size", state.value.textSize.toString())
            }

            // Set Previous value
            is Event.SetPreviousValue -> {
                _state.update {
                    it.copy(
                        previousValue = event.previousValue
                    )
                }
                Log.d("set previous value", state.value.previousValue.toString())
            }

            // Set Button clicked
            is Event.SetButtonClicked -> {
                _state.update {
                    it.copy(
                        buttonClicked = event.buttonClicked
                    )
                }
                Log.d("set button clicked", state.value.buttonClicked.toString())
            }

            // Set Button clicked for color
            is Event.SetButtonClickedForColor -> {
                _state.update {
                    it.copy(
                        buttonClickedForColor = event.buttonClickedForColor
                    )
                }
                Log.d("set button for color", state.value.buttonClickedForColor.toString())
            }

            // Set Equal button pressed
            is Event.SetEqualPressed -> {
                _state.update {
                    it.copy(
                        isEqualPressed = event.isEqualPressed
                    )
                }
                Log.d("set equal pressed", state.value.isEqualPressed.toString())
            }

            // Set Dot Pressed
            is Event.SetDotPressed -> {
                _state.update {
                    it.copy(
                        isDotPressed = event.isPressed
                    )
                }
                Log.d("Set dot pressed", state.value.isDotPressed.toString())
            }

            // Set Value set after operator
            is Event.SetValueSetAfterOperator -> {
                _state.update {
                    it.copy(
                        isValueSetAfterOperator = event.isSet
                    )
                }
            }


            // Set Status bar size
            is Event.SetStatusBarSize -> {
                _state.update {
                    it.copy(
                        statusBarSize = event.size
                    )
                }
                Log.d("Status bar size", state.value.statusBarSize.toString())
            }

            // Clear All
            Event.ClearAll -> {
                _state.update {
                    it.copy(
                        value = "0",
                        valueToShow = "0",
                        textSize = 68,
                        previousValue = null,
                        buttonClicked = null,
                        buttonClickedForColor = null,
                        isEqualPressed = false,
                        isDotPressed = false,
                        isValueSetAfterOperator = false
                    )
                }
                Log.d("Clear Everything", state.value.toString())
            }

            // Clear Value
            Event.ClearValue -> {
                _state.update {
                    it.copy(
                        value = "0",
                        valueToShow = "0",
                        isDotPressed = false,
                        textSize = 68
                    )
                }
                Log.d("clear value", state.value.value?: "Null")
            }

            // Clear Previous value
            Event.ClearPreviousValue -> {
                _state.update {
                    it.copy(
                        previousValue = null
                    )
                }
                Log.d("clear previous value", state.value.previousValue.toString())
            }

            // Clear Last Char (delete on swipe)
            Event.ClearLastChar -> {

                val value = state.value.value?: "0"

                // Only clear if possible
                if((!state.value.isEqualPressed) && state.value.buttonClickedForColor == null){

                    val newValue =
                        if (value.length == 2 && value.toDouble() < 0)
                            0.toString()
                        else if (value.length < 2) "0"
                        else value.substring(0, value.length - 1)

                    event(Event.SetValue(newValue))

                    Log.d("Clear last", newValue)

                }
            }

            // Show Answer
            is Event.ShowAns -> {

                event(Event.SetDotPressed(false))

                if (state.value.previousValue != null && state.value.buttonClicked != null){

                    val preValue = state.value.previousValue!!.toDouble()
                    val value = state.value.value?.toDouble()?: 0.0

                    val ans = when(state.value.buttonClicked){

                        "×" -> if("${preValue * value}" != "Infinity") "${preValue * value}" else null

                        "+" -> "${preValue + value}"

                        "-" -> "${preValue - value}"

                        "÷" -> if(value != 0.0) "${preValue / value}" else null

                        "xʸ" -> "${preValue.pow(value)}"

                        "'" -> if(value != 0.0)"${preValue.pow(1/value)}" else null

                        "logᵧ" -> if(preValue != 0.0) "${log(preValue, value)}" else "1.0"

                        "x10ʸ" -> "${preValue * 10.0.pow(value)}"

                        else -> null

                    }

                    event(Event.SetValueSetAfterOperator(false))
                    event(Event.SetValue(ans))

                    Log.d("show answer", "value ${state.value.value}\n value ${state.value.previousValue}")

                }
            }

            // Calculate Percentage
            is Event.CalculatePercent -> {

                // Defining values
                val value = state.value.value?.toDouble() ?: 0.0
                val previousValue = state.value.previousValue?.toDouble()
                val buttonClicked = state.value.buttonClicked

                // If only one value is given
                if (buttonClicked == null && previousValue == null){

                    val ans = value / 100

                    event(Event.SetValue(ans.toString()))
                    event(Event.SetEqualPressed(true))

                } else {

                    // If multiply was not clicked
                    if (buttonClicked in listOf("+", "-", "÷")) {
                        event(Event.SetValue((value * previousValue!! / 100).toString()))
                        event(Event.ShowAns)
                    }

                    // If multiply was clicked
                    else {
                        event(Event.SetValue("${value * previousValue!! / 100}"))
                        _state.update {
                            it.copy(
                                isValueSetAfterOperator = false
                            )
                        }
                    }

                    // Clear everything and set equal pressed to true
                    event(Event.ClearPreviousValue)
                    event(Event.SetButtonClicked(null))
                    event(Event.SetButtonClickedForColor(null))
                    event(Event.SetEqualPressed(true))

                }
            }

            // Change Sign of value
            Event.ChangeSignOfValue -> {

                // Defining value
                var value = state.value.value?.toDouble() ?: 0.0
                value = 0- value

                var newValue = value.toString()

                // Removing trailing zeros after .
                while (newValue.contains('.') && newValue.endsWith("0")){
                    newValue = newValue.removeSuffix("0")
                    if (newValue.endsWith('.')){
                        newValue = newValue.removeSuffix(".")
                    }
                }

                event(Event.SetValue(newValue))

                Log.d("Change sign", state.value.value?: "Null")
            }

            Event.SetReciprocal -> {

                val value = state.value.value?.toDouble()?: 0.0

                event(Event.SetDotPressed(false))
                event(Event.SetEqualPressed(false))

                val reciprocal = if(value != 0.0) (1 / value).toString() else null

                event(Event.SetValue(reciprocal))

                Log.d("Set reciprocal", state.value.value?: "Null")
            }

            Event.SetFactorial -> {

                event(Event.SetDotPressed(false))
                event(Event.SetEqualPressed(true))

                val value = state.value.value?.toDouble() ?: 0.0

                // Check if the value is an integer
                val factorial = if (value == value.toInt().toDouble()) {

                    if(value in 0.0..170.0) {

                        (1..value.toInt()).fold(1.0) { acc, i -> acc * i }
                    }
                    else {
                        0.0
                    }

                }
                else {

                    Gamma.gamma(value + 1)

                }

                event(Event.SetValue(factorial.toString()))

                Log.d("Set factorial", state.value.value?: "Null")
            }

            is Event.SetPower -> {
                event(Event.SetDotPressed(false))
                event(Event.SetEqualPressed(true))

                val power = event.value.pow(event.power)

                event(Event.SetValue(power.toString()))

                Log.d("Set power", state.value.value?: "Null")

            }

            is Event.SetTrig -> {
                event(Event.SetDotPressed(false))
                event(Event.SetEqualPressed(true))


                val value = state.value.value?.toDouble()?: 0.0

                when(event.trig){

                    "sin" -> {
                        if(state.value.angleUnitDeg){
                            event(Event.SetValue(sin(toRadians(value)).toString()))
                        } else {
                            event(Event.SetValue(sin(value).toString()))
                        }
                    }

                    "cos" -> {
                        if(state.value.angleUnitDeg){
                            event(Event.SetValue(cos(toRadians(value)).toString()))
                        } else {
                            event(Event.SetValue(cos(value).toString()))
                        }
                    }

                    "tan" -> {
                        if(state.value.angleUnitDeg){
                            event(Event.SetValue(tan(toRadians(value)).toString()))
                        } else {
                            event(Event.SetValue(tan(value).toString()))
                        }
                    }

                    "sin⁻¹" -> {

                        if(value >=-1 && value <= 1) {
                            if (state.value.angleUnitDeg) {
                                event(Event.SetValue(toDegrees(asin(value)).toString()))
                            } else {
                                event(Event.SetValue(asin(value).toString()))
                            }
                        } else {
                            event(Event.SetValue(null))
                        }
                    }

                    "cos⁻¹" -> {

                        if(value >=-1 && value <= 1) {
                            if(state.value.angleUnitDeg){
                                event(Event.SetValue(toDegrees(acos(value)).toString()))
                            } else {
                                event(Event.SetValue(acos(value).toString()))
                            }
                        } else {
                            event(Event.SetValue(null))
                        }
                    }

                    "tan⁻¹" -> {
                        if(state.value.angleUnitDeg){
                            event(Event.SetValue(toDegrees(atan(value)).toString()))
                        } else {
                            event(Event.SetValue(atan(value).toString()))
                        }
                    }

                    "sinh" -> event(Event.SetValue(sinh(value).toString()))

                    "cosh" -> event(Event.SetValue(cosh(value).toString()))

                    "tanh" -> event(Event.SetValue(tanh(value).toString()))


                    "sinh⁻¹" -> event(Event.SetValue(asinh(value).toString()))

                    "cosh⁻¹" -> event(Event.SetValue(acosh(value).toString()))

                    "tanh⁻¹" -> event(Event.SetValue(atanh(value).toString()))


                }

                Log.d("Set trig", state.value.value?: "Null")
            }

            is Event.SetLog -> {
                event(Event.SetDotPressed(false))
                event(Event.SetEqualPressed(true))


                val logAns = if(event.value != 0.0) log(event.value, event.base) else 1.0

                event(Event.SetValue(logAns.toString()))

                Log.d("Set log", state.value.value?: "Null")
            }

            is Event.UpdateMemory -> {

                event(Event.SetDotPressed(false))
                event(Event.SetEqualPressed(true))

                var memory = state.value.memory

                memory += (event.sign)*(event.value)

                _state.update {
                    it.copy(
                        memory = memory
                    )
                }

                Log.d("Update memory", state.value.memory.toString())
            }

            Event.MemoryClear -> {
                _state.update {
                    it.copy(
                        memory = 0.0
                    )
                }

                Log.d("Clear memory", state.value.memory.toString())
            }

            Event.MemoryRecall -> {

                event(Event.SetDotPressed(false))
                event(Event.SetEqualPressed(true))

                event(Event.SetValue(state.value.memory.toString()))

                Log.d("Recall memory", state.value.memory.toString())
            }

            is Event.ToggleIsInverseVisible -> {

                _state.update {
                    it.copy(
                        isInverseVisible = !state.value.isInverseVisible
                    )
                }

                Log.d("Toggle inverse visibility", state.value.isInverseVisible.toString())
            }

            is Event.ToggleAngleUnitDeg -> {

                _state.update {
                    it.copy(
                        angleUnitDeg = !state.value.angleUnitDeg
                    )
                }

                Log.d("Toggle angle Unit", state.value.angleUnitDeg.toString())
            }


        }

    }

}

// Function to Format value with commas
private fun formatWithCommas(value: String, isDotPressed: Boolean): String {

    // DEFINING SOME VALUES
    val isNegative = value.toDouble().sign == -1.0
    val parts = value.split('.')

    val integerPart = if(isNegative){
        parts[0].removePrefix("-")
    } else {
        parts[0]
    }

    val fractionalPart = if (parts.size > 1) parts[1] else ""

    // Making groups separated by commas
    val groups = when(integerPart.length) {
        0 -> listOf()
        in 1 .. 3 -> listOf(integerPart)
        4 -> listOf(integerPart.substring(0,1), integerPart.substring(1,4))
        5 -> listOf(integerPart.substring(0,2), integerPart.substring(2,5))
        6 -> listOf(integerPart.substring(0,1), integerPart.substring(1,3), integerPart.substring(3,6))
        7 -> listOf(integerPart.substring(0,2), integerPart.substring(2,4), integerPart.substring(4,7))
        8 -> listOf(integerPart.substring(0,1), integerPart.substring(1,3), integerPart.substring(3,5), integerPart.substring(5,8))
        9 -> listOf(integerPart.substring(0,2), integerPart.substring(2,4), integerPart.substring(4,6), integerPart.substring(6,9))
        else -> { listOf() }
    }.reversed()

    Log.d("parts", groups.toString())

    // Formated value
    val formatted = if(isNegative) {
        "-${groups.joinToString(",") { it.reversed() }.reversed()}"
    } else {
        groups.joinToString(",") { it.reversed() }.reversed()
    }

    // Returning value as per conditions
    return if (value  in listOf("error", "infinity", "nan")) {
        "Error"
    }
    else {
        if (fractionalPart.isNotEmpty() || isDotPressed) "$formatted.$fractionalPart" else formatted
    }

}

fun main(){
}