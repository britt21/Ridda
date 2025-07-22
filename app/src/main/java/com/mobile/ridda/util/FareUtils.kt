package com.mobile.ridda.util

import com.mobile.ridda.data.FareEstimateResponse
import java.util.Calendar
import kotlin.random.Random

object FareUtils {
    private const val BASE_FARE = 2.5
    private const val PER_KM = 1.0

    fun getBaseFare() = BASE_FARE
    fun getPerKmRate() = PER_KM

    fun getDemandMultiplier(hour: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)): Double {
        // Peak hours: 7-9am, 5-8pm
        return if ((hour in 7..9) || (hour in 17..20)) 1.5 else 1.0
    }

    fun getTrafficMultiplier(): Double {
        // Simulate traffic: 20% chance heavy (1.3x), 30% moderate (1.15x), else normal (1.0x)
        val roll = Random.nextDouble()
        return when {
            roll < 0.2 -> 1.3
            roll < 0.5 -> 1.15
            else -> 1.0
        }
    }

    fun calculateFare(distanceKm: Double, demand: Double, traffic: Double = 1.0): Double {
        val fare = (BASE_FARE + (distanceKm * PER_KM)) * demand * traffic
        return String.format("%.2f", fare).toDouble() // rounded to 2dp
    }

    fun getFareEstimate(distanceKm: Double): FareEstimateResponse {
        val demand = getDemandMultiplier()
        val traffic = getTrafficMultiplier()
        val distanceFare = distanceKm * PER_KM
        val totalFare = calculateFare(distanceKm, demand, traffic)
        return FareEstimateResponse(
            base_fare = BASE_FARE,
            distance_fare = distanceFare,
            demand_multiplier = demand,
            total_fare = totalFare
        )
    }
}