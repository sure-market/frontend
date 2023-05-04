package com.example.sure_market.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.sure_market.R
import com.example.sure_market.viewmodel.MapViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

@Composable
fun MapDialog(
    viewModel: MapViewModel,
    latitude: String,
    longitude: String,
    onMovePostActivity: (String, String, String) -> Unit,
    onClick: () -> Unit
) {
    Dialog(onDismissRequest = { onClick() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            color = colorResource(id = R.color.dark_gray),
//            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            shape = RoundedCornerShape(24.dp)
        ) {
            MapDialogContent(
                viewModel = viewModel,
                latitude = latitude,
                longitude = longitude,
                onMovePostActivity = onMovePostActivity,
                onClick = onClick
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MapDialogContent(
    viewModel: MapViewModel,
    latitude: String,
    longitude: String,
    onMovePostActivity: (String, String, String) -> Unit,
    onClick: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    var buttonEnabled by remember { mutableStateOf(false) }
    buttonEnabled = viewModel.place.value != ""

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        delay(700)
        keyboardController?.show()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.location_input),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth(),
            value = viewModel.place.value,
            onValueChange = { viewModel.setPlace(it) },
            textStyle = TextStyle(color = Color.White),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.location_placeholder),
                    color = Color.Gray
                )
            })

        Spacer(modifier = Modifier.height(16.dp))

        Button(modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.orange)),
            enabled = buttonEnabled,
            onClick = {
                onClick()
                Log.d("daeYoung", "MapDialog success")
                onMovePostActivity(latitude, longitude, viewModel.place.value)
            }) {
            Text(
                text = "거래 장소 등록",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

    }

}