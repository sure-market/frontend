package com.example.sure_market.screen.intro

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sure_market.R
import com.example.sure_market.viewmodel.LoginViewModel

@Composable
fun SignUp(
    onBack: () -> Unit,
    onFinish: (String, String, String, String) -> Unit
) {
    var name by rememberSaveable() {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var phone by rememberSaveable {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    // 뒤로 가기
    BackHandler(enabled = true) {
        onBack()
    }

    val focusManager = LocalFocusManager.current
    val focusRequester =
        remember { listOf(FocusRequester(), FocusRequester(), FocusRequester()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = R.string.id_text),
            modifier = Modifier.align(Alignment.Start),
            fontSize = 20.sp
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = { name = it },
            placeholder = { Text(text = stringResource(id = R.string.id)) },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusRequester[0].requestFocus() })
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.password_text),
            modifier = Modifier.align(Alignment.Start),
            fontSize = 20.sp
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester[0]),
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(text = stringResource(id = R.string.password_text)) },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusRequester[1].requestFocus() })

        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.phone_text),
            modifier = Modifier.align(Alignment.Start),
            fontSize = 20.sp
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester[1]),
            value = phone,
            onValueChange = { phone = it },
            placeholder = { Text(text = stringResource(id = R.string.phone_text)) },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusRequester[2].requestFocus() })
            )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.email_text),
            modifier = Modifier.align(Alignment.Start),
            fontSize = 20.sp
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().focusRequester (focusRequester[2]),
            value = email,
            onValueChange = { email = it },
            placeholder = { Text(text = stringResource(id = R.string.email_text)) },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
            keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus() })
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onFinish(name, password, phone, email) }) {
            Text(text = stringResource(id = R.string.signup_complete))
        }
    }


}