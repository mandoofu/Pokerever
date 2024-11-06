package com.mandoo.pokerever.basic.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mandoo.pokerever.R

@Preview(showSystemUi = true)
@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(60.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = painterResource(id = R.drawable.mainlogo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(300.dp)
                .padding(bottom = 12.dp)
        )
    }
}