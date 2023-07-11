package com.example.sure_market.screen.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat
import androidx.lifecycle.lifecycleScope
import com.example.sure_market.screen.post.MapScreen
import com.example.sure_market.screen.post.PostActivity
import com.example.sure_market.ui.theme.SureMarketTheme
import com.example.sure_market.viewmodel.MapViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class MapActivity : AppCompatActivity() {
    val viewModel by viewModels<MapViewModel>()
    var addressDong: String = "연습"      // 동
    lateinit var addressRegion: String    // 도
    var addressCity: String = " 연습"      // 시
    lateinit var addressLatitude: String  // 위도
    lateinit var addressLongitude: String // 경도


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("daeYoung", "activity: MapActivity")
        setContent {
            var granted by rememberSaveable {
                mutableStateOf(false)
            }

            val launcher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
                    granted = isGranted
                }

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                granted = true
            }
            SureMarketTheme{
                if (granted) {
                    lifecycle.addObserver(viewModel)
                    if (viewModel.location.value != null) {
                        MapScreen(viewModel = viewModel, onMovePostActivity = onMovePostActivity)
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(strokeWidth = 3.dp)
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text("권한이 허용되지 않았습니다")
                        Button(onClick = { launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }) {
                            Text("권한 요청")
                        }
                    }
                }

            }
        }
    }


    inner class MyGeocodeListener(private val place: String) : Geocoder.GeocodeListener {
        override fun onGeocode(address: MutableList<Address>) {
            Log.d("daeYoung", "MyGeocodeListener call: $address")
            address[0].apply {
                addressDong = featureName
                addressRegion = adminArea
                addressCity = locality
                addressLatitude = latitude.toString()
                addressLongitude = longitude.toString()
            }
            Log.d(
                "daeYoung",
                "도: $addressRegion, 시: $addressCity, 동: $addressDong, 위도: $addressLatitude, 경도: $addressLongitude "
            )
            intentSetting(place = place, resion = "$addressCity $addressDong")
        }
        private fun intentSetting(place: String, resion: String) {
            val intent = Intent(applicationContext, PostActivity::class.java).also {
                it.putExtra("region", resion)
                it.putExtra("placeName", place)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun reverseGeocoder(latitude: String, longitude: String, place:String) {
        val geocoder = Geocoder(this, Locale.KOREA)
        geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1, MyGeocodeListener(place))
    }

    private val onMovePostActivity = { latitude: String, longitude: String, place: String ->
//        lifecycleScope.launch {
//            val intent = Intent(applicationContext, PostActivity::class.java).apply {
//                async { reverseGeocoder(latitude, longitude) }.await()
//                putExtra("region", "$addressCity $addressDong")
//                putExtra("placeName", place)
//            }
//
//        }
        reverseGeocoder(latitude, longitude, place)
    }
}