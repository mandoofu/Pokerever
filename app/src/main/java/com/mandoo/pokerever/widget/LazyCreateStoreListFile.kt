package com.mandoo.pokerever.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.mandoo.pokerever.R
import com.mandoo.pokerever.common.StoreInfo

@Composable
fun LazyCreateStoreList(navController: NavController, stores: List<StoreInfo>) {
    LazyColumn {
        items(stores) { store ->
            CreateStoreListItemUI(
                storeInfo = store,
                onStoreClick = { storeId ->
                    navController.navigate("store_detail_screen/$storeId")
                },
                onLikeClick = { storeId, isLike -> /* 좋아요 기능 구현 */ }
            )
        }
    }

}

@Composable
fun CreateStoreListItemUI(
    storeInfo: StoreInfo,
    onStoreClick: (storeId: String) -> Unit,
    onLikeClick: (storeId: String, isLike: Boolean) -> Unit
) {
    // 좋아요 상태를 로컬에서 관리
    var isLiked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onStoreClick(storeInfo.sid) },
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
            // 매장 이미지
            Image(
                painter = rememberAsyncImagePainter(storeInfo.imageRes),
                contentDescription = storeInfo.address,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 매장 정보 (이름, 주소, 포인트)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                // 매장 이름
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = R.string.store_name),
                        fontWeight = FontWeight.SemiBold,
                        style = typography.bodySmall,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = storeInfo.storeName,
                        style = typography.bodySmall,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // 매장 주소
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = R.string.store_address),
                        fontWeight = FontWeight.SemiBold,
                        style = typography.bodySmall,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = storeInfo.address,
                        style = typography.bodySmall,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // 매장 포인트
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = R.string.point),
                        fontWeight = FontWeight.SemiBold,
                        style = typography.bodySmall,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${storeInfo.points}P", // 포인트 표시
                        style = typography.bodySmall,
                        color = Color.White
                    )
                }
            }

            // 좋아요 버튼
            IconToggleButton(
                checked = isLiked,
                onCheckedChange = {
                    isLiked = !isLiked
                    onLikeClick(storeInfo.sid, isLiked)
                }
            ) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "좋아요 상태",
                    tint = if (isLiked) Color.Red else Color.Gray
                )
            }
        }
    }
}

