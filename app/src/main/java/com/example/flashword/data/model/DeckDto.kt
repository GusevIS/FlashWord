package com.example.flashword.data.model

import com.example.flashword.domain.model.DeckCreateModel
import com.example.flashword.domain.model.DeckModel

data class DeckDto(
    val userId: String = "",
    var deckId: String = "",
    val title: String = "",
)

data class DeckCreateDto(
    val userId: String = "",
    val title: String = "",
)


fun DeckDto.toDeckModel(): DeckModel =
    DeckModel(
        userId = userId,
        deckId = deckId,
        title = title
    )

fun DeckModel.toDeckDto(): DeckDto =
    DeckDto(
        userId = userId,
        deckId = deckId,
        title = title
    )

fun DeckCreateDto.toDeckCreateModel(): DeckCreateModel =
    DeckCreateModel(
        userId = userId,
        title = title
    )

fun DeckCreateModel.toDeckCreateDto(): DeckCreateDto =
    DeckCreateDto(
        userId = userId,
        title = title
    )