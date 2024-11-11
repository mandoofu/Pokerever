package com.mandoo.pokerever.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mandoo.pokerever.R
import com.mandoo.pokerever.common.StoreInfo
import com.mandoo.pokerever.common.StoreInit
import com.mandoo.pokerever.map.openNaverMap
import okhttp3.internal.toImmutableList

@Composable
fun LazyStoreList(searchQuery: String) {
    val filteredStores = StoreInit.sortCreateStoreInfoList().filter {
        it.storeName.contains(searchQuery, ignoreCase = true)
    }
    LazyColumn {
        items(items = filteredStores) { item ->
            StoreListItemUI(storeInfo = item, onItemClick = {})
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(4.dp))
        LazyColumn(
            userScrollEnabled = true //Default
        ) {
            items(
                items = StoreInit.sortCreateStoreInfoList().toImmutableList(),
//                key = { it.storeName }
            ) { item ->
                StoreListItemUI(storeInfo = item, onItemClick = {})
            }
        }
    }
}

@Composable
fun StoreListItemUI(storeInfo: StoreInfo, onItemClick: (StoreInfo) -> Unit) {
    var isDialogVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                isDialogVisible = true
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
                        text = stringResource(id = R.string.store_name),
                        fontWeight = FontWeight.SemiBold,
                        style = typography.bodySmall,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(1.dp)) // 텍스트 간의 간격
                    Text(
                        text = storeInfo.storeName,
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
                            text = stringResource(id = R.string.store_address),
                            fontWeight = FontWeight.SemiBold,
                            style = typography.bodySmall,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(1.dp)) // 텍스트 간의 간격
                        Text(
                            text = storeInfo.address,
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
                            .clickable {
                                openNaverMap(context, storeInfo.address)
                            }
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.store_distance),
                        fontWeight = FontWeight.SemiBold,
                        style = typography.bodySmall,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(1.dp)) // 텍스트 간의 간격
                    Text(
                        text = "${storeInfo.distance}M",
                        style = typography.bodySmall,
                        color = Color.White
                    )
                }
                if (isDialogVisible) {
                    Dialog(onDismissRequest = { isDialogVisible = false }) {
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(Color.White)
                                    .padding(16.dp)
                                    .width(250.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Image(
                                        painter = painterResource(storeInfo.imageRes), // 매장 이미지 리소스 사용
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = storeInfo.address,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                        Text(text = "추가 하시겠습니까?", color = Color.Black)
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.LightGray)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable { isDialogVisible = false }
                                            .padding(vertical = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "취소", color = Color.Black)
                                    }
                                    Box(
                                        modifier = Modifier
                                            .width(2.dp)
                                            .height(40.dp) // 구분선의 높이 조절
                                            .background(Color.Gray)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable {
                                                onItemClick(storeInfo)
                                                isDialogVisible = false
                                            }
                                            .padding(vertical = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "추가", color = Color.Black)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

