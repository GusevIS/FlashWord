package com.example.flashword.presentation.dashboard

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.flashword.FlashAppViewModel
import com.example.flashword.domain.usecases.AddNewDeckUseCase
import com.example.flashword.domain.usecases.ObserveDecksUseCase
import com.example.flashword.presentation.dashboard.deck.DeckState
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val addNewDeckUseCase: AddNewDeckUseCase,
    private val observeDecksUseCase: ObserveDecksUseCase,
): FlashAppViewModel() {
    private val _state = MutableStateFlow(DashboardUiState())
    val state = _state.asStateFlow()

    private var currentJob: Job? = null

    init {
        synchronize()
    }

    fun synchronize() {
        currentJob?.cancel()

        viewModelScope.launch {
            observeDecksUseCase().collect { decks ->
                _state.update { currentState ->
                    currentState.copy(
                        cardDecks = decks.map { DeckState(
                            deckId = it.deckId,
                            userId = it.userId,
                            title = it.title,
                            totalCards = it.totalCards.toInt(),
                            newCards = it.newCards.toInt(),
                            cardsToStudy = it.cardsToStudy.toInt(),
                            cardsToReview = it.cardsToReview.toInt(),
                            todayDue = it.todayDue.toInt()
                        )
                        }
                    )
                }
            }
        }
    }

    fun addDeck(title: String) {
        launchCatching {
            addNewDeckUseCase(title)
        }
    }
}