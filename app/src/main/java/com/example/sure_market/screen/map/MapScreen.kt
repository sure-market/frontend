package com.example.sure_market.screen.post

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sure_market.R
import com.example.sure_market.view.MapDialog
import com.example.sure_market.viewmodel.MapViewModel
import com.example.sure_market.viewmodel.PostViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun MapScreen(viewModel: MapViewModel, onMovePostActivity: (String, String, String) -> Unit) {
    var dialogState by rememberSaveable {
        mutableStateOf(false)
    }

    val location = viewModel.location.value
    val cameraPositionState = rememberCameraPositionState {
        location?.let {
            val latLng = LatLng(it.latitude, it.longitude)
            position = CameraPosition.fromLatLngZoom(latLng, 17f)
        }
    }
    val uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val mapProperties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }
    val scope = rememberCoroutineScope()


    Box(modifier = Modifier.fillMaxSize()) {
        if (dialogState) {
            MapDialog(
                viewModel = viewModel,
                latitude = cameraPositionState.position.target.latitude.toString(),
                longitude = cameraPositionState.position.target.longitude.toString(),
                onMovePostActivity = onMovePostActivity
            ) { dialogState = false }
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            uiSettings = uiSettings.copy(zoomControlsEnabled = false),
            properties = mapProperties,
            cameraPositionState = cameraPositionState,
            ) {
            // 맵 카메라 중앙 마커(아이콘 변경)
            Marker(
                state = MarkerState(position = cameraPositionState.position.target),
            )
            // 내 현재 위치 마커
//            location?.let {
//                Marker(
//                    state = MarkerState(position = LatLng(it.latitude, it.longitude))
//                )
//                Circle(
//                    center = LatLng(it.latitude, it.longitude),
//                    radius = 10.0,
//                    fillColor = MaterialTheme.colors.secondary.copy(alpha = 0.3f),
//                    strokeColor = Color.Blue,
//                    strokeWidth = 0.5f,
//                )
//            }
        }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(alignment = Alignment.BottomStart),
            horizontalAlignment = Alignment.End
        ) {
            IconButton(modifier = Modifier
                .clip(CircleShape)
                .background(color = Color.Gray),
                onClick = {
                    viewModel.location.value?.let {
                        scope.launch {
                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newCameraPosition(
                                    CameraPosition(
                                        LatLng(it.latitude, it.longitude),
                                        17f, 0f, 0f
                                    )
                                ),
                                durationMs = 1000
                            )

                        }
                    }
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_gps_fixed_24),
                    contentDescription = "move current location",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.orange),
                    contentColor = Color.White
                ),
                onClick = {
                    dialogState = true
                    Log.d("daeYoung", "LatLng: ${cameraPositionState.position.target}")
//                    onMovePostActivity(
//                        cameraPositionState.position.target.latitude.toString(),
//                        cameraPositionState.position.target.longitude.toString(),
//                        "placeName"
//                    )
                }) {
                Text(text = stringResource(id = R.string.location_decision))
            }
        }

    }


}
