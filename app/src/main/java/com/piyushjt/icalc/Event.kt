package com.piyushjt.icalc

sealed interface Event {

    // Modifying values
    data class SetValue(val value : String?) : Event
    data class AppendValue(val value : String) : Event
    data class SetValueToShow(val valueToShow : String) : Event
    data class SetPreviousValue(val previousValue : String?) : Event
    data class SetButtonClicked(val buttonClicked : String?) : Event
    data class SetButtonClickedForColor(val buttonClickedForColor : String?) : Event
    data class SetEqualPressed(val isEqualPressed : Boolean) : Event
    data class SetDotPressed(val isPressed: Boolean) : Event
    data class SetValueSetAfterOperator(val isSet: Boolean) : Event

    // Clearing values
    object ClearAll : Event
    object ClearValue : Event
    object ClearPreviousValue : Event
    object ClearLastChar : Event

    // Operations
    object ShowAns : Event
    object CalculatePercent : Event

    object ChangeSignOfValue : Event


    // Scientific Operations
    object SetReciprocal : Event
    object SetFactorial : Event

    data class SetPower(val value : Double, val power : Double) : Event
    data class SetTrig(val trig : String) : Event
    data class SetLog(val base : Double, val value : Double) : Event

    data class UpdateMemory(val sign : Int, val value : Double) : Event
    object MemoryClear : Event
    object MemoryRecall : Event

    object ToggleIsInverseVisible: Event
    object ToggleAngleUnitDeg: Event
}