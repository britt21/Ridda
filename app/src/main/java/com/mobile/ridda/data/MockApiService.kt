package com.mobile.ridda.data

import com.google.android.gms.maps.model.LatLng

interface MockApiService {
    suspend fun requestRide(): RideConfirmationResponse
    suspend fun getFareEstimate(pickup: LatLng, destination: LatLng): FareEstimateResponse
}
