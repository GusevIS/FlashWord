package com.example.flashword.presentation.dashboard

import com.example.flashword.FlashAppViewModel
import com.example.flashword.domain.model.DeckModel
import com.example.flashword.domain.repos.AccountService
import com.example.flashword.domain.repos.DecksRepository
import com.example.flashword.presentation.addcard.AddCardScreen
import com.example.flashword.presentation.dashboard.deck.DeckState
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val accountService: AccountService,
    private val decksRepository: DecksRepository,
): FlashAppViewModel() {
    private val _state = MutableStateFlow(DashboardUiState())
    val state = _state.asStateFlow()

    private var listenerRegistration: ListenerRegistration? = null

    init {
        listenerRegistration = decksRepository.observeDecks(accountService.currentUserId) { decks ->
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

    override fun onCleared() {
        super.onCleared()
        listenerRegistration?.remove()
    }

    fun addDeck(title: String) {
        launchCatching {
            decksRepository.addDeck(DeckModel(accountService.currentUserId, title))
        }
    }

}