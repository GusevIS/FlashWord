package com.example.flashword.presentation.addcard

data class AddCardState(
    val deckTitle: String = "Test Deck",
    val isBackSide: Boolean = false,
    val frontText: String = "",
    val backText: String = "",


)