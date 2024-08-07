package com.piyushjt.icalc

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.piyushjt.icalc.ui.theme.Black
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
        Black.toArgb().also { window.navigationBarColor = it }


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


// Value Text View
@SuppressLint("ReturnFromAwaitPointerEventScope")
@Composable
fun TextValue(
    state: State,
    event: (Event) -> Unit,
    height: Dp,
    textSize: Int?
) {
    // State of event trigger
    val isEventTriggered = remember { mutableStateOf(false) }

    // Container for text value
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 4.dp)
            .fillMaxWidth()
            .background(Black)
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
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        val textToShow = if (state.valueToShow.contains('e')) {

            "${state.valueToShow
                .substring(0, state.valueToShow.indexOf('e') + 1)
                .replace("e", "x10")}${(state.valueToShow.substringAfter('e'))}"

        } else {
            state.valueToShow
        }

        if(textSize == null) {
            Text(
                text = if (state.angleUnitDeg) "Deg" else "Rad",
                color = White,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
        }
        else {
            Box(modifier = Modifier.height(1.dp).width(1.dp).background(Transparent))
        }

        // Text Value
        Text(
            text = buildAnnotatedString {

                // Showing power in superscript
                if (textToShow.contains("x10")) {

                    append(textToShow.substring(0, textToShow.indexOf("x10") + 3))

                    withStyle(
                        style = SpanStyle(
                            fontSize = if(textSize == null) 20.sp else 24.sp,
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
            textAlign = TextAlign.End,
            color = White,
            fontFamily = FontFamily(Font(R.font.inter_light)),
            fontSize = (textSize?: 38).sp
        )
    }
}