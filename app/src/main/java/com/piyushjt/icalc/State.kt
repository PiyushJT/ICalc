package com.piyushjt.icalc

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class State(
    val value : String = "0",
    val valueToShow : String = "0",
    val textSize : Int = 68,
    val previousValue: String? = null,
    val buttonClicked : String? = null,
    val buttonClickedForColor : String? = null,
    val isEqualPressed : Boolean = false,
    val isDotPressed : Boolean = false,
    val isValueSetAfterOperator: Boolean = false,
    val statusBarSize : Dp = 24.dp,
    val memory : Double = 0.0,
    val isInverseVisible : Boolean = false
)
