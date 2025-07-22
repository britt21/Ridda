package com.mobile.ridda.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rides")
data class Ride(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pickupLat: Double,
    val pickupLng: Double,
    val destinationLat: Double,
    val destinationLng: Double,
    val fare: Double,
    val timestamp: Long,
    val driverName: String? = null,
    val car: String? = null,
    val plateNumber: String? = null
)

data class Driver(
    val name: String,
    val car: String,
    val plateNumber: String
)

data class FareEstimateResponse(
    val base_fare: Double,
    val distance_fare: Double,
    val demand_multiplier: Double,
    val total_fare: Double
)
