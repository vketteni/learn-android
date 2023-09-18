package com.example.learn.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LocalDeck::class, LocalCard::class, DeckCardCrossRef::class], version = 1, exportSchema = false)
@TypeConverters(CardIdsTypeConverter::class, CardContentTypeConverter::class, CardReferenceTypeConverter::class)
abstract class LearnDatabase : RoomDatabase() {
    abstract fun deckDao(): DecksDao
    abstract fun cardDao(): CardsDao
    abstract fun deckCardCrossRefDao(): DeckCardCrossRefDao
    companion object {

        @Volatile
        private var Instance: LearnDatabase? = null

        fun getDatabase(context: Context): LearnDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    LearnDatabase::class.java,
                    "Learn.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

