package com.piyushjt.icalc

data class State(
    val value : String = "0",
    val valueToShow : String = "0",
    val previousValue: String? = null,
    val buttonClicked : String? = null,
    val buttonClickedForColor : String? = null,
    val isEqualPressed : Boolean = false,
    val isValueSetAfterOperator: Boolean = false
)
