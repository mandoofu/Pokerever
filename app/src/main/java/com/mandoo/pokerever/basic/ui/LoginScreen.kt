package com.mandoo.pokerever.basic.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mandoo.pokerever.R
import com.mandoo.pokerever.viewmodel.LoginViewModel


@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = viewModel()
    var errorMessage by remember { mutableStateOf<String?>(null) } // 오류 메시지를 저장할 상태

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(60.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = painterResource(id = R.drawable.mainlogo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(300.dp)
                .padding(bottom = 12.dp)
        )
        var email by remember { mutableStateOf("") }
        TextField(
            value = email,
            onValueChange = { newText ->
                email = newText
            },
            label = {
                Text(text = stringResource(R.string.email))
            },
            placeholder = {
                Text(text = stringResource(R.string.sample_email))
            },
            colors = TextFieldDefaults.colors(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
        )

        var pass by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }

        TextField(
            value = pass,
            onValueChange = { newText ->
                pass = newText
            },
            label = {
                Text(text = stringResource(R.string.password))
            },
            placeholder = {
                Text(text = stringResource(R.string.password_input))
            },
            colors = TextFieldDefaults.colors(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                IconButton(onClick = {
                    passwordVisible = !passwordVisible
                }) {
                    Icon(imageVector = image, contentDescription = "")
                }
            },
        )
        // 오류 메시지 표시
        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Row(
//            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(R.string.auto_login),
                color = Color.Gray
            )
            var switchState by remember {
                mutableStateOf(false)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Switch(checked = switchState, onCheckedChange = {
                switchState = it
            },
                thumbContent = if (switchState) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                } else {
                    null
                }, colors = SwitchDefaults.colors(
                    checkedIconColor = Color.Green,
                    checkedTrackColor = Color.Gray
                ))
        }
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.no_account_yet),
                color = Color.Gray,
                modifier = Modifier.clickable {
                    Log.d("LoginScreen", "회원이 아니신가요 클릭됨")
                    navController.navigate("register")
                })
        }
        Button(
            onClick = {
                if (email.isBlank() || pass.isBlank()) {
                    errorMessage = "이메일과 비밀번호를 모두 입력해주세요." // 오류 메시지 표시
                } else {
                    viewModel.loginUser(email, pass) { success ->
                        if (success) {
                            navController.navigate("bottom_tab")
                        } else {
                            errorMessage = "로그인 실패: 잘못된 이메일 또는 비밀번호" // 실패 시 오류 메시지 설정
                        }
                    }
                }
            },
            Modifier
                .width(108.dp)
                .height(40.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text(text = stringResource(R.string.login), fontWeight = FontWeight.Bold)
        }
    }
}