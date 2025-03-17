package com.example.flashword.presentation.addcard

data class AddCardState(
    val deckId: String,
    val deckTitle: String = "Test Deck",
    val isBackSide: Boolean = false,
    val frontText: String = "",
    val backText: String = "",


)