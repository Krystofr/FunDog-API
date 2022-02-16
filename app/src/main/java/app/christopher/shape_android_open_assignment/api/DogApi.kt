package app.christopher.shape_android_open_assignment.api

import app.christopher.shape_android_open_assignment.data.BreedImageObject
import app.christopher.shape_android_open_assignment.data.DogBreeds
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApi {
    companion object {
        const val BASE_URL = "https://dog.ceo/api/"
    }

    @GET("breeds/list/all")
    suspend fun getDogBreeds(): DogBreeds

    @GET("breed/{breed}/images")
    suspend fun getBreedImages(@Path("breed") breed:String) : BreedImageObject
}