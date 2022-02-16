package app.christopher.shape_android_open_assignment.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dogs")
data class DogBreeds(
    @PrimaryKey(autoGenerate = true)
    var breedId: Int = 0,
    val message: Message? = null,
    val status: String? = null
)