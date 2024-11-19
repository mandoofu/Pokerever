package com.mandoo.pokerever.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationProvider(context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission") // 권한은 별도로 확인
    fun getCurrentLocation(onResult: (Location?) -> Unit) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                onResult(location)
            }
            .addOnFailureListener { e ->
                Log.e("LocationProvider", "Failed to get location: ${e.localizedMessage}")
                onResult(null)
            }
    }
}
