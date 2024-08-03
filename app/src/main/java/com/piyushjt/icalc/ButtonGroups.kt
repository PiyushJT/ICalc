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
    state: State
) {
    val height = (0.15 * (LocalConfiguration.current.screenHeightDp)).dp
    val width = (0.08 * (LocalConfiguration.current.screenWidthDp)).dp
    val widthForZero = (0.18 * (LocalConfiguration.current.screenWidthDp)).dp
    Column(
        modifier = Modifier
            .padding(bottom = (0.0285 * (LocalConfiguration.current.screenHeightDp)).dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = (0.0142 * (LocalConfiguration.current.screenHeightDp)).dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            ScientificButton(event = event, text = "(", height = height, width = width)
            ScientificButton(event = event, text = ")", height = height, width = width)
            ScientificButton(event = event, text = "mc", height = height, width = width)
            ScientificButton(event = event, text = "m+", height = height, width = width)
            ScientificButton(event = event, text = "m-", height = height, width = width)
            ScientificButton(event = event, text = "mr", height = height, width = width)

            OtherButton(event = event, text = if (state.value != "0") "C" else "AC", height = height, width = width, textSize = 18)

            OtherButton(event = event, text = "+/-", height = height, width = width, textSize = 18)
            OtherButton(event = event, text = "%", height = height, width = width, textSize = 18)
            OppButton(state = state, event = event, text = "÷", height = height, width = width, textSize = 24)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = (0.0142 * (LocalConfiguration.current.screenHeightDp)).dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            ScientificButton(event = event, text = "2nd", height = height, width = width)
            ScientificButton(event = event, text = "x2", height = height, width = width)
            ScientificButton(event = event, text = "x3", height = height, width = width)
            ScientificButton(event = event, text = "xy", height = height, width = width)
            ScientificButton(event = event, text = "ex", height = height, width = width)
            ScientificButton(event = event, text = "10x", height = height, width = width)

            NumButton(state = state, event = event, text = "7", height = height, width = width, textSize = 24)
            NumButton(state = state, event = event, text = "8", height = height, width = width, textSize = 24)
            NumButton(state = state, event = event, text = "9", height = height, width = width, textSize = 24)
            OppButton(state = state, event = event, text = "×", height = height, width = width, textSize = 24)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = (0.0142 * (LocalConfiguration.current.screenHeightDp)).dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            ScientificButton(event = event, text = "1/x", height = height, width = width)
            ScientificButton(event = event, text = "2rtx", height = height, width = width)
            ScientificButton(event = event, text = "3rtx", height = height, width = width)
            ScientificButton(event = event, text = "yrtx", height = height, width = width)
            ScientificButton(event = event, text = "ln", height = height, width = width)
            ScientificButton(event = event, text = "log10", height = height, width = width)

            NumButton(state = state, event = event, text = "4", height = height, width = width, textSize = 24)
            NumButton(state = state, event = event, text = "5", height = height, width = width, textSize = 24)
            NumButton(state = state, event = event, text = "6", height = height, width = width, textSize = 24)
            OppButton(state = state, event = event, text = "-", height = height, width = width, textSize = 24)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = (0.0142 * (LocalConfiguration.current.screenHeightDp)).dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            ScientificButton(event = event, text = "x!", height = height, width = width)
            ScientificButton(event = event, text = "sin", height = height, width = width)
            ScientificButton(event = event, text = "cos", height = height, width = width)
            ScientificButton(event = event, text = "tan", height = height, width = width)
            ScientificButton(event = event, text = "e", height = height, width = width)
            ScientificButton(event = event, text = "EE", height = height, width = width)

            NumButton(state = state, event = event, text = "1", height = height, width = width, textSize = 24)
            NumButton(state = state, event = event, text = "2", height = height, width = width, textSize = 24)
            NumButton(state = state, event = event, text = "3", height = height, width = width, textSize = 24)
            OppButton(state = state, event = event, text = "+", height = height, width = width, textSize = 24)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = (0.0142 * (LocalConfiguration.current.screenHeightDp)).dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // Showing text in buttons as needed
            ScientificButton(event = event, text = "Rad", height = height, width = width)
            ScientificButton(event = event, text = "sinh", height = height, width = width)
            ScientificButton(event = event, text = "cosh", height = height, width = width)
            ScientificButton(event = event, text = "tanh", height = height, width = width)
            ScientificButton(event = event, text = "π", height = height, width = width)
            ScientificButton(event = event, text = "Rand", height = height, width = width)

            ZeroButton(state = state, event = event, height = height, width = widthForZero, textSize = 24)
            DotButton(state = state, event = event, height = height, width = width)
            OppButton(state = state, event = event, text = "=", height = height, width = width, textSize = 24)
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
            DotButton(state = state, event = event, height = heightWidth, width = heightWidth)
            OppButton(state = state, event = event, text = "=", height = heightWidth, width = heightWidth, textSize = 30)
        }


    }
}
