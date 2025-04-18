package ir.ha.goodfeeling.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ha.goodfeeling.data.remote.webServices.WeatherWebServices
import ir.ha.goodfeeling.data.repository.weather.WeatherRepository
import ir.ha.goodfeeling.data.repository.weather.WeatherRepositoryImpl
import ir.ha.goodfeeling.db.DataStoreManager
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDeveloperInfoRepository(webServices: WeatherWebServices , dataStoreManager: DataStoreManager) : WeatherRepository{
        return WeatherRepositoryImpl(webServices,dataStoreManager)
    }

}