package com.mobile.ridda.repository

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.mobile.ridda.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RideRepository(
    private val rideDao: RideDao,
    private val apiService: FakeApiService
) {
    suspend fun getFareEstimate(pickup: LatLng, destination: LatLng): FareEstimateResponse {
        return apiService.getFareEstimate(pickup, destination)
    }

    suspend fun requestRide(): RideConfirmationResponse {
        return apiService.requestRide()
    }

    suspend fun saveRide(ride: Ride) {
        withContext(Dispatchers.IO) {
            rideDao.insertRide(ride)
        }
    }

    suspend fun getRideHistory(): List<Ride> {
        return withContext(Dispatchers.IO) {
            rideDao.getAllRides()
        }
    }
}

