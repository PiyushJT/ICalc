package com.piyushjt.icalc

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.piyushjt.icalc.ui.theme.Background
import com.piyushjt.icalc.ui.theme.NumBtnColor
import com.piyushjt.icalc.ui.theme.OnTopBtn
import com.piyushjt.icalc.ui.theme.OrangeBtnColor
import com.piyushjt.icalc.ui.theme.SciBtnColor
import com.piyushjt.icalc.ui.theme.TopBtnColor
import com.piyushjt.icalc.ui.theme.Transparent
import com.piyushjt.icalc.ui.theme.White
import kotlinx.coroutines.coroutineScope


class MainActivity : ComponentActivity() {

    // View Model (MVVM)
    private val viewModel: ViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Setting status bar & navigation bar colors
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = false
        Background.toArgb().also { window.navigationBarColor = it }


        setContent {

            // State
            val state by viewModel.state.collectAsState()

            val configuration = LocalConfiguration.current
            val aspectRatio = configuration.screenWidthDp / configuration.screenHeightDp


            WindowCompat.setDecorFitsSystemWindows(window, false)



            if(aspectRatio <= 0.8) {

                // Vertical Screen
                VerticalScreen(
                    state = state,
                    event = viewModel::event
                )
            }
            else {
                val systemUiController = rememberSystemUiController()

                // Hide the status bar
                systemUiController.isStatusBarVisible = false

                // Horizontal Screen
                HorizontalScreen(
                    state = state,
                    event = viewModel::event
                )
            }
        }
    }
}


@Preview(
    showSystemUi = true
)
@Composable
private fun Screen() {
    val configuration = LocalConfiguration.current
    val aspectRatio = configuration.screenWidthDp / configuration.screenHeightDp

    // View Model (MVVM)
    val viewModel = ViewModel()
    val state by viewModel.state.collectAsState()


    if(aspectRatio < 0.8) {

        // Vertical Screen
        VerticalScreen(
            state = state,
            event = viewModel::event
        )
    }
    else {
        // Horizontal Screen
        HorizontalScreen(
            state = state,
            event = viewModel::event
        )
    }
}

// Horizontal Screen
@Composable
fun HorizontalScreen(
    state: State,
    event: (Event) -> Unit
) {

    Log.d("Height", LocalConfiguration.current.screenHeightDp.toString())
    Log.d("Width", LocalConfiguration.current.screenWidthDp.toString())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(
                bottom = WindowInsets.navigationBars
                    .asPaddingValues()
                    .calculateBottomPadding(),
                start = state.statusBarSize,
                end = WindowInsets.navigationBars.asPaddingValues().calculateEndPadding(
                    LayoutDirection.Ltr
                )
            ),
        verticalArrangement = Arrangement.Bottom
    ) {

        // Value text view
        TextValue(
            state = state,
            event = event,
            height = (0.15 * (LocalConfiguration.current.screenHeightDp)).dp,
            textSize = null
        )

        // Buttons in grid layout
        HorizontalButtons(event = event, state = state)
    }
}

// Vertical Screen
@Composable
fun VerticalScreen(
    state: State,
    event: (Event) -> Unit
) {

    event(Event.SetStatusBarSize(WindowInsets.statusBars.asPaddingValues().calculateTopPadding()))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = WindowInsets
                    .navigationBars
                    .asPaddingValues()
                    .calculateBottomPadding()
            )
            .background(Background),
        verticalArrangement = Arrangement.Bottom
    ) {

        // Value text view
        TextValue(
            state = state,
            event = event,
            height = (0.15 * (LocalConfiguration.current.screenHeightDp)).dp,
            textSize = state.textSize
        )

        // Buttons in grid layout
        VerticalButtons(event = event, state = state)
    }
}


// Value Text View
@Composable
fun TextValue(
    state: State,
    event: (Event) -> Unit,
    height: Dp,
    textSize: Int?
) {
    // State of event trigger
    var isEventTriggered = remember { mutableStateOf(false) }

    // Container for text value
    Box(
        modifier = Modifier
            .padding(
                start = 20.dp, end = 20.dp,
                bottom = (0.0142 * (LocalConfiguration.current.screenHeightDp)).dp
            )
            .fillMaxWidth()
            .background(Background)
            .height(height)

            // Swipe Listener
            .pointerInput(Unit) {
                coroutineScope {
                    awaitPointerEventScope {
                        while (true) {
                            val pointerEvent = awaitPointerEvent(PointerEventPass.Initial)

                            if (pointerEvent.type == PointerEventType.Move) {

                                if (!isEventTriggered.value) {
                                    event(Event.ClearLastChar)
                                    isEventTriggered.value = true
                                }
                            }
                            if (pointerEvent.type == PointerEventType.Release) {
                                isEventTriggered.value = false
                            }
                        }
                    }
                }
            },
        contentAlignment = Alignment.BottomEnd
    ) {

        val textToShow = if (state.valueToShow.contains('e')) {

            "${state.valueToShow
                .substring(0, state.valueToShow.indexOf('e') + 1)
                .replace("e", "x10")}${(state.value.substringAfter('e'))}"

        } else {
            state.valueToShow
        }


        // Text Value
        Text(
            text = buildAnnotatedString {

                // Showing power in superscript
                if (textToShow.contains("x10")) {

                    append(textToShow.substring(0, textToShow.indexOf("x10") + 3))

                    withStyle(
                        style = SpanStyle(
                            fontSize = 24.sp,
                            baselineShift = BaselineShift.Superscript
                        )
                    ) {
                        append(textToShow.substringAfter("x10"))
                    }
                }
                else {
                    append(textToShow)
                }
            },
            color = White,
            fontFamily = FontFamily(Font(R.font.inter_light)),
            fontSize = (textSize?: 28).sp
        )
    }
}


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



// Dot Button
@Composable
fun DotButton(
    event: (Event) -> Unit,
    state: State,
    height : Dp,
    width : Dp
) {
    Button(
        onClick = {

            // If equal button was pressed
            if(state.isEqualPressed){
                event(Event.SetDotPressed(true)) // Set dot pressed to true
                event(Event.SetValue("0.")) // Set value to 0.
                event(Event.SetValueSetAfterOperator(false)) // Set value set after operator to false
                event(Event.SetEqualPressed(false)) // Set equal pressed to false
            }

            // If equal button was not pressed
            else {

                // If dot is not present already
                if (!state.value.contains(".")) {
                    event(Event.SetDotPressed(true)) // Set dot pressed to true

                    // If operation button was just clicked
                    if (state.buttonClickedForColor != null) {
                        event(Event.SetPreviousValue(state.value)) // Set previous value to $currentValue
                        event(Event.SetValue("0.")) // Set value to 0.
                        event(Event.SetValueSetAfterOperator(true))
                    } else {
                        event(Event.AppendValue(".")) // Append .
                    }
                }

                // If operation button was just clicked
                else if (state.buttonClickedForColor != null) {
                    event(Event.SetDotPressed(true)) // Set dot pressed to true
                    event(Event.SetValue("0.")) // Set value to 0.
                    event(Event.SetValueSetAfterOperator(true))
                }
            }
        },
        modifier= Modifier
            .background(Transparent)
            .clip(RoundedCornerShape(50.dp))
            .width(width)
            .height(height),
        colors = ButtonDefaults.buttonColors(containerColor = NumBtnColor)
    ) {
        Text(
            text = ".",
            color = White,
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.inter_light)),
            fontWeight = FontWeight.Bold
        )
    }
}


// Number button
@Composable
fun NumButton(
    event: (Event) -> Unit,
    text : String,
    state: State,
    height : Dp,
    width : Dp,
    textSize : Int
) {
    TextButton(
        onClick = {

            // If equal button was pressed just before clicking on a number
            if(state.isEqualPressed){
                event(Event.ClearAll) // Clear everything
                event(Event.SetValue(text)) // Set the new number
            }

            // If equal button was not pressed
            else {

                // If no operation button was clicked before
                if(state.buttonClicked == null){

                    // if value was 0 i.e., empty
                    if(state.value == "0"){
                        event(Event.SetValue(text)) // change value directly
                    }

                    // if value was not 0
                    else{
                        event(Event.AppendValue(text)) // append a number
                    }

                }

                // If an operation button was clicked
                else {

                    if(state.value == "0"){
                        event(Event.SetValue(text)) // change value directly
                    }
                    else {
                        // If a value has been set after the operator was clicked
                        if (state.isValueSetAfterOperator) {
                            event(Event.AppendValue(text)) // Append the new number to the existing value
                        }

                        // If a value is not been added
                        else {
                            if (state.value == "0."){
                                event(Event.AppendValue(text))
                            }
                            else {
                                event(Event.SetPreviousValue(state.value)) // Update the previous value
                                event(Event.SetValue(text)) // Set the new number
                                event(Event.SetButtonClickedForColor(null)) // Change the button to null

                            }

                            event(Event.SetValueSetAfterOperator(true))
                        }
                    }

                }

            }

        },
        modifier= Modifier
            .background(Transparent)
            .clip(RoundedCornerShape(50.dp))
            .width(width)
            .height(height),
        colors = ButtonDefaults.buttonColors(containerColor = NumBtnColor)
    ) {
        Text(
            text = text,
            color = White,
            fontSize = textSize.sp,
            fontFamily = FontFamily(Font(R.font.inter_light)),
            fontWeight = FontWeight.Bold
        )
    }
}


// Zero button
@Composable
fun ZeroButton(
    state: State,
    event: (Event) -> Unit,
    height : Dp,
    width : Dp,
    textSize : Int
) {
    Button(
        onClick = {
            // If equal button was pressed just before clicking on a number
            if(state.isEqualPressed){
                event(Event.SetValue("0")) // changing value
            }

            // If equal button was not pressed
            else {

                // If no operation button was clicked before
                if(state.buttonClicked == null){

                    // if value was 0 i.e., empty
                    if(state.value == "0"){
                        event(Event.SetValue("0")) // change value directly
                    }

                    // if value was not 0
                    else{
                        event(Event.AppendValue("0")) // append a number
                    }

                }

                // If an operation button was clicked
                else {

                    // If a value has been set after the operator was clicked
                    if (state.isValueSetAfterOperator) {
                        if(state.value != "0") {
                            event(Event.AppendValue("0")) // Append the new number to the existing value
                        }
                    }

                    // If a value is not been added
                    else {
                        event(Event.SetPreviousValue(state.value)) // Update the previous value
                        event(Event.SetValue("0")) // Set the new number
                        event(Event.SetButtonClickedForColor(null)) // Change the button to null

                        event(Event.SetValueSetAfterOperator(true))

                    }

                }

            }
        },
        modifier = Modifier
            .background(Color.Transparent)
            .clip(RoundedCornerShape(50.dp))
            .width(width)
            .height(height),
        colors = ButtonDefaults.buttonColors(containerColor = NumBtnColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start =
                    if (textSize == 24)
                        (0.007 * (LocalConfiguration.current.screenWidthDp)).dp
                    else
                        (0.02 * (LocalConfiguration.current.screenWidthDp)).dp
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "0",
                color = White,
                fontSize = textSize.sp,
                fontFamily = FontFamily(Font(R.font.inter_light)),
                fontWeight = FontWeight.Bold
            )
        }
    }
}


// Operation button
@Composable
fun OppButton(
    event: (Event) -> Unit,
    text : String,
    state: State,
    height : Dp,
    width : Dp,
    textSize : Int
) {
    TextButton(
        onClick = {

            event(Event.SetDotPressed(false))

            // If equal to button is clicked
            if (text == "=") {

                event(Event.ShowAns) // Show ans
                // clear everything and set equal pressed to true
                event(Event.ClearPreviousValue)
                event(Event.SetButtonClicked(null))
                event(Event.SetButtonClickedForColor(null))
                event(Event.SetEqualPressed(true))

            }

            // If any operation button is pressed
            else {

                // If equal button was not pressed recently
                if(!state.isEqualPressed) {

                    // If operation button not clicked previously
                    if (state.buttonClicked == null) {
                        event(Event.SetPreviousValue(state.value)) // Set previous value
                        // Set button clicked
                        event(Event.SetButtonClicked(text))
                        event(Event.SetButtonClickedForColor(text))
                        if (state.valueToShow == "0") {
                            event(Event.ShowAns)
                        }
                    }

                    // If operation button was clicked previously
                    else {

                        // If button color is null
                        if(state.buttonClickedForColor == null) {
                            event(Event.ShowAns) // show and
                            event(Event.ClearPreviousValue) // clear previous value
                            // Set button clicked
                            event(Event.SetButtonClicked(text))
                            event(Event.SetButtonClickedForColor(text))
                        }

                        // If button color is not null
                        else {
                            // Set button clicked
                            event(Event.SetButtonClicked(text))
                            event(Event.SetButtonClickedForColor(text))
                        }
                    }
                }

                // If equal button was pressed recently
                else {
                    event(Event.SetPreviousValue(state.value)) // Set previous value
                    // Set button clicked
                    event(Event.SetButtonClicked(text))
                    event(Event.SetButtonClickedForColor(text))
                    // Set equal button pressed to false
                    event(Event.SetEqualPressed(false))
                }

            }
        },
        modifier= Modifier
            .background(Transparent)
            .clip(RoundedCornerShape(50.dp))
            .width(width)
            .height(height),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (state.buttonClickedForColor == text) White else OrangeBtnColor
        )
    ) {
        Text(
            text = text,
            color = if (state.buttonClickedForColor == text) OrangeBtnColor else White,
            fontSize = textSize.sp,
            fontFamily = FontFamily(Font(R.font.inter_light)),
            fontWeight = FontWeight.Bold
        )
    }
}


// Other buttons
@Composable
fun OtherButton(
    event: (Event) -> Unit,
    text : String,
    height : Dp,
    width : Dp,
    textSize : Int
) {
    TextButton(
        onClick = {

            when(text) {

                "AC" -> event(Event.ClearAll)

                "C" -> event(Event.ClearValue)

                "+/-" -> event(Event.ChangeSignOfValue)

                "%" -> event(Event.CalculatePercent)

            }

        },
        modifier= Modifier
            .background(Transparent)
            .clip(RoundedCornerShape(50.dp))
            .width(width)
            .height(height),
        colors = ButtonDefaults.buttonColors(containerColor = TopBtnColor)
    ) {
        Text(
            text = text,
            color = OnTopBtn,
            fontSize = textSize.sp,
            fontFamily = FontFamily(Font(R.font.inter_light)),
            fontWeight = FontWeight.Bold
        )
    }
}

// Scientific buttons
@Composable
fun ScientificButton(
    event: (Event) -> Unit,
    text : String,
    height : Dp,
    width : Dp
) {
    TextButton(
        onClick = {  },
        modifier= Modifier
            .background(Transparent)
            .clip(RoundedCornerShape(50.dp))
            .width(width)
            .height(height),
        colors = ButtonDefaults.buttonColors(containerColor = SciBtnColor)
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = White,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.inter_light)),
            fontWeight = FontWeight.Bold
        )
    }
}