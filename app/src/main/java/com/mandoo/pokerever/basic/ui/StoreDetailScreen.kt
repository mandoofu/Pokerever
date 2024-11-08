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
import com.mandoo.pokerever.common.CreateStoreInfo


@Composable
fun StoreDetailScreen(navController: NavController, sroreInfo: CreateStoreInfo) {
    var isDialogVisible by remember { mutableStateOf(false) }
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
                modifier = Modifier
                    .clickable { navController.popBackStack() }
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
            modifier = Modifier
                .fillMaxWidth() // Row의 전체 너비를 채워 요소를 양쪽 끝에 배치
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // 양쪽 끝에 요소 배치
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.point), style = pointInfoTextStyle)
                Spacer(modifier = Modifier.width(1.dp)) // 텍스트 간의 간격
                Text(text = ("100"), style = pointInfoTextStyle) //매장 별 고객 포인트 정보 표출)
                Text(text = ("p"), style = pointInfoTextStyle)
            }
            // 아이콘 추가
            Image(
                painter = painterResource(id = R.drawable.user_point_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp) // 아이콘 크기 설정
                    .clickable { isDialogVisible = true }
            )
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

// 다이얼로그 표시
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
                        // 상단 제목과 포인트 정보
                        Text(
                            text = "포인트 전송",
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            // 포인트 아이콘
                            Image(
                                painter = painterResource(id = R.drawable.user_point_icon),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                colorFilter = ColorFilter.tint(Color.Black)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            // 포인트 입력 필드
                            var pointInput by remember { mutableStateOf("0") } // 초기값 설정

                            Box(
                                modifier = Modifier
                                    .width(140.dp)
                                    .height(40.dp)
                                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)) // 외곽선 추가
                                    .padding(horizontal = 4.dp, vertical = 2.dp) // 기본 패딩 최소화
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
                                                "10p",
                                                style = TextStyle(
                                                    color = Color.LightGray,
                                                    fontSize = 16.sp
                                                )
                                            )
                                        }
                                        innerTextField() // 실제 입력란
                                    },
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 10.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // 버튼 행
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray)
                        ) {
                            // "취소" 버튼
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { isDialogVisible = false }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "취소", color = Color.Black)
                            }

                            // 구분선
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(40.dp) // 구분선의 높이 설정
                                    .background(Color.Gray)
                            )

                            // "전송" 버튼
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        // 전송 버튼 클릭 시 동작 추가 가능
                                        isDialogVisible = false
                                    }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "전송", color = Color.Black)
                            }
                        }
                    }
                }
            }
        }
    }
}
