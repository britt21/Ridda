package com.mobile.ridda.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RideDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRide(ride: Ride)

    @Query("SELECT * FROM rides ORDER BY timestamp DESC")
    suspend fun getAllRides(): List<Ride>
}

