package com.piyushjt.icalc

data class State(
    val value : Int = 0,
    val previousValue: Int? = null,
    val buttonClicked : String? = null,
    val buttonClickedForColor : String? = null,
    val isEqualPressed : Boolean = false,
    val isValueSetAfterOperator: Boolean = false
)
