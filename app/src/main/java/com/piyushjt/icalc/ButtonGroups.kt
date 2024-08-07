package com.piyushjt.icalc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
    val buttonHeight = (0.16 * screenHeight).dp
    val buttonWidth = (0.086 * screenWidth).dp
    val zeroButtonWidth = ((2 * buttonWidth.value) + ((screenWidth - (10 * buttonWidth.value)) / 11)).dp

    Column(
        modifier = Modifier
            .padding(bottom = (0.01 * screenHeight).dp)
            .fillMaxHeight(),
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
            ScientificButton(event = event, state = state, text = "(", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = ")", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "mc", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "m+", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "m-", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "mr", height = buttonHeight, width = buttonWidth)

            OtherButton(event = event, text = if (state.value != "0") "C" else "AC", height = buttonHeight, width = buttonWidth, textSize = 18)

            OtherButton(event = event, text = "+/-", height = buttonHeight, width = buttonWidth, textSize = 18)
            OtherButton(event = event, text = "%", height = buttonHeight, width = buttonWidth, textSize = 18)
            OppButton(state = state, event = event, text = "÷", height = buttonHeight, width = buttonWidth, textSize = 28)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = (0.01 * screenHeight).dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            ScientificButton(event = event, state = state, text = "2ⁿᵈ", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "x²", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "x³", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "xʸ", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "eˣ", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "10ˣ", height = buttonHeight, width = buttonWidth)

            NumButton(state = state, event = event, text = "7", height = buttonHeight, width = buttonWidth, textSize = 24)
            NumButton(state = state, event = event, text = "8", height = buttonHeight, width = buttonWidth, textSize = 24)
            NumButton(state = state, event = event, text = "9", height = buttonHeight, width = buttonWidth, textSize = 24)
            OppButton(state = state, event = event, text = "×", height = buttonHeight, width = buttonWidth, textSize = 28)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = (0.01 * screenHeight).dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            ScientificButton(event = event, state = state, text = "¹⁄ₓ", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "!", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "\"", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "'", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "ln", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "log₁₀", height = buttonHeight, width = buttonWidth)

            NumButton(state = state, event = event, text = "4", height = buttonHeight, width = buttonWidth, textSize = 24)
            NumButton(state = state, event = event, text = "5", height = buttonHeight, width = buttonWidth, textSize = 24)
            NumButton(state = state, event = event, text = "6", height = buttonHeight, width = buttonWidth, textSize = 24)
            OppButton(state = state, event = event, text = "-", height = buttonHeight, width = buttonWidth, textSize = 28)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = (0.01 * screenHeight).dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            ScientificButton(event = event, state = state, text = "x!", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "sin", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "cos", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "tan", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "e", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "EE", height = buttonHeight, width = buttonWidth)

            NumButton(state = state, event = event, text = "1", height = buttonHeight, width = buttonWidth, textSize = 24)
            NumButton(state = state, event = event, text = "2", height = buttonHeight, width = buttonWidth, textSize = 24)
            NumButton(state = state, event = event, text = "3", height = buttonHeight, width = buttonWidth, textSize = 24)
            OppButton(state = state, event = event, text = "+", height = buttonHeight, width = buttonWidth, textSize = 28)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = (0.01 * screenHeight).dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            ScientificButton(event = event, state = state, text = "Rad", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "sinh", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "cosh", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "tanh", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "π", height = buttonHeight, width = buttonWidth)
            ScientificButton(event = event, state = state, text = "Rand", height = buttonHeight, width = buttonWidth)

            ZeroButton(state = state, event = event, height = buttonHeight, width = zeroButtonWidth, textSize = 24)
            DotButton(state = state, event = event, height = buttonHeight, width = buttonWidth, textSize = 24)
            OppButton(state = state, event = event, text = "=", height = buttonHeight, width = buttonWidth, textSize = 28)
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
            OppButton(state = state, event = event, text = "÷", height = heightWidth, width = heightWidth, textSize = 38)
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
            OppButton(state = state, event = event, text = "×", height = heightWidth, width = heightWidth, textSize = 38)
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
            OppButton(state = state, event = event, text = "-", height = heightWidth, width = heightWidth, textSize = 38)
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
            OppButton(state = state, event = event, text = "+", height = heightWidth, width = heightWidth, textSize = 38)
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
            OppButton(state = state, event = event, text = "=", height = heightWidth, width = heightWidth, textSize = 38)
        }


    }
}