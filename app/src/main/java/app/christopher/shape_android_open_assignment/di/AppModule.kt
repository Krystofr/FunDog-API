package app.christopher.shape_android_open_assignment.di

import android.app.Application
import androidx.room.Room
import app.christopher.shape_android_open_assignment.api.DogApi
import app.christopher.shape_android_open_assignment.data.DogsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(DogApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideRestaurantApi(retrofit: Retrofit): DogApi =
        retrofit.create(DogApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application) : DogsDatabase =
        Room.databaseBuilder(app, DogsDatabase::class.java, "dog_api_database")
            .build()
}