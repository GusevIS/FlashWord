package com.example.flashword.domain.model

data class DeckModel(
    val userId: String = "",
    var deckId: String = "",
    val title: String = "",
) {
    constructor() : this("")
}