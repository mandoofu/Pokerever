package com.mandoo.pokerever.basic.ui

import android.widget.ImageButton
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mandoo.pokerever.R

@Preview(showSystemUi = true)
@Composable
fun StoreDetailScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth(),

        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_icon),
                contentDescription = null,
                modifier = Modifier.clickable {}
                    .padding(end = 8.dp)
                    .align(Alignment.CenterStart)
            )
            Text(
                text = "홀스 신림점", //Store name 데이터 rest 추가 부분
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        HorizontalDivider(
            color = Color.White,
            thickness = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        val pointInfoTextStyle = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(text = stringResource(R.string.point), style = pointInfoTextStyle)
            Text(text = ("100"), style = pointInfoTextStyle) //매장 별 고객 포인트 정보 표출)
            Text(text = ("p"), style = pointInfoTextStyle)
        }
        HorizontalDivider(
            color = Color.White,
            thickness = 0.4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        val recevingPointInfoTextStyle = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(72.dp)
        ) {
            Text(text = stringResource(R.string.from_user), style = recevingPointInfoTextStyle)
            Text(text = stringResource(R.string.recipient_user), style = recevingPointInfoTextStyle)
            Text(text = stringResource(R.string.time), style = recevingPointInfoTextStyle)
            Text(text = stringResource(R.string.quantity), style = recevingPointInfoTextStyle)
        }
    }
}
