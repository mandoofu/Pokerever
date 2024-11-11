package com.mandoo.pokerever.widget

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mandoo.pokerever.R
import com.mandoo.pokerever.common.CreateStoreInfo
import com.mandoo.pokerever.common.CreateStoreInit
import okhttp3.internal.toImmutableList

@Composable
fun LazyCreateStoreList(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(4.dp))
        LazyColumn(
            userScrollEnabled = true //Default
        ) {
            items(CreateStoreInit.sortCreateStoreInfoList().toImmutableList()) { item ->
                CreateStoreListItemUI(
                    storeInfo = item,
                    onStoreClick = { storeId -> navController.navigate("store_detail_screen/$storeId") },
                    onLikeClick = { storeId, isLike ->
                        CreateStoreInit.updateStoreLikeStatus(
                            storeId,
                            isLike
                        )
                    }

                )

            }
        }
    }
}

@Composable
fun CreateStoreListItemUI(
    storeInfo: CreateStoreInfo,
    onStoreClick: (storeId: String) -> Unit,
    onLikeClick: (storeId: String, isLike: Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onStoreClick(storeInfo.id)
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
                contentDescription = storeInfo.address
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
                    modifier = Modifier.padding(end = 16.dp),
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
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.point),
                        fontWeight = FontWeight.SemiBold,
                        style = typography.bodySmall,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(1.dp)) // 텍스트 간의 간격
                    Text(
                        text = storeInfo.point,
                        style = typography.bodySmall,
                        color = Color.White
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                var isMemberLike by remember { mutableStateOf(storeInfo.isLike) }
                IconToggleButton(
                    checked = isMemberLike,
                    onCheckedChange = {
                        isMemberLike = !isMemberLike
                        onLikeClick(storeInfo.id, isMemberLike)
                    }
                ) {
                    Icon(
                        tint = Color.Red,
                        modifier = Modifier.graphicsLayer {
                            scaleX = 1f
                            scaleY = 1f
                        },
                        imageVector = if (isMemberLike) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = "좋아요! 유무"
                    )
                }
            }
        }
    }
}