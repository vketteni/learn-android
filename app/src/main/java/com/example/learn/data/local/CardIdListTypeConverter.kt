package com.example.learn.data.local

import androidx.room.TypeConverter

class CardIdListTypeConverter {
    @TypeConverter
    fun fromCardList(cardIds: List<String>): String {
        val stringBuilder = StringBuilder()
        for (cardId in cardIds)
        {
            if (cardId.isBlank()) { continue }
            stringBuilder.append("$cardId;")
        }
        println("TypeConverter fromCardIdList: '${stringBuilder.toString().removePrefix(";").removeSuffix(";")}'")
        return stringBuilder.toString().removePrefix(";").removeSuffix(";")
    }

    @TypeConverter
    fun toCardList(cardIdListString: String): List<String> {
        val cardIdList = cardIdListString.split(';')

        println("TypeConverter toCardIdList: '${cardIdList}'")
        return if (cardIdListString == "") listOf() else cardIdList
    }
}
