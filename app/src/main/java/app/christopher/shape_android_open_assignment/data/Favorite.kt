package app.christopher.shape_android_open_assignment.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val image: String = "",
    val breed: String = ""
)
