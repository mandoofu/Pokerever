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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mandoo.pokerever.R
import com.mandoo.pokerever.common.StoreInfo
import com.mandoo.pokerever.map.openNaverMap
import com.mandoo.pokerever.viewmodel.StoreViewModel

@Composable
fun LazyStoreList(searchQuery: String, viewModel: StoreViewModel) {
    val stores by remember { viewModel.storeList }

    // 검색어 필터링
    val filteredStores = stores.filter {
        it.storeName.contains(searchQuery, ignoreCase = true)
    }

    LazyColumn {
        items(items = filteredStores) { item ->
            StoreListItemUI(storeInfo = item, onItemClick = {})
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
        storageReference.downloadUrl.addOnSuccessListener { uri ->
            imageUrl = uri.toString()
            isLoading = false // 이미지 로딩 완료
        }.addOnFailureListener {
            imageUrl = null
            isLoading = false // 이미지 로딩 실패
        }
    }

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
            // 이미지 로딩 상태에 따른 UI 변경
            if (isLoading) {
                // 이미지 로딩 중에는 기본 이미지 표시
                Image(
                    painter = painterResource(id = R.drawable.mainlogo),
                    contentDescription = storeInfo.address,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                // 이미지가 로드된 후에는 AsyncImage 사용
                AsyncImage(
                    model = imageUrl, // Firebase Storage에서 가져온 URL을 모델로 사용
                    contentDescription = storeInfo.address,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Store name, address 등의 정보 표시
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.store_address),
                            fontWeight = FontWeight.SemiBold,
                            style = typography.bodySmall,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(1.dp))
                        Text(
                            text = storeInfo.address,
                            style = typography.bodySmall,
                            color = Color.White
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


