package app.christopher.shape_android_open_assignment.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DogsDao {

    @Query("SELECT * FROM dogs")
    fun getAllDogBreeds(): Flow<DogBreeds>

    @Query("SELECT * FROM favorite")
    fun getFavoriteBreeds(): LiveData<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogBreeds(breeds: DogBreeds)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Query("DELETE FROM dogs")
    suspend fun deleteAllBreeds()

    @Delete
    suspend fun removeFavorite(favorite: Favorite)
}