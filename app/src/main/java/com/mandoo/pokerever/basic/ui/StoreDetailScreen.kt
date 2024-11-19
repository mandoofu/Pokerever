package com.mandoo.pokerever.basic.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mandoo.pokerever.R
import com.mandoo.pokerever.utils.sendPoints
import com.mandoo.pokerever.viewmodel.StoreDetailScreenViewModel

@Composable
fun StoreDetailScreen(
    navController: NavController,
    storeId: String,
    userId: String,
    viewModel: StoreDetailScreenViewModel = viewModel()
) {
    var isDialogVisible by remember { mutableStateOf(false) }
    var pointInput by remember { mutableStateOf("") }

    val storeName by viewModel.storeName
    val userPoints by viewModel.userPoints

    // Firestore 데이터 로드
    LaunchedEffect(storeId, userId) {
        viewModel.loadStoreAndUserPoints(storeId, userId)
    }

    val isInputValid = pointInput.toIntOrNull()?.let { it in 1..userPoints } ?: false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // 상단 매장 정보 헤더
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_icon),
                contentDescription = null,
                modifier = Modifier
                    .clickable { navController.popBackStack() }
                    .padding(end = 8.dp)
                    .align(Alignment.CenterStart)
            )
            Text(
                text = if (storeName.isNotEmpty()) storeName else "Loading...", // 매장 이름
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // 구분선
        Divider(
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

        // 사용자 포인트 표시
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.user_point), style = pointInfoTextStyle)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "$userPoints p", style = pointInfoTextStyle) // 사용자 포인트 표시
            }
            Image(
                painter = painterResource(id = R.drawable.user_point_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { isDialogVisible = true } // 다이얼로그 표시
            )
        }

        // 구분선
        Divider(
            color = Color.White,
            thickness = 0.4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        val receivingPointInfoTextStyle = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )

        // 포인트 송신 내역 헤더
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(68.dp)
        ) {
            Text(text = stringResource(R.string.from_user), style = receivingPointInfoTextStyle)
            Text(text = stringResource(R.string.recipient_user), style = receivingPointInfoTextStyle)
            Text(text = stringResource(R.string.time), style = receivingPointInfoTextStyle)
            Text(text = stringResource(R.string.quantity), style = receivingPointInfoTextStyle)
        }

        // 포인트 전송 다이얼로그
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
                        // 다이얼로그 제목
                        Text(
                            text = stringResource(R.string.send_points),
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // 포인트 입력 필드
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.user_point_icon),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                colorFilter = ColorFilter.tint(Color.Black)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Box(
                                modifier = Modifier
                                    .width(140.dp)
                                    .height(40.dp)
                                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                BasicTextField(
                                    value = pointInput,
                                    onValueChange = { pointInput = it },
                                    textStyle = TextStyle(
                                        fontSize = 16.sp,
                                        color = Color.Gray
                                    ),
                                    singleLine = true,
                                    decorationBox = { innerTextField ->
                                        if (pointInput.isEmpty()) {
                                            Text(
                                                text = "10p",
                                                style = TextStyle(
                                                    color = Color.LightGray,
                                                    fontSize = 16.sp
                                                )
                                            )
                                        }
                                        innerTextField()
                                    },
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 10.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // 다이얼로그 버튼
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.cancel),
                                color = Color.Gray,
                                modifier = Modifier.clickable { isDialogVisible = false }
                            )
                            Text(
                                text = stringResource(R.string.send),
                                color = if (isInputValid) Color.Blue else Color.Gray,
                                modifier = Modifier.clickable(enabled = isInputValid) {
                                    isDialogVisible = false
                                    sendPoints(
                                        userId = userId,
                                        storeId = storeId,
                                        points = pointInput.toInt(),
                                        onSuccess = {
                                            // 성공적으로 포인트를 전송한 후 처리
                                            viewModel.loadStoreAndUserPoints(storeId, userId) // 업데이트된 데이터 다시 로드
                                        },
                                        onFailure = { errorMessage ->
                                            // 실패 메시지 표시 (예: Toast)
                                            println("포인트 전송 실패: $errorMessage")
                                        }
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
