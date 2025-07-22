package com.mobile.ridda.di

import android.content.Context
import com.mobile.ridda.data.FakeApiService
import com.mobile.ridda.data.RideDao
import com.mobile.ridda.data.RideDatabase
import com.mobile.ridda.repository.RideRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRideDatabase(@ApplicationContext context: Context): RideDatabase =
        RideDatabase.getDatabase(context)

    @Provides
    fun provideRideDao(db: RideDatabase): RideDao = db.rideDao()

    @Provides
    @Singleton
    fun provideFakeApiService(): FakeApiService = FakeApiService()

    @Provides
    @Singleton
    fun provideRideRepository(rideDao: RideDao, apiService: FakeApiService): RideRepository =
        RideRepository(rideDao, apiService)
}

