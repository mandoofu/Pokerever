package com.mandoo.pokerever.basic.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mandoo.pokerever.R
import com.mandoo.pokerever.location.LocationProvider
import com.mandoo.pokerever.viewmodel.StoreViewModel
import com.mandoo.pokerever.widget.LazyStoreList

@Composable
fun StoreScreen(navController: NavController) {
    val storeViewModel: StoreViewModel = viewModel()

    // 현재 사용자 ID 가져오기
    val userId = storeViewModel.getUserId() ?: ""
    var userLat by remember { mutableStateOf<Double?>(null) }
    var userLon by remember { mutableStateOf<Double?>(null) }

    // Firestore에서 매장 정보를 로드
    LaunchedEffect(Unit) {
        storeViewModel.loadStores()
    }
    // 현재 위치 가져오기
    val locationProvider = LocationProvider(LocalContext.current)
    locationProvider.getCurrentLocation { location ->
        if (location != null) {
            userLat = location.latitude
            userLon = location.longitude
        } else {
            // 위치를 가져오지 못했을 때 처리
            userLat = null
            userLon = null
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        var storename by remember { mutableStateOf("") }

        // 검색 입력 필드
        OutlinedTextField(
            value = storename,
            onValueChange = { storename = it },
            label = { Text(text = stringResource(R.string.search_store)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
            },
            textStyle = TextStyle(color = Color.White)
        )

        HorizontalDivider(
            color = Color.White,
            thickness = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // 검색어 및 필터링된 매장을 LazyStoreList로 표시
        if (userLat != null && userLon != null) {
            LazyStoreList(
                searchQuery = storename,
                viewModel = storeViewModel,
                userLat = userLat!!,
                userLon = userLon!!
            )
        } else {
            // 위치를 가져오는 동안 대체 UI 표시
            Text(
                text = stringResource(R.string.loading_location),
                color = Color.White,
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
