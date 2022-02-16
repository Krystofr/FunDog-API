package app.christopher.shape_android_open_assignment.data

import android.util.Log
import androidx.lifecycle.LiveData
import app.christopher.shape_android_open_assignment.api.DogApi
import app.christopher.shape_android_open_assignment.util.networkBoundResource
import kotlinx.coroutines.*
import javax.inject.Inject

class DogsRepository @Inject constructor(
    private val api: DogApi,
    db: DogsDatabase
) {
    private val dogsDao = db.dogsDao()
    var job: CompletableJob? = null

    fun getDogBreeds() = networkBoundResource(
        query = {
            dogsDao.getAllDogBreeds()
        },
        fetch = {
            delay(2000)
            api.getDogBreeds()
        },
        saveFetchResult = {
            dogsDao.deleteAllBreeds()
            dogsDao.insertDogBreeds(it)
        }
    )

    fun getBreedImages(breed: String): LiveData<BreedImageObject> {
        job = Job()
        return object : LiveData<BreedImageObject>() {
            override fun onActive() { //when this method is called do something
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(Dispatchers.IO + theJob).launch {//get photos on the background thread
                        try {
                            val breedImageObject: BreedImageObject =
                                api.getBreedImages(breed)

                            withContext(Dispatchers.Main) {
                                //set value on the main thread
                                value = breedImageObject
                                theJob.complete()
                            }

                        } catch (e: Throwable) {
                            Log.d("Repo", e.message.toString())
                        }
                    }
                }
            }
        }
    }

    suspend fun insertFavorite(favorite: Favorite) = dogsDao.insertFavorite(favorite)

    fun getFavorites() = dogsDao.getFavoriteBreeds()

    suspend fun removeFavorite(favorite: Favorite) = dogsDao.removeFavorite(favorite)
}