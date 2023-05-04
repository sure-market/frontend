package com.example.sure_market.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.location.*

class MapViewModel(application: Application) : AndroidViewModel(application),
    DefaultLifecycleObserver {

    private val _place = mutableStateOf("")
    val place: State<String> = _place

    fun setPlace(place: String) {
        _place.value = place
    }

    // FusedLocationProviderClient
    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application.applicationContext)
    private val locationRequest: LocationRequest      // 현재 위치를 얻기위한 설정
    private val locationCallback: MyLocationCallback  // 위치정보를 얻었을 때 무슨 처리를 할 건지

    private val _location = mutableStateOf<Location?>(null)
    val location: State<Location?> = _location

    init {
        locationCallback = MyLocationCallback()
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).apply {
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(true)
        }.build()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        addLocationListener()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        removeLocationListener()
    }

    @SuppressLint("MissingPermission")
    private fun addLocationListener() {
        Looper.myLooper()?.let { looper ->
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
            Log.d("daeYoung", "onResume: requestLocationUpdates")
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            Log.d("daeYoung", "lastLocation의 위도:${location?.latitude} 경도: ${location?.longitude}")
        }

    }


    private fun removeLocationListener() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    inner class MyLocationCallback : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val location = locationResult.lastLocation // 현재 위치, 주기적으로 호출된다.
            _location.value = location
            Log.d(
                "daeYoung",
                "location_latitude: ${location?.latitude} location_longitude ${location?.longitude}"
            )
        }
    }
}