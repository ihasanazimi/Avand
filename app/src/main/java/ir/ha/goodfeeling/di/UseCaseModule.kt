package ir.ha.goodfeeling.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
/*    @Provides
    @Singleton
    fun provideDeveloperInfoUseCase(developerInfoRepository: DeveloperInfoRepository) : DeveloperInfoUseCase{
        return DeveloperInfoUseCaseImpl(developerInfoRepository)
    }*/
}