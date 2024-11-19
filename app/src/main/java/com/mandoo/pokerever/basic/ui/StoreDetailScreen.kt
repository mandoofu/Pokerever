package com.mandoo.pokerever.basic.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavController
import com.mandoo.pokerever.R
import com.mandoo.pokerever.common.StoreInfo
import com.mandoo.pokerever.utils.sendPoints

@Composable
fun StoreDetailScreen(
    navController: NavController,
    storeInfo: StoreInfo,
    userId: String
) {
    var isDialogVisible by remember { mutableStateOf(false) }
    var pointInput by remember { mutableStateOf("") } // 다이얼로그 입력 포인트
    val isInputValid = pointInput.toIntOrNull()?.let { it in 1..storeInfo.points } ?: false // 입력값 검증

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
                text = storeInfo.storeName, // 매장 이름
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // 구분선
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

        // 포인트 정보 표시
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
                Text(text = "${storeInfo.points}p", style = pointInfoTextStyle) // 보유 포인트 표시
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
        HorizontalDivider(
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

        // 포인트 송신 내역 헤더 (표시용)
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
                                        storeId = storeInfo.sid,
                                        points = pointInput.toInt(),
                                        onSuccess = { /* 성공 처리 */ },
                                        onFailure = { /* 실패 처리 */ }
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
