package com.mobile.ridda.ui

import com.mobile.ridda.data.FareEstimateResponse
import com.mobile.ridda.data.RideConfirmationResponse

sealed class RideUiState {
    object Idle : RideUiState()
    object Loading : RideUiState()
    data class FareEstimate(val estimate: FareEstimateResponse) : RideUiState()
    data class RideConfirmed(val confirmation: RideConfirmationResponse) : RideUiState()
    data class Error(val message: String) : RideUiState()
}