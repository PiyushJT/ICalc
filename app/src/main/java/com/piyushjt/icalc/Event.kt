package com.piyushjt.icalc

sealed interface Event {
    data class SetPreviousValue(val previousValue : String?) : Event
    data class SetValue(val value : String) : Event
    data class SetValueToShow(val valueToShow : String) : Event
    data class AppendValue(val value : String) : Event
    data class SetButtonClicked(val buttonClicked : String?) : Event
    data class SetEqualPressed(val isEqualPressed : Boolean) : Event
    data class SetButtonClickedForColor(val buttonClickedForColor : String?) : Event
    data class SetValueSetAfterOperator(val isSet: Boolean) : Event
    data class SetDotPressed(val isPressed: Boolean) : Event
    object ClearValue : Event
    object ChangeSignOfValue : Event
    object ClearPreviousValue : Event
    object ClearAll : Event
    object ShowAns : Event
    object CalculatePercent : Event
}