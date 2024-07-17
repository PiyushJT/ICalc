package com.piyushjt.icalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VerticalScreen()
        }
    }
}






@Composable
fun VerticalScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        verticalArrangement = Arrangement.Bottom
    ) {
        TextValue()
        Buttons()
    }
}


@Composable
fun TextValue(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .padding(horizontal = 20.dp, vertical = 20.dp), contentAlignment = Alignment.BottomEnd
    ) {
        Text(
            text = "12,34,56,789",
            color = TextColor,
            fontFamily = FontFamily(Font(R.font.inter_light)),
            fontSize = 52.sp,
            modifier = Modifier.layoutId("number")
        )
    }
}


@Composable
fun Buttons(
    modifier: Modifier = Modifier
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
            OtherButton(text = "AC",)
            OtherButton(text = "+/-",)
            OtherButton(text = "%",)
            OppButton(text = "÷")
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
            .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            NumButton(text = "7",)
            NumButton(text = "8",)
            NumButton(text = "9",)
            OppButton(text = "×")
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
            .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            NumButton(text = "4",)
            NumButton(text = "5",)
            NumButton(text = "6",)
            OppButton(text = "-")
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
            .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            NumButton(text = "1",)
            NumButton(text = "2",)
            NumButton(text = "3",)
            OppButton(text = "+")
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
            .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            ZeroButton()
            NumButton(text = ".",)
            OppButton(text = "=")
        }


    }
}



@Composable
fun NumButton(
    modifier: Modifier = Modifier,
    text : String
) {
    Button(
        onClick = { /*TODO*/ },
        modifier= Modifier
            .background(Transparent)
            .clip(RoundedCornerShape(50.dp))
            .width((0.21*(LocalConfiguration.current.screenWidthDp)).dp)
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



@Composable
fun ZeroButton(
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .background(Color.Transparent)
            .clip(RoundedCornerShape(50.dp))
            .width((0.452*(LocalConfiguration.current.screenWidthDp)).dp)
            .aspectRatio(2.15238f),
        colors = ButtonDefaults.buttonColors(containerColor = NumBtnColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = (0.02*(LocalConfiguration.current.screenWidthDp)).dp),
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


@Composable
fun OppButton(
    modifier: Modifier = Modifier,
    text : String
) {
    Button(
        onClick = { /*TODO*/ },
        modifier= Modifier
            .background(Transparent)
            .clip(RoundedCornerShape(50.dp))
            .width((0.21*(LocalConfiguration.current.screenWidthDp)).dp)
            .aspectRatio(1f),
        colors = ButtonDefaults.buttonColors(containerColor = OrangeBtnColor)
    ) {
        Text(
            text = text,
            color = OnOrange,
            fontSize = 42.sp,
            fontFamily = FontFamily(Font(R.font.inter_light)),
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun OtherButton(
    modifier: Modifier = Modifier,
    text : String
) {
    Button(
        onClick = { /*TODO*/ },
        modifier= Modifier
            .background(Transparent)
            .clip(RoundedCornerShape(50.dp))
            .width((0.21*(LocalConfiguration.current.screenWidthDp)).dp)
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


@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    VerticalScreen()
}