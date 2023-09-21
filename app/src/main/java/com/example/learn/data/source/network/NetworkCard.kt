package com.example.learn.data.source.network


data class NetworkCard(
    val cardId: String,
    val frontContent: String,
    val backContent: String,
    val title: String,
    val position: Int,
    val created: Long,
)