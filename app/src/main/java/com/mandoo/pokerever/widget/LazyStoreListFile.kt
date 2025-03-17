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
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mandoo.pokerever.R
import com.mandoo.pokerever.common.StoreInfo
import com.mandoo.pokerever.map.openNaverMap
import com.mandoo.pokerever.utils.FirebaseStorageCache
import com.mandoo.pokerever.viewmodel.StoreViewModel
import java.util.Locale

@Composable
fun LazyStoreList(
    searchQuery: String,
    viewModel: StoreViewModel,
    userLat: Double,
    userLon: Double
) {
    val stores = viewModel.storeList
    val userAddedStores = viewModel.userAddedStores

    // 유저가 추가하지 않은 매장 필터링
    val filteredStores = stores.filter { store ->
        userAddedStores.none { it == store.sid } &&
                store.storeName.contains(searchQuery, ignoreCase = true)
    }
        .sortedBy { it.distance } // 거리순 정렬 추가
    LazyColumn {
        items(filteredStores) { store ->
            // 거리 계산 수행
            val distance = store.geoPoint?.let {
                viewModel.calculateDistance(userLat, userLon, it.latitude, it.longitude)
            } ?: Double.MAX_VALUE // 거리 계산 실패 시 큰 값

            // 거리 값을 포함한 StoreInfo 객체 생성
            val updatedStoreInfo = store.copy(distance = distance)

            // 매장 정보 UI 표시
            StoreListItemUI(
                storeInfo = updatedStoreInfo,
                onItemClick = {
                    val userId = viewModel.getUserId() ?: return@StoreListItemUI
                    viewModel.addStoreForUser(userId, updatedStoreInfo) {
                        // 추가 후 데이터 동기화
                        viewModel.loadStores(userLat, userLon)
                        viewModel.loadUserAddedStores(userId)
                    }
                }
            )
        }
    }
}


@Composable
fun StoreListItemUI(storeInfo: StoreInfo, onItemClick: (StoreInfo) -> Unit) {
    var isDialogVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val storageReference: StorageReference =
        FirebaseStorage.getInstance().getReference(storeInfo.imageRes)
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // 비동기적으로 Firebase에서 이미지 URL을 가져오기
    LaunchedEffect(storeInfo.imageRes) {
        FirebaseStorageCache.getCachedUrl(
            reference = storageReference,
            onSuccess = {
                imageUrl = it
                isLoading = false
            },
            onFailure = {
                imageUrl = null
                isLoading = false
            }
        )
    }
    fun formatDistance(distanceInMeters: Double): String {
        return if (distanceInMeters >= 1000) {
            String.format(Locale.US, "%.1f km", distanceInMeters / 1000)
        } else {
            "${distanceInMeters.toInt()} m"
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { isDialogVisible = true },
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
            // 이미지 로딩 상태에 따른 UI 변경
            if (isLoading) {
                Image(
                    painter = painterResource(id = R.drawable.mainlogo),
                    contentDescription = storeInfo.address,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = storeInfo.address,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

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
                    Spacer(modifier = Modifier.width(1.dp))
                    Text(
                        text = storeInfo.storeName,
                        style = typography.bodySmall,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.store_address),
                            fontWeight = FontWeight.SemiBold,
                            style = typography.bodySmall,
                            color = Color.White,
                        )
                        Spacer(modifier = Modifier.width(1.dp))
                        Text(
                            text = storeInfo.address,
                            style = typography.bodySmall,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.address_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
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
                    Spacer(modifier = Modifier.width(1.dp))
                    Text(
                        text = formatDistance(storeInfo.distance), // 미터 단위로 표시
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
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = storeInfo.storeName,
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = storeInfo.storeName,
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
                                            .height(40.dp)
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
