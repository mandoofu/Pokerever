package com.mandoo.pokerever.basic.ui

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mandoo.pokerever.R

@Composable
fun InfoScreen(navController: NavController) {
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
                text = stringResource(R.string.my_info),
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
        var name by remember { mutableStateOf("") }
        OutlinedTextField(
            value = name,
            onValueChange = { },
            label = { Text(text = stringResource(R.string.name)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.Gray,
                disabledLabelColor = Color.Gray
            )
        )
        var nickname by remember { mutableStateOf("") }
        OutlinedTextField(
            value = nickname,
            onValueChange = { },
            label = { Text(text = stringResource(R.string.nick_name)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.Gray,
                disabledLabelColor = Color.Gray
            )
        )
        var datebirth by remember { mutableStateOf("") }
        OutlinedTextField(
            value = datebirth,
            onValueChange = { },
            label = { Text(text = stringResource(R.string.date_of_birth)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.Gray,
                disabledLabelColor = Color.Gray
            )
        )
        var password by remember { mutableStateOf("") }
        OutlinedTextField(
            value = password,
            onValueChange = { newText -> password = newText },
            label = { Text(text = stringResource(R.string.password)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                unfocusedLabelColor = Color.LightGray
            )
        )
        var passwordcheck by remember { mutableStateOf("") }
        OutlinedTextField(
            value = passwordcheck,
            onValueChange = { newText -> passwordcheck = newText },
            label = { Text(text = stringResource(R.string.password_check)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                unfocusedLabelColor = Color.LightGray
            )
        )
        Spacer(modifier = Modifier.weight(1f)) // 남은 공간을 차지하게 하여 버튼을 아래로 밀어내기


        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.logout),
                    color = Color.Gray,
                    modifier = Modifier
                        .clickable { }
                )
                Text(
                    text = " | ", // 구분선 텍스트
                    color = Color.Gray
                )
                Text(
                    text = stringResource(R.string.user_delete),
                    color = Color.Gray,
                    modifier = Modifier
                        .clickable { }
                )
            }

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(text = stringResource(R.string.data_road), fontWeight = FontWeight.Bold)
            }
        }
    }
}
