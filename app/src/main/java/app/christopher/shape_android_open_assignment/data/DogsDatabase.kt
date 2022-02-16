package app.christopher.shape_android_open_assignment.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DogBreeds::class,Favorite::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class DogsDatabase : RoomDatabase() {

    abstract fun dogsDao(): DogsDao
}