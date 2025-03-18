package com.example.flashword.domain.model

data class DeckModel(
    val userId: String,
    var deckId: String,
    val title: String,
)

data class DeckCreateModel(
    val userId: String,
    val title: String,
)