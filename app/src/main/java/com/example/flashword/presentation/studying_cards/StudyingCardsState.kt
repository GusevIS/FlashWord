package com.example.flashword.presentation.studying_cards

import com.example.flashword.domain.model.CardModel

data class StudyingCardsState(
    val deckId: String,
    val deckTitle: String = "Test Deck",
    val isBackSide: Boolean = true,
    val cards: List<CardModel> = emptyList(),
    val cardIndex: Int = 0,



)