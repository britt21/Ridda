package com.mobile.ridda.data

import com.mobile.ridda.data.Driver

data class RideConfirmationResponse(
    var status: String,
    var driver: Driver,
    var estimated_arrival: String) {

}
