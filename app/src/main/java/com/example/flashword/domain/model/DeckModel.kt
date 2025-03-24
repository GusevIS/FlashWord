package com.example.flashword.domain.model

data class DeckModel(
    val userId: String,
    var deckId: String,
    val title: String,
    val totalCards: Long = 0,
    val newCards: Long = 0,
    val cardsToStudy: Long = 0,
    val cardsToReview: Long = 0,
    val todayProgress: Float = 0.0f,
    val todayDue: Long = 0,
)

data class DeckCreateModel(
    val userId: String,
    val title: String,
    val totalCards: Long = 0,
    val newCards: Long = 0,
    val cardsToStudy: Long = 0,
    val cardsToReview: Long = 0,
    val todayProgress: Float = 0.0f,
    val todayDue: Long = 0,
)