package com.example.flashword.presentation.dashboard

import androidx.lifecycle.viewModelScope
import com.example.flashword.FlashAppViewModel
import com.example.flashword.domain.repos.AccountService
import com.example.flashword.domain.usecases.AddNewDeckUseCase
import com.example.flashword.domain.usecases.ObserveDecksUseCase
import com.example.flashword.presentation.dashboard.deck.DeckState
import com.google.firebase.firestore.ListenerRegistration
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

    private var listenerRegistration: ListenerRegistration? = null

    init {
        viewModelScope.launch {
            observeDecksUseCase().collect { decks ->
                _state.update { currentState ->
                    currentState.copy(
                        cardDecks = decks.map { DeckState(
                            deckId = it.deckId,
                            userId = it.userId,
                            title = it.title
                        )
                        }
                    )
                }
            }
        }

//        listenerRegistration = decksRepository.observeDecks(accountService.currentUserId) { decks ->
//            _state.update { currentState ->
//                currentState.copy(
//                    cardDecks = decks.map { DeckState(
//                        deckId = it.deckId,
//                        userId = it.userId,
//                        title = it.title
//                    )
//                    }
//                )
//            }
//        }
    }

    override fun onCleared() {
        super.onCleared()
        listenerRegistration?.remove()
    }

    fun addDeck(title: String) {
        launchCatching {
            addNewDeckUseCase(title)
        }
    }
}