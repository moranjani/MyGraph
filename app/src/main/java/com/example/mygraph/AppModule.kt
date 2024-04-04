package com.example.mygraph

import com.example.mygraph.service.IRandomService
import com.example.mygraph.service.RandomApi
import com.example.mygraph.service.RandomService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): RandomApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RandomApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: RandomApi): IRandomService {
        return RandomService(api)
    }

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO


}