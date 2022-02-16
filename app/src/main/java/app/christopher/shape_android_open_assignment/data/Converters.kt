package app.christopher.shape_android_open_assignment.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private inline fun <reified T> genericType() = object : TypeToken<T>() {}.type

    @TypeConverter
    fun fromResultList(results: Message): String {
        return Gson().toJson(results)
    }

    @TypeConverter
    fun toResultList(results: String): Message {
        val type = genericType<Message>()
        return Gson().fromJson(results, type)
    }
}