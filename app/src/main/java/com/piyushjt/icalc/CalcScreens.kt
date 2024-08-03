package com.piyushjt.icalc

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.piyushjt.icalc.ui.theme.Background

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