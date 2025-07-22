package com.mobile.ridda.data

import com.google.android.gms.maps.model.LatLng
import com.mobile.ridda.util.FareUtils
import kotlinx.coroutines.delay
import kotlin.random.Random

// Data class representing a latitude/longitude
// Use Android's LatLng in real app, but for mock, keep this
// data class LatLng(val latitude: Double, val longitude: Double)

class FakeApiService : MockApiService {
    override suspend fun getFareEstimate(pickup: LatLng, destination: LatLng): FareEstimateResponse {
        // Simulate network delay
        delay(500)
        val distance = calculateDistance(pickup, destination)
        return FareUtils.getFareEstimate(distance)
    }

    override suspend fun requestRide(): RideConfirmationResponse {
        delay(1000)
        val drivers = listOf(
            Driver("John Doe", "Toyota Prius", "XYZ-1234"),
            Driver("Jane Smith", "Honda Civic", "ABC-5678"),
            Driver("Alex Lee", "Ford Focus", "LMN-9101")
        )
        val driver = drivers[Random.nextInt(drivers.size)]
        return RideConfirmationResponse(
            status = "confirmed",
            driver = driver,
            estimated_arrival = "${Random.nextInt(3, 8)} min"
        )
    }

    private fun calculateDistance(p1: LatLng, p2: LatLng): Double {
        // Haversine formula for distance in km
        val R = 6371 // Earth radius in km
        val dLat = Math.toRadians(p2.latitude - p1.latitude)
        val dLon = Math.toRadians(p2.longitude - p1.longitude)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(p1.latitude)) * Math.cos(Math.toRadians(p2.latitude)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return R * c
    }
}
