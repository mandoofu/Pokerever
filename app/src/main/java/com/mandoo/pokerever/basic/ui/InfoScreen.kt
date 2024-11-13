package com.mandoo.pokerever.basic.ui

import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mandoo.pokerever.R
import com.mandoo.pokerever.tab.ScreenRouteDef
import com.mandoo.pokerever.utils.ValidationUtil
import com.mandoo.pokerever.viewmodel.HomeViewModel

@Composable
fun InfoScreen(navController: NavController) {
    val viewModel: HomeViewModel = viewModel()
    val userInfoState = viewModel.userInfoState.value

    LaunchedEffect(true) {
        viewModel.fetchUserInfo()
    }

    val name = userInfoState.name ?: "이름 없음"
    val nickname = userInfoState.nickname ?: "닉네임 없음"
    val dateOfBirth = userInfoState.frontNumber ?: "생년월일 없음"
    var password by remember { mutableStateOf("") }
    var passwordCheck by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

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
        OutlinedTextField(
            value = name,
            onValueChange = { },
            label = { Text(text = stringResource(R.string.name)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            textStyle = TextStyle(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.Gray,
                disabledLabelColor = Color.Gray
            )
        )
        OutlinedTextField(
            value = nickname,
            onValueChange = { },
            label = { Text(text = stringResource(R.string.nick_name)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            textStyle = TextStyle(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.Gray,
                disabledLabelColor = Color.Gray
            )
        )
        OutlinedTextField(
            value = dateOfBirth,
            onValueChange = { },
            label = { Text(text = stringResource(R.string.date_of_birth)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            textStyle = TextStyle(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.Gray,
                disabledLabelColor = Color.Gray
            )
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(R.string.password)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(), // 비밀번호 확인 입력 시 *로 보이도록 설정
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                unfocusedLabelColor = Color.LightGray
            )
        )
        OutlinedTextField(
            value = passwordCheck,
            onValueChange = { passwordCheck = it },
            label = { Text(text = stringResource(R.string.password_check)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                unfocusedLabelColor = Color.LightGray
            )
        )
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
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
                        .clickable {
                            // 로그아웃 시 예외 처리 추가
                            try {
                                viewModel.logout {
                                    navController.navigate("login") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            } catch (e: Exception) {
                                // 로그아웃 중 에러 발생 시 처리
                                Log.e("LogoutError", "로그아웃 중 에러 발생: ${e.message}")
                                // 에러 메시지 등을 UI에 표시할 수 있습니다.
                            }
                        }
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
                onClick = {
                    // 비밀번호 유효성 검사
                    when {
                        !ValidationUtil.isValidPassword(password) -> {
                            errorMessage = "8자 이상, 영어, 숫자, 특수문자가 포함되어야 합니다."
                        }

                        !ValidationUtil.isPasswordMatch(password, passwordCheck) -> {
                            errorMessage = "비밀번호가 일치하지 않습니다."
                        }

                        else -> {
                            // 비밀번호 변경 성공 시
                            viewModel.updatePassword(password,
                                onSuccess = {
                                    errorMessage = "비밀번호가 변경되었습니다."
                                    navController.navigate(ScreenRouteDef.HomeTab.routeName) {
                                        popUpTo(ScreenRouteDef.HomeTab.routeName) {
                                            inclusive = true
                                        }
                                    }
                                },
                                onFailure = { message ->
                                    errorMessage = message
                                }
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(text = stringResource(R.string.save), fontWeight = FontWeight.Bold)
            }
        }
    }
}
