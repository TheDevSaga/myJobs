package com.example.myjobs.di

import com.example.myjobs.constants.Constants
import com.example.myjobs.data.api.AuthApiService
import com.example.myjobs.data.db.UserDao
import com.example.myjobs.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor(

                    ).setLevel(HttpLoggingInterceptor.Level.BODY)
                ).build()
        )
        .build()


    @Singleton
    @Provides
    fun provideAuthAPI(retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Singleton
    @Provides
    fun provideAuthRepository(apiService: AuthApiService,userDao: UserDao): AuthRepository =
        AuthRepository(apiService,userDao)
}