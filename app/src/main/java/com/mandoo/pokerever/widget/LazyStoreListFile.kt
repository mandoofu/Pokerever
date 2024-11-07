package com.mandoo.pokerever.widget

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mandoo.pokerever.R
import com.mandoo.pokerever.common.StoreInfo
import com.mandoo.pokerever.common.StoreInit

@Preview(showSystemUi = true)
@Composable
fun LazyStoreList() {
    val composeCtx = LocalContext.current
    val onGroupItemClick = { storeInfo: StoreInfo ->
        Toast.makeText(
            composeCtx,
            "${storeInfo.address} 입니다!",
            Toast.LENGTH_SHORT
        ).show()
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(4.dp))
        LazyColumn(
            userScrollEnabled = true //Default
        ) {
            items(StoreInit.shuffleStoreInfoList()) { item ->
                StoreListItemUI(storeInfo = item, onItemClick = onGroupItemClick)
            }
        }
    }
}

@Composable
fun StoreListItemUI(storeInfo: StoreInfo, onItemClick: (StoreInfo) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onItemClick(storeInfo)
            },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 16.dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            /**
             * Network 로 이미지 로딩 시에는 Coil Compose 의 AsyncImage 를 사용
             */
            Image(
                modifier = Modifier
                    .size(80.dp, 80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = storeInfo.imageRes),
                contentDescription = storeInfo.address,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                Row(
                    modifier = Modifier.padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = storeInfo.storeName,
                        fontWeight = FontWeight.SemiBold,
                        style = typography.bodySmall,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(1.dp)) // 텍스트 간의 간격
                    Text(
                        text = storeInfo.storeNameRes,
                        style = typography.bodySmall,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth() // Row의 전체 너비를 채워 요소를 양쪽 끝에 배치
                        .padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween // 양쪽 끝에 요소 배치
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = storeInfo.address,
                            fontWeight = FontWeight.SemiBold,
                            style = typography.bodySmall,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(1.dp)) // 텍스트 간의 간격
                        Text(
                            text = storeInfo.addressRes,
                            style = typography.bodySmall,
                            color = Color.White
                        )
                    }
                    // 아이콘 추가
                    Image(
                        painter = painterResource(id = R.drawable.address_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp) // 아이콘 크기 설정
                            .clickable { }
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = storeInfo.distance,
                        fontWeight = FontWeight.SemiBold,
                        style = typography.bodySmall,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(1.dp)) // 텍스트 간의 간격
                    Text(
                        text = storeInfo.distanceRes,
                        style = typography.bodySmall,
                        color = Color.White
                    )
                }
            }
        }
    }
}

