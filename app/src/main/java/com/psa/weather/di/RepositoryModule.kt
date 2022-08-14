package com.psa.weather.di

import com.psa.weather.data.repository.CityRepositoryImpl
import com.psa.weather.domain.repository.CityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun provideCityRepositoryImpl(cityRepository: CityRepositoryImpl): CityRepository

}
