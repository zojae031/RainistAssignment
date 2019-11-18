package rainist.assignment.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonArray

class DataConverterUtil {
    @TypeConverter
    fun fromJsonArray(data: String): JsonArray? {
        return Gson().fromJson(data, JsonArray::class.java)
    }

    @TypeConverter
    fun toJsonArray(data: JsonArray): String? {
        return Gson().toJson(data)
    }

}