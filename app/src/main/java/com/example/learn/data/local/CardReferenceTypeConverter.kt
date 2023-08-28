package com.example.learn.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson

class CardReferenceTypeConverter {
        private val gson = Gson()
    @TypeConverter
    fun fromCardReference(cardReference: CardReference): String {
        return gson.toJson(cardReference)
    }

    @TypeConverter
    fun jsonToCardReference(json: String): CardReference {
        return gson.fromJson(json, CardReference::class.java)
    }
}
