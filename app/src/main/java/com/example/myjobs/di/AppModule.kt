package com.example.myjobs.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.myjobs.BuildConfig
import com.example.myjobs.constants.Constants
import com.example.myjobs.data.api.AuthApiService
import com.example.myjobs.data.api.AuthInterceptor
import com.example.myjobs.data.api.JobApi
import com.example.myjobs.data.db.UserDao
import com.example.myjobs.data.repository.AuthRepository
import com.example.myjobs.data.repository.JobRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(sharedPreferences: SharedPreferences): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getHeader(sharedPreferences))
        .build()


    @Singleton
    @Provides
    fun provideAuthAPI(retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Singleton
    @Provides
    fun provideAuthRepository(
        apiService: AuthApiService,
        userDao: UserDao,
        sharedPreferences: SharedPreferences
    ): AuthRepository =
        AuthRepository(apiService, userDao, sharedPreferences)

    @Singleton
    @Provides
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.applicationContext.getSharedPreferences(
            "User",
            Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun provideJobAPI(retrofit: Retrofit): JobApi = retrofit.create(JobApi::class.java)

    @Singleton
    @Provides
    fun provideJobRepository(jobApi: JobApi): JobRepository = JobRepository(jobApi)

    private fun getHeader(sharedPreferences: SharedPreferences): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(AuthInterceptor(sharedPreferences.getString("token", "")))
        return client.connectTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .build()
    }

}