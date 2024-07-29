package com.piyushjt.icalc

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

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
                        value = 0,
                        previousValue = null,
                        buttonClicked = null,
                        buttonClickedForColor = null,
                        isEqualPressed = false,
                        isValueSetAfterOperator = false
                    )
                }
                Log.d("clear Everything", state.value.toString())
            }

            // Clear Value
            Event.ClearValue -> {
                _state.update {
                    it.copy(
                        value = 0
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
                Log.d("set value", state.value.value.toString())
            }

            // Append Value
            is Event.AppendValue -> {
                if (state.value.value.toString().length <= 8) {
                    _state.update {
                        it.copy(
                            value = "${state.value.value}${event.value}".toInt()
                        )
                    }
                }
                Log.d("append value", state.value.value.toString())
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

                    val ans = when(state.value.buttonClicked){

                        "×" -> { state.value.previousValue!! * state.value.value }

                        "+" -> { state.value.previousValue!! + state.value.value }

                        "-" -> { state.value.previousValue!! - state.value.value }

                        "÷" -> { state.value.previousValue!! / state.value.value }

                        else -> { 0 }

                    }

                    _state.update {
                        it.copy(
                            isValueSetAfterOperator = false,
                            value = ans
                        )
                    }
                    Log.d("show answer", "value ${state.value.value}\n value ${state.value.previousValue}")

                }
            }

        }

    }

}