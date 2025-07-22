package com.mobile.ridda

import com.mobile.ridda.util.FareUtils
import org.junit.Assert.assertEquals
import org.junit.Test

class FareUtilsTest {
    @Test
    fun testBasicFareCalculation() {
        val fare = FareUtils.calculateFare(distanceKm = 5.0, demand = 1.0, traffic = 1.0)
        assertEquals(7.5, fare, 0.01)
    }

    @Test
    fun testSurgePricing() {
        val fare = FareUtils.calculateFare(distanceKm = 8.0, demand = 1.5, traffic = 1.0)
        assertEquals(14.0, fare, 0.01)
    }

    @Test
    fun testTrafficSurgePricing() {
        val fare = FareUtils.calculateFare(distanceKm = 6.0, demand = 1.0, traffic = 1.3)
        assertEquals(12.0, fare, 0.01)
    }
} 