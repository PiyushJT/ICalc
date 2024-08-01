package com.piyushjt.icalc

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.sign

class ViewModel : ViewModel() {

    // state
    private val _state = MutableStateFlow(State())
    val state : StateFlow<State> = _state

    // Event function
    fun event(event: Event){


        when (event) {

            // Clear All
            Event.ClearAll -> {
                _state.update {
                    it.copy(
                        value = "0",
                        valueToShow = "0",
                        previousValue = null,
                        buttonClicked = null,
                        buttonClickedForColor = null,
                        isEqualPressed = false,
                        isValueSetAfterOperator = false
                    )
                }
                Log.d("clear Everything", state.value.toString())
            }

            // Change Sign of value
            Event.ChangeSignOfValue -> {


                var value = state.value.value.toDouble()
                value = 0- value

                var newValue = value.toString()

                while (newValue.contains('.') && newValue.endsWith("0")){
                    newValue = newValue.removeSuffix("0")
                    if (newValue.endsWith('.')){
                        newValue = newValue.removeSuffix(".")
                    }
                }

                event(Event.SetValue(newValue))

                Log.d("Change sign", state.value.toString())
            }

            // Clear Value
            Event.ClearValue -> {
                _state.update {
                    it.copy(
                        value = "0",
                        valueToShow = "0"
                    )
                }
                Log.d("clear value", state.value.value.toString())
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

            // Set Previous value
            is Event.SetPreviousValue -> {
                _state.update {
                    it.copy(
                        previousValue = event.previousValue
                    )
                }
                Log.d("set previous value", state.value.previousValue.toString())
            }

            // Set Value
            is Event.SetValue -> {
                _state.update {
                    it.copy(
                        value = event.value
                    )
                }
                event(Event.SetValueToShow(state.value.value))

                Log.d("set value", state.value.value)
            }

            // Set Value To Show
            is Event.SetValueToShow -> {

                var givenValue = event.valueToShow.lowercase()

                if (givenValue.contains("e")) {
                    var indexOfe = givenValue.indexOf('e')

                    while (givenValue.substring(0, indexOfe).length >7) {
                        givenValue = givenValue.substring(0, indexOfe-1) + givenValue.substring(indexOfe)
                        indexOfe = givenValue.indexOf('e')
                    }

                }
                else if (givenValue.contains('.') && !givenValue.contains('e')){
                    while (givenValue.length > 10){
                        givenValue = givenValue.substring(0, 10)
                    }

                    while (givenValue.contains('.') && givenValue.endsWith("0")){
                        givenValue = givenValue.removeSuffix("0")
                        if (givenValue.endsWith('.')){
                            givenValue = givenValue.removeSuffix(".")
                        }
                    }
                }


                val formattedValue = formatWithCommas(givenValue)

                val textSize = when(formattedValue.length){
                    in 0..8 -> 68
                    9 -> 60
                    10 -> 60
                    11 -> 50
                    12 -> 50
                    else -> 50
                }

                _state.update {
                    it.copy(
                        valueToShow = formattedValue,
                        textSize = textSize
                    )
                }
                Log.d("set value to show", state.value.valueToShow)
                Log.d("text size", state.value.textSize.toString())
            }

            // Append Value
            is Event.AppendValue -> {
                if (state.value.value.length <= 8) {
                    _state.update {
                        it.copy(
                            value = "${state.value.value}${event.value}"
                        )
                    }

                    event(Event.SetValueToShow(state.value.value))
                }
                Log.d("append value", state.value.value)
            }

            // Set Value set after operator
            is Event.SetValueSetAfterOperator -> {
                _state.update {
                    it.copy(
                        isValueSetAfterOperator = event.isSet
                    )
                }
            }

            // Show Answer
            is Event.ShowAns -> {

                if (state.value.previousValue != null && state.value.buttonClicked != null){

                    val preValue = state.value.previousValue!!.toDouble()
                    val value = state.value.value.toDouble()

                    val ans = when(state.value.buttonClicked){

                        "×" -> "${preValue * value}"

                        "+" -> "${preValue + value}"

                        "-" -> "${preValue - value}"

                        "÷" -> "${preValue / value}"

                        else -> "0"

                    }

                    _state.update {
                        it.copy(
                            isValueSetAfterOperator = false
                        )
                    }
                    event(Event.SetValue(ans))
                    Log.d("show answer", "value ${state.value.value}\n value ${state.value.previousValue}")

                }
            }

            is Event.CalculatePercent -> {

                val value = state.value.value.toDouble()
                val previousValue = state.value.previousValue?.toDouble()
                val buttonClicked = state.value.buttonClicked

                if (buttonClicked == null && previousValue == null){

                    val ans = value / 100

                    event(Event.SetValue(ans.toString()))

                } else {

                    if (buttonClicked in listOf("+", "-", "÷")) {
                        event(Event.SetValue((value * previousValue!! / 100).toString()))
                        event(Event.ShowAns)
                    }
                    else {
                        event(Event.SetValue("${value * previousValue!! / 100}"))
                        event(Event.ClearPreviousValue)
                        event(Event.SetButtonClicked(null))
                        event(Event.SetButtonClickedForColor(null))
                        event(Event.SetEqualPressed(true))
                    }

                }
            }

        }

    }

}

// Add commas to the integer part before updating the state
private fun formatWithCommas(value: String): String {
    val isNegative = value.toDouble().sign == -1.0
    val parts = value.split('.')

    val integerPart = if(isNegative){
        parts[0].removePrefix("-")
    } else {
        parts[0]
    }

    val fractionalPart = if (parts.size > 1) parts[1] else ""

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

    val formatted = if(isNegative) {
        "-${groups.joinToString(",") { it.reversed() }.reversed()}"
    } else {
        groups.joinToString(",") { it.reversed() }.reversed()
    }

    return if (value  in listOf("infinity", "nan")) {
        value
    }
    else {
        if (fractionalPart.isNotEmpty()) "$formatted.$fractionalPart" else formatted
    }

}