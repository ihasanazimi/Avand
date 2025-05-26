package ir.hasanazimi.avand.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hasanazimi.avand.data.repository.CurrenciesRepository
import ir.hasanazimi.avand.data.repository.CurrenciesRepositoryImpl
import ir.hasanazimi.avand.data.repository.NewsRssRepository
import ir.hasanazimi.avand.data.repository.NewsRssRepositoryImpl
import ir.hasanazimi.avand.data.repository.WeatherRepository
import ir.hasanazimi.avand.data.repository.WeatherRepositoryImpl
import ir.hasanazimi.avand.data.web_services.currencies.CurrenciesWebService
import ir.hasanazimi.avand.data.web_services.news.RssService
import ir.hasanazimi.avand.data.web_services.weather.WeatherWebServices
import ir.hasanazimi.avand.db.DataStoreManager
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(webServices: WeatherWebServices, dataStoreManager: DataStoreManager) : WeatherRepository{
        return WeatherRepositoryImpl(webServices,dataStoreManager)
    }



    @Provides
    @Singleton
    fun provideRssNewsRepository(rssService: RssService, dataStoreManager: DataStoreManager) : NewsRssRepository{
        return NewsRssRepositoryImpl(rssService,dataStoreManager)
    }


    @Provides
    @Singleton
    fun provideCurrenciesRepository(webService: CurrenciesWebService, dataStoreManager: DataStoreManager) : CurrenciesRepository{
        return CurrenciesRepositoryImpl(webService,dataStoreManager)
    }
}