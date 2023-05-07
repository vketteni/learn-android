package com.example.learn.data

import java.util.*

data class Card(
    val id: String = UUID.randomUUID().toString(),
    val deckId: String,
    val front: String = "",
    val back: String = "",
    val created: Int
)
