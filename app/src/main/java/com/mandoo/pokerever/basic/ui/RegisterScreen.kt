package com.mandoo.pokerever.basic.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.mandoo.pokerever.R

@Preview(showSystemUi = true)
@Composable
fun RegisterScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(40.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start)
    {
        Text(
            text = stringResource(R.string.member_insert),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally) // 중앙 정렬
        )

        var name by remember { mutableStateOf("") }
        OutlinedTextField(
            value = name,
            onValueChange = { newText -> name = newText },
            label = { Text(text = stringResource(R.string.name)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        var registration by remember { mutableStateOf("") }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = registration,
                onValueChange = { newText -> registration = newText },
                label = { Text(text = stringResource(R.string.registration)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            OutlinedButton(
                onClick = { /* 중복 확인 로직 추가 */ },
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp)
                    .height(40.dp), // TextField 높이와 동일하게 설정
            ) {
                Text(text = stringResource(R.string.identity_verification), fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
        var nickname by remember { mutableStateOf("") }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = nickname,
                onValueChange = { newText -> nickname = newText },
                label = { Text(text = stringResource(R.string.nick_name)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            OutlinedButton(
                onClick = { /* 중복 확인 로직 추가 */ },
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp)
                    .height(40.dp), // TextField 높이와 동일하게 설정
            ) {
                Text(text = stringResource(R.string.duplicate_check), fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
        var email by remember { mutableStateOf("") }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { newText -> email = newText },
                label = { Text(text = stringResource(R.string.email)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            OutlinedButton(
                onClick = { /* 중복 확인 로직 추가 */ },
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp)
                    .height(40.dp), // TextField 높이와 동일하게 설정
            ) {
                Text(text = stringResource(R.string.duplicate_check), fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
        var password by remember { mutableStateOf("") }
        OutlinedTextField(
            value = password,
            onValueChange = { newText -> password = newText },
            label = { Text(text = stringResource(R.string.password)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        var passwordcheck by remember { mutableStateOf("") }
        OutlinedTextField(
            value = passwordcheck,
            onValueChange = { newText -> passwordcheck = newText },
            label = { Text(text = stringResource(R.string.password_check)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = { },
                Modifier
                    .width(108.dp)
                    .height(40.dp) ,
                    colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(text = stringResource(R.string.member_create), fontWeight = FontWeight.Bold)
            }
                Text(
                    text = stringResource(R.string.or),
                    color = Color.Gray
                )

                Text(text = stringResource(R.string.return_login),
                    color = Color.Gray, modifier = Modifier.clickable { })
            }
        }

    }
}