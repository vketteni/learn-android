package com.example.learn.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson

class CardContentTypeConverter {
        private val gson = Gson()
    @TypeConverter
    fun fromCardContent(cardContent: CardContent): String {
        return gson.toJson(cardContent)
    }

    @TypeConverter
    fun jsonToCardContent(json: String): CardContent {
        return gson.fromJson(json, CardContent::class.java)
    }
}
