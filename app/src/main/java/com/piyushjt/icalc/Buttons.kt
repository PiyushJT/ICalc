package com.piyushjt.icalc


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.piyushjt.icalc.ui.theme.NumBtnColor
import com.piyushjt.icalc.ui.theme.OnTopBtn
import com.piyushjt.icalc.ui.theme.OrangeBtnColor
import com.piyushjt.icalc.ui.theme.SciBtnColor
import com.piyushjt.icalc.ui.theme.TopBtnColor
import com.piyushjt.icalc.ui.theme.Transparent
import com.piyushjt.icalc.ui.theme.White
import kotlin.math.E
import kotlin.math.PI


// Dot Button
@Composable
fun DotButton(
    event: (Event) -> Unit,
    state: State,
    height : Dp,
    width : Dp,
    textSize : Int
) {
    TextButton(
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
            fontSize = textSize.sp,
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
                        0.dp
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
    state: State,
    text : String,
    height : Dp,
    width : Dp
) {
    TextButton(

        onClick = {

            val trigs = listOf("sin", "cos", "tan", "sinh", "cosh", "tanh")

            when(text) {

                "¹⁄ₓ" -> event(Event.SetReciprocal)

                "x!" -> event(Event.SetFactorial)

                "π" -> event(Event.SetValue(PI.toString()))

                "e" -> event(Event.SetValue(E.toString()))

                "x²" -> event(Event.SetPower(state.value.toDouble(), 2.0))

                "x³" -> event(Event.SetPower(state.value.toDouble(), 3.0))

                "eˣ" -> event(Event.SetPower(E, state.value.toDouble()))

                "10ˣ" -> event(Event.SetPower(10.0, state.value.toDouble()))

                "2ˣ" -> event(Event.SetPower(2.0, state.value.toDouble()))

                in trigs -> event(Event.SetTrig(text))

                "!" -> event(Event.SetPower(state.value.toDouble(), 1/2.0))

                "\"" -> event(Event.SetPower(state.value.toDouble(), 1/3.0))

                "log₁₀" -> event(Event.SetLog(base = 10.0, value = state.value.toDouble()))

               "log₂" -> event(Event.SetLog(base = 2.0, value = state.value.toDouble()))

                "ln" -> event(Event.SetLog(base = E, value = state.value.toDouble()))
            }

        },

        modifier = Modifier
            .background(Transparent)
            .clip(RoundedCornerShape(50.dp))
            .width(width)
            .height(height),
        colors = ButtonDefaults.buttonColors(containerColor = SciBtnColor)
    ) {

        val isRootButton = text in listOf("!", "\"", "'")

        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = White,

            // Changing font if it is a root button
            fontSize = if (isRootButton) 24.sp else 14.sp,
            fontFamily = if (isRootButton) FontFamily(Font(R.font.roots_logos_as_font)) else FontFamily(Font(R.font.inter_light)),
            fontWeight = if (isRootButton) FontWeight.Light else FontWeight.Bold
        )
    }
}