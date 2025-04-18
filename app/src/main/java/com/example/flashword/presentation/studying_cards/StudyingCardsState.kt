package com.example.flashword.presentation.studying_cards

import com.example.flashword.domain.model.CardModel

data class StudyingCardsState(
    val deckId: String,
    val deckTitle: String = "Test Deck",
    val isBackSide: Boolean = false,
    val card: CardModel = CardModel("", deckId, "", "", 0L, 0L, 0L, wasForgotten = false),
    val reviewingEnded: Boolean = false,
    val progress: Float = 0f,
    val totalCards: Int = 0,
    val cardIndex: Int = 0,
    )