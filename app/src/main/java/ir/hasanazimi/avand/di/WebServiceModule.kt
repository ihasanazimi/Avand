package ir.hasanazimi.avand.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hasanazimi.avand.data.web_services.news.NewsRssWebService
import ir.hasanazimi.avand.data.web_services.weather.WeatherWebServices
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WebServiceModule {


    @Singleton
    @Provides
    fun provideWeatherWebServices(@Named("weather")retrofit: Retrofit.Builder) : WeatherWebServices {
        return retrofit.build().create(WeatherWebServices::class.java)
    }



    @Singleton
    @Provides
    fun provideNewsWebServices(@Named("newsRss")retrofit: Retrofit.Builder) : NewsRssWebService {
        return retrofit.build().create(NewsRssWebService::class.java)
    }



}