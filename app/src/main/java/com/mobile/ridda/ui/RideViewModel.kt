package com.mobile.ridda.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.mobile.ridda.data.*
import com.mobile.ridda.repository.RideRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RideViewModel @Inject constructor(
    private val repository: RideRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<RideUiState>(RideUiState.Idle)
    val uiState: StateFlow<RideUiState> = _uiState.asStateFlow()

    private val _rideHistory = MutableStateFlow<List<Ride>>(emptyList())
    val rideHistory: StateFlow<List<Ride>> = _rideHistory.asStateFlow()

    var pickup: LatLng? = null
    var destination: LatLng? = null
    var lastFareEstimate: FareEstimateResponse? = null

    fun estimateFare() {
        val p = pickup
        val d = destination
        if (p == null || d == null) {
            _uiState.value = RideUiState.Error("Select pickup and destination")
            return
        }
        _uiState.value = RideUiState.Loading
        viewModelScope.launch {
            try {
                val estimate = repository.getFareEstimate(p, d)
                lastFareEstimate = estimate
                _uiState.value = RideUiState.FareEstimate(estimate)
            } catch (e: Exception) {
                _uiState.value = RideUiState.Error("Failed to estimate fare")
            }
        }
    }

    fun requestRide() {
        val p = pickup
        val d = destination
        val fare = lastFareEstimate?.total_fare
        if (p == null || d == null || fare == null) {
            _uiState.value = RideUiState.Error("Missing ride info")
            return
        }
        _uiState.value = RideUiState.Loading
        viewModelScope.launch {
            try {
                val confirmation = repository.requestRide()
                val ride = Ride(
                    pickupLat = p.latitude,
                    pickupLng = p.longitude,
                    destinationLat = d.latitude,
                    destinationLng = d.longitude,
                    fare = fare,
                    timestamp = System.currentTimeMillis(),
                    driverName = confirmation.driver.name,
                    car = confirmation.driver.car,
                    plateNumber = confirmation.driver.plateNumber
                )
                repository.saveRide(ride)
                _uiState.value = RideUiState.RideConfirmed(confirmation)
                loadRideHistory()
            } catch (e: Exception) {
                _uiState.value = RideUiState.Error("Failed to request ride")
            }
        }
    }

    fun loadRideHistory() {
        viewModelScope.launch {
            _rideHistory.value = repository.getRideHistory()
        }
    }

    fun resetState() {
        _uiState.value = RideUiState.Idle
    }
}
