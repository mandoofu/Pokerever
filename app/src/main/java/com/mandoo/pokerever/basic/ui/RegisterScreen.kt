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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mandoo.pokerever.R
import com.mandoo.pokerever.utils.ValidationUtil
import com.mandoo.pokerever.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(navController: NavController) {
    val viewModel: RegisterViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(40.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.member_insert),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally) // 중앙 정렬
        )

        var name by remember { mutableStateOf("") }
        var nameError by remember { mutableStateOf("") }
        OutlinedTextField(
            value = name,
            onValueChange = { newText -> name = newText },
            label = { Text(text = stringResource(R.string.name)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White)
        )
        nameError = if (name.isNotEmpty() && name.length < 2) "이름은 2자 이상이어야 합니다." else ""

        Text(
            text = nameError,
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )

        var frontNumber by remember { mutableStateOf("") }
        var backNumber by remember { mutableStateOf("") }
        var registrationNumberError by remember { mutableStateOf("") }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp), // 간격 줄이기
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = frontNumber,
                onValueChange = { newText -> frontNumber = newText },
                label = { Text(text = "생년월일") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.weight(0.5f),
                textStyle = TextStyle(color = Color.White)
            )
            Text(
                text = "-",
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            OutlinedTextField(
                value = if (backNumber.isNotEmpty()) backNumber[0] + "******" else "",
                onValueChange = { newValue ->
                    if (newValue.isNotEmpty() && newValue[0] in '1'..'4') {
                        backNumber = newValue[0].toString()
                    }
                },
                label = { Text(text = "주민번호 뒷자리") },
                singleLine = true,
                modifier = Modifier.weight(0.5f),
                textStyle = TextStyle(color = Color.White)
            )
        }

        registrationNumberError =
            if (frontNumber.isNotEmpty() && backNumber.isNotEmpty() && !ValidationUtil.isValidRegistrationNumber(
                    "$frontNumber-$backNumber"
                )
            ) {
                "유효하지 않은 주민등록번호입니다."
            } else {
                ""
        }

        Text(
            text = registrationNumberError,
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )

        var nickname by remember { mutableStateOf("") }
        var nicknameError by remember { mutableStateOf("") }

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
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(color = Color.White)
            )
            OutlinedButton(
                onClick = {
                    viewModel.checkDuplicateNickname(nickname)
                },
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp)
                    .height(40.dp), // TextField 높이와 동일하게 설정
            ) {
                Text(
                    text = stringResource(R.string.duplicate_check),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // 닉네임 유효성 검사 및 중복 확인
        nicknameError = if (nickname.isEmpty()) {
            "닉네임을 입력해주세요."
        } else {
            if (viewModel.isDuplicate != null) {
                if (viewModel.isDuplicate == true) {
                    "닉네임이 이미 존재합니다."
                } else {
                    "사용 가능한 닉네임입니다."
                }
            } else {
                ""
            }
        }

        Text(
            text = nicknameError,
            color = if (viewModel.isDuplicate == true) Color.Red else Color.Green,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )

        var email by remember { mutableStateOf("") }
        var emailError by remember { mutableStateOf("") }

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
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(color = Color.White)
            )
            OutlinedButton(
                onClick = {
                    viewModel.checkDuplicateEmail(email)
                },
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp)
                    .height(40.dp), // TextField 높이와 동일하게 설정
            ) {
                Text(
                    text = stringResource(R.string.duplicate_check),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // 이메일 유효성 검사 및 중복 확인
        emailError = if (email.isEmpty()) {
            "이메일을 입력해주세요."
        } else {
            if (viewModel.isEmailDuplicate != null) {
                if (viewModel.isEmailDuplicate == true) {
                    "이메일이 이미 존재합니다."
                } else {
                    "사용 가능한 이메일입니다."
                }
            } else {
                ""
            }
        }

        Text(
            text = emailError,
            color = if (viewModel.isEmailDuplicate == true) Color.Red else Color.Green,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )

        var password by remember { mutableStateOf("") }
        var passwordError by remember { mutableStateOf("") }

        OutlinedTextField(
            value = password,
            onValueChange = { newText -> password = newText },
            label = { Text(text = stringResource(R.string.password)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(), // 비밀번호 입력 시 *로 보이도록 설정
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White)
        )

        passwordError = if (password.isNotEmpty() && !ValidationUtil.isValidPassword(password)) {
            "8자 이상, 영문,숫자,특수문자를 포함해야 합니다."
        } else {
            ""
        }

        Text(
            text = passwordError,
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )

        var passwordcheck by remember { mutableStateOf("") }
        var passwordCheckError by remember { mutableStateOf("") }

        OutlinedTextField(
            value = passwordcheck,
            onValueChange = { newText -> passwordcheck = newText },
            label = { Text(text = stringResource(R.string.password_check)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(), // 비밀번호 확인 입력 시 *로 보이도록 설정
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White)
        )

        passwordCheckError = if (passwordcheck != password) "비밀번호가 일치하지 않습니다." else ""

        Text(
            text = passwordCheckError,
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )

        Button(
            onClick = {
                if (name.isNotEmpty() && password.isNotEmpty() && password == passwordcheck) {
                    viewModel.registerUser(
                        name,
                        frontNumber,
                        backNumber,
                        email,
                        password,
                        nickname
                    )
                    navController.navigate("login_screen")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D1D1D)) // 버튼 색상 추가
        ) {
            Text(
                text = stringResource(R.string.member_create),
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.return_login),
                color = Color.White,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.login),
                color = Color(0xFF1D1D1D),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate("login_screen")
                }
            )
        }
    }
}
