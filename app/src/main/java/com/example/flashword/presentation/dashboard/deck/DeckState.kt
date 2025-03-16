package com.example.flashword.presentation.dashboard.deck

data class DeckState(
    val title: String = "TestDeck",
    val totalCards: Int = 0,
    val newCards: Int = 0,
    val cardsToStudy: Int = 0,
    val cardsToReview: Int = 0,
    val todayProgress: Float = 0.0f,
    val todayDue: Int = 0,
)