package com.mandoo.pokerever.basic.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mandoo.pokerever.R
import com.mandoo.pokerever.viewmodel.HomeViewModel
import com.mandoo.pokerever.widget.LazyCreateStoreList

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = viewModel()
    val userInfoState = viewModel.userInfoState.value
    val userAddedStores by viewModel.userAddedStores

    // 사용자 ID를 가져오고, 추가한 매장을 로드
    LaunchedEffect(Unit) {
        val userId = viewModel.getUserId() ?: return@LaunchedEffect
        viewModel.fetchUserInfo()
        viewModel.fetchUserAddedStores(userId)
    }

    // 로딩 중 UI 표시
    if (userInfoState.isLoading) {
        SplashScreen() // 로딩 중 화면
    } else {
        // 사용자 정보와 추가한 매장을 표시
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // 사용자 정보 표시
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = userInfoState.name ?: "이름 없음",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text(
                    text = "(${userInfoState.nickname})",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Text(
                    text = stringResource(R.string.welcome_user),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            HorizontalDivider(
                color = Color.White,
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            // 추가된 매장 목록 표시
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.create_store),
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }

            // LazyCreateStoreList로 사용자 추가 매장 표시
            LazyCreateStoreList(navController = navController, stores = userAddedStores)
        }
    }
}
