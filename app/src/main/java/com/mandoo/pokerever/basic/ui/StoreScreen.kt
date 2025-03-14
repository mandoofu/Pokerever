package com.mandoo.pokerever.basic.ui

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mandoo.pokerever.R
import com.mandoo.pokerever.viewmodel.StoreViewModel
import com.mandoo.pokerever.widget.LazyStoreList

@Composable
fun StoreScreen(navController: NavController) {
    val storeViewModel: StoreViewModel = viewModel()
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var userLat by remember { mutableStateOf(0.0) }
    var userLon by remember { mutableStateOf(0.0) }

    // 사용자 위치 가져오기
    LaunchedEffect(Unit) {
        getLastKnownLocation(fusedLocationClient) { lat, lon ->
            userLat = lat
            userLon = lon
            storeViewModel.initializeData(userLat, userLon)
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
        if (userLat != 0.0 && userLon != 0.0) {
            LazyStoreList(
                searchQuery = storename,
                viewModel = storeViewModel,
                userLat = userLat,
                userLon = userLon
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

// 사용자 위치 가져오는 함수
@SuppressLint("MissingPermission")
fun getLastKnownLocation(fusedLocationClient: FusedLocationProviderClient, onLocationReceived: (Double, Double) -> Unit) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) {
            onLocationReceived(location.latitude, location.longitude)
        }
    }.addOnFailureListener {
        onLocationReceived(0.0, 0.0) // 기본값 설정
    }
}
