package com.example.learn.data

import java.util.UUID

data class Deck(
    val title: String,
    val created: Int,
    val id: String = UUID.randomUUID().toString()
)
