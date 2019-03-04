package com.jedrula.moviegeek.data.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jedrula.moviegeek.data.db.entity.Genre

class GenreConverter {

    private inline fun <reified T> genericType() = object: TypeToken<T>() {}.type

    @TypeConverter
    fun fromGenreList(genres: List<Genre>?): String? {
        if(genres == null) {
            return null
        }
        val gson = Gson()
        val type = genericType<List<Genre>>()

        return gson.toJson(genres, type)
    }

    @TypeConverter
    fun toGenreList(genreJson: String?): List<Genre> {
        if (genreJson == null) {
            return emptyList()
        }

        val gson = Gson()
        val type = genericType<List<Genre>>()

        return gson.fromJson(genreJson, type)
    }
}