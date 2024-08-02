package com.piyushjt.icalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.piyushjt.icalc.ui.theme.Background
import com.piyushjt.icalc.ui.theme.NumBtnColor
import com.piyushjt.icalc.ui.theme.OnNum
import com.piyushjt.icalc.ui.theme.OnOrange
import com.piyushjt.icalc.ui.theme.OnTopBtn
import com.piyushjt.icalc.ui.theme.OrangeBtnColor
import com.piyushjt.icalc.ui.theme.TextColor
import com.piyushjt.icalc.ui.theme.TopBtnColor
import com.piyushjt.icalc.ui.theme.Transparent
import kotlinx.coroutines.coroutineScope

class MainActivity : ComponentActivity() {

    // View Model (MVVM)
    private val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // State
            val state by viewModel.state.collectAsState()

            VerticalScreen(
                state = state,
                event = viewModel::event
            )
        }
    }
}





// Vertical Screen
@Composable
fun VerticalScreen(
    state: State,
    event: (Event) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        verticalArrangement = Arrangement.Bottom
    ) {

        // Value text view
        TextValue(
            text = state.valueToShow,
            state = state,
            event = event
        )

        // Buttons in grid layout
        VerticalButtons(event = event, state = state)
    }
}


// Value Text View
@Composable
fun TextValue(
    text: String,
    state: State,
    event: (Event) -> Unit
) {

    var isEventTriggered = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .padding(horizontal = 20.dp, vertical = 20.dp).pointerInput(Unit) {
                coroutineScope {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent(PointerEventPass.Initial)
                            if (event.type == PointerEventType.Move) {
                                if (!isEventTriggered.value) {
                                    event(Event.ClearLastChar)
                                    isEventTriggered.value = true
                                }
                            }
                            if (event.type == PointerEventType.Release) {
                                isEventTriggered.value = false
                            }
                        }
                    }
                }
            },
        contentAlignment = Alignment.BottomEnd
    ) {

        val textToShow = if (text.contains('e')) {

            "${text.substring(0, text.indexOf('e') + 1).replace("e", "x10")}${(text.substringAfter('e'))}"

        } else {
            text
        }



        Text(
            text = buildAnnotatedString {
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
            color = TextColor,
            fontFamily = FontFamily(Font(R.font.inter_light)),
            fontSize = state.textSize.sp
        )
    }
}



// Buttons on grid format
@Composable
fun VerticalButtons(
    event: (Event) -> Unit,
    state: State
) {
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
            OtherButton(event = event, text = if (state.value != "0") "C" else "AC")

            OtherButton(event = event, text = "+/-")
            OtherButton(event = event, text = "%")
            OppButton(state = state, event = event, text = "÷")
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            NumButton(state = state, event = event, text = "7")
            NumButton(state = state, event = event, text = "8")
            NumButton(state = state, event = event, text = "9")
            OppButton(state = state, event = event, text = "×")
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            NumButton(state = state, event = event, text = "4")
            NumButton(state = state, event = event, text = "5")
            NumButton(state = state, event = event, text = "6")
            OppButton(state = state, event = event, text = "-")
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            NumButton(state = state, event = event, text = "1")
            NumButton(state = state, event = event, text = "2")
            NumButton(state = state, event = event, text = "3")
            OppButton(state = state, event = event, text = "+")
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            ZeroButton(state = state, event = event)
            DotButton(state = state, event = event,)
            OppButton(state = state, event = event, text = "=")
        }


    }
}


// To be completed later
@Composable
fun DotButton(
    event: (Event) -> Unit,
    state: State
) {
    Button(
        onClick = {
            if(state.isEqualPressed){
                event(Event.SetDotPressed(true))
                event(Event.SetValue("0."))
                event(Event.SetValueSetAfterOperator(false))
                event(Event.SetEqualPressed(false))
            }
            else {
                if (!state.value.contains(".")) {
                    event(Event.SetDotPressed(true))

                    if (state.buttonClickedForColor != null) {
                        event(Event.SetPreviousValue(state.value))
                        event(Event.SetValue("0."))
                        event(Event.SetValueSetAfterOperator(true))
                    } else {
                        event(Event.AppendValue("."))
                    }
                } else if (state.buttonClickedForColor != null) {
                    event(Event.SetDotPressed(true))
                    event(Event.SetValue("0."))
                    event(Event.SetValueSetAfterOperator(true))
                }
            }
        },
        modifier= Modifier
            .background(Transparent)
            .clip(RoundedCornerShape(50.dp))
            .width((0.21 * (LocalConfiguration.current.screenWidthDp)).dp)
            .aspectRatio(1f),
        colors = ButtonDefaults.buttonColors(containerColor = NumBtnColor)
    ) {
        Text(
            text = ".",
            color = OnNum,
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
    state: State
) {
    Button(
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
            .width((0.21 * (LocalConfiguration.current.screenWidthDp)).dp)
            .aspectRatio(1f),
        colors = ButtonDefaults.buttonColors(containerColor = NumBtnColor)
    ) {
        Text(
            text = text,
            color = OnNum,
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.inter_light)),
            fontWeight = FontWeight.Bold
        )
    }
}


// Number button (zero)
@Composable
fun ZeroButton(
    state: State,
    event: (Event) -> Unit
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
                        event(Event.AppendValue("0")) // Append the new number to the existing value
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
            .width((0.452 * (LocalConfiguration.current.screenWidthDp)).dp)
            .aspectRatio(2.15238f),
        colors = ButtonDefaults.buttonColors(containerColor = NumBtnColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = (0.02 * (LocalConfiguration.current.screenWidthDp)).dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "0",
                color = OnNum,
                fontSize = 32.sp,
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
    state: State
) {
    Button(
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
            .width((0.21 * (LocalConfiguration.current.screenWidthDp)).dp)
            .aspectRatio(1f),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (state.buttonClickedForColor == text) OnOrange else OrangeBtnColor
        )
    ) {
        Text(
            text = text,
            color = if (state.buttonClickedForColor == text) OrangeBtnColor else OnOrange,
            fontSize = 42.sp,
            fontFamily = FontFamily(Font(R.font.inter_light)),
            fontWeight = FontWeight.Bold
        )
    }
}


// Other buttons
@Composable
fun OtherButton(
    event: (Event) -> Unit,
    text : String
) {
    Button(
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
            .width((0.21 * (LocalConfiguration.current.screenWidthDp)).dp)
            .aspectRatio(1f),
        colors = ButtonDefaults.buttonColors(containerColor = TopBtnColor)
    ) {
        Text(
            text = text,
            color = OnTopBtn,
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.inter_light)),
            fontWeight = FontWeight.Bold
        )
    }
}