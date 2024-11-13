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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mandoo.pokerever.R
import com.mandoo.pokerever.viewmodel.StoreViewModel
import com.mandoo.pokerever.widget.LazyStoreList

@Composable
fun StoreScreen(navController: NavController) {
    val storeViewModel: StoreViewModel = viewModel()

    // 화면이 시작될 때 Firestore 데이터를 불러옵니다.
    LaunchedEffect(Unit) {
        storeViewModel.loadStores()
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
        OutlinedTextField(
            value = storename,
            onValueChange = { newText -> storename = newText },
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
                    modifier = Modifier.padding(end = 8.dp) // 아이콘과 텍스트 간 간격 조정
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

        // 검색어를 포함하여 StoreList를 불러옵니다.
        LazyStoreList(searchQuery = storename, viewModel = storeViewModel)
    }
}


