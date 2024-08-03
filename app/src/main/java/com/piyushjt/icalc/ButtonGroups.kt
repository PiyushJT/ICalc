package com.piyushjt.icalc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp


// Buttons in grid format for horizontal screen
@Composable
fun HorizontalButtons(
    event: (Event) -> Unit,
    state: State,
    screenHeight: Int,
    screenWidth: Int
) {
    val buttonHeight = (0.15 * screenHeight).dp
    val buttonWidth = (0.086 * screenWidth).dp
    val zeroButtonWidth = (0.18 * screenWidth).dp

    Column(
        modifier = Modifier
            .padding(bottom = (0.01 * screenHeight).dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = (0.01 * screenHeight).dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            ScientificButton(event = event, text = "(", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = ")", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "mc", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "m+", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "m-", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "mr", height = buttonHeight, width = buttonWidth)

            OtherButton(event = event, text = if (state.value != "0") "C" else "AC", height = buttonHeight, width = buttonWidth, textSize = 18)

            OtherButton(event = event, text = "+/-", height = buttonHeight, width = buttonWidth, textSize = 18)
            OtherButton(event = event, text = "%", height = buttonHeight, width = buttonWidth, textSize = 18)
            OppButton(state = state, event = event, text = "÷", height = buttonHeight, width = buttonWidth, textSize = 24)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = (0.01 * screenHeight).dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            ScientificButton(event = event, text = "2nd", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "x2", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "x3", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "xy", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "ex", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "10x", height = buttonHeight, width = buttonWidth)

            NumButton(state = state, event = event, text = "7", height = buttonHeight, width = buttonWidth, textSize = 24)
            NumButton(state = state, event = event, text = "8", height = buttonHeight, width = buttonWidth, textSize = 24)
            NumButton(state = state, event = event, text = "9", height = buttonHeight, width = buttonWidth, textSize = 24)
            OppButton(state = state, event = event, text = "×", height = buttonHeight, width = buttonWidth, textSize = 24)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = (0.01 * screenHeight).dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            ScientificButton(event = event, text = "1/x", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "2rtx", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "3rtx", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "yrtx", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "ln", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "log10", height = buttonHeight, width = buttonWidth)

            NumButton(state = state, event = event, text = "4", height = buttonHeight, width = buttonWidth, textSize = 24)
            NumButton(state = state, event = event, text = "5", height = buttonHeight, width = buttonWidth, textSize = 24)
            NumButton(state = state, event = event, text = "6", height = buttonHeight, width = buttonWidth, textSize = 24)
            OppButton(state = state, event = event, text = "-", height = buttonHeight, width = buttonWidth, textSize = 24)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = (0.01 * screenHeight).dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            ScientificButton(event = event, text = "x!", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "sin", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "cos", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "tan", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "e", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "EE", height = buttonHeight, width = buttonWidth)

            NumButton(state = state, event = event, text = "1", height = buttonHeight, width = buttonWidth, textSize = 24)
            NumButton(state = state, event = event, text = "2", height = buttonHeight, width = buttonWidth, textSize = 24)
            NumButton(state = state, event = event, text = "3", height = buttonHeight, width = buttonWidth, textSize = 24)
            OppButton(state = state, event = event, text = "+", height = buttonHeight, width = buttonWidth, textSize = 24)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = (0.01 * screenHeight).dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            ScientificButton(event = event, text = "Rad", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "sinh", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "cosh", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "tanh", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "π", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, text = "Rand", height = buttonHeight, width = buttonWidth)

            ZeroButton(state = state, event = event, height = buttonHeight, width = zeroButtonWidth, textSize = 24)
            DotButton(state = state, event = event, height = buttonHeight, width = buttonWidth, textSize = 24)
            OppButton(state = state, event = event, text = "=", height = buttonHeight, width = buttonWidth, textSize = 24)
        }


    }
}


// Buttons in grid format for vertical screen
@Composable
fun VerticalButtons(
    event: (Event) -> Unit,
    state: State
) {
    val heightWidth = (0.21 * (LocalConfiguration.current.screenWidthDp)).dp
    val widthForZero = (0.452 * (LocalConfiguration.current.screenWidthDp)).dp
    Column(
        modifier = Modifier
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            OtherButton(event = event, text = if (state.value != "0") "C" else "AC", height = heightWidth, width = heightWidth, textSize = 24)

            OtherButton(event = event, text = "+/-", height = heightWidth, width = heightWidth, textSize = 24)
            OtherButton(event = event, text = "%", height = heightWidth, width = heightWidth, textSize = 24)
            OppButton(state = state, event = event, text = "÷", height = heightWidth, width = heightWidth, textSize = 30)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            NumButton(state = state, event = event, text = "7", height = heightWidth, width = heightWidth, textSize = 30)
            NumButton(state = state, event = event, text = "8", height = heightWidth, width = heightWidth, textSize = 30)
            NumButton(state = state, event = event, text = "9", height = heightWidth, width = heightWidth, textSize = 30)
            OppButton(state = state, event = event, text = "×", height = heightWidth, width = heightWidth, textSize = 30)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            NumButton(state = state, event = event, text = "4", height = heightWidth, width = heightWidth, textSize = 30)
            NumButton(state = state, event = event, text = "5", height = heightWidth, width = heightWidth, textSize = 30)
            NumButton(state = state, event = event, text = "6", height = heightWidth, width = heightWidth, textSize = 30)
            OppButton(state = state, event = event, text = "-", height = heightWidth, width = heightWidth, textSize = 30)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            NumButton(state = state, event = event, text = "1", height = heightWidth, width = heightWidth, textSize = 30)
            NumButton(state = state, event = event, text = "2", height = heightWidth, width = heightWidth, textSize = 30)
            NumButton(state = state, event = event, text = "3", height = heightWidth, width = heightWidth, textSize = 30)
            OppButton(state = state, event = event, text = "+", height = heightWidth, width = heightWidth, textSize = 30)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            ZeroButton(state = state, event = event, height = heightWidth, width = widthForZero, textSize = 30)
            DotButton(state = state, event = event, height = heightWidth, width = heightWidth, textSize = 30)
            OppButton(state = state, event = event, text = "=", height = heightWidth, width = heightWidth, textSize = 30)
        }


    }
}