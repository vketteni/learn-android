package com.example.learn.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CardIdsTypeConverter {
        private val gson = Gson()
    @TypeConverter
    fun fromCardIds(cardIds: List<String>): String {
        return gson.toJson(cardIds)
    }

    @TypeConverter
    fun jsonToCardIds(json: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, listType)
    }
}
