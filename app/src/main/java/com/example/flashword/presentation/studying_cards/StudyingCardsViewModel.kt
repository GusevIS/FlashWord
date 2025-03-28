package com.example.flashword.presentation.studying_cards

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.flashword.FlashAppViewModel
import com.example.flashword.data.source.FIRESTORE_LOG
import com.example.flashword.domain.model.CardModel
import com.example.flashword.domain.usecases.AddNewCardUseCase
import com.example.flashword.domain.usecases.GetCardsByDeckUseCase
import com.example.flashword.domain.usecases.GetCardsForReviewUseCase
import com.example.flashword.domain.usecases.RecallQuality
import com.example.flashword.domain.usecases.ScheduleNextReviewUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudyingCardsViewModel (
    private val deckId: String,
    deckTitle: String,
    private val getCardsForReviewUseCase: GetCardsForReviewUseCase,
    private val scheduleNextReviewUseCase: ScheduleNextReviewUseCase
): FlashAppViewModel() {
    private val _state = MutableStateFlow(StudyingCardsState(deckId, deckTitle))
    val state = _state.asStateFlow()
    private var cards: List<CardModel> = emptyList()
    private var wrongAnswers: List<CardModel> = emptyList()
    private var cardIndex: Int = -1

    init {
        viewModelScope.launch {
            cards = getCardsForReviewUseCase(deckId).shuffled()
            _state.value = _state.value.copy(totalCards = cards.size)
            onNextCard()

        }
    }

    private fun onNextCard() {
        if (cardIndex < cards.lastIndex) {
            _state.value = _state.value.copy(card = cards[++cardIndex], progress = cardIndex / cards.size.toFloat(), isBackSide = false, cardIndex = cardIndex + 1)
        } else _state.value = _state.value.copy(reviewingEnded = true, isBackSide = false, progress = 1f)

    }

    fun processCardAnswer(recall: RecallQuality) {
        launchCatching {
            scheduleNextReviewUseCase(cards[cardIndex], recall)
        }

        onNextCard()
    }

    fun rotate() {
        val isBackSide = _state.value.isBackSide
        _state.value = _state.value.copy(isBackSide = !isBackSide)
    }


    class StudyingCardsViewModelFactory @AssistedInject constructor(
        @Assisted("deckId") private val deckId: String,
        @Assisted("deckTitle") private val deckTitle: String,
        private val getCardsForReviewUseCase: GetCardsForReviewUseCase,
        private val scheduleNextReviewUseCase: ScheduleNextReviewUseCase
    ): ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(StudyingCardsViewModel::class.java))
            return StudyingCardsViewModel(deckId, deckTitle, getCardsForReviewUseCase, scheduleNextReviewUseCase) as T
        }

        @AssistedFactory
        interface Factory {

            fun create(@Assisted("deckId") deckId: String, @Assisted("deckTitle") deckTitle: String): StudyingCardsViewModelFactory

        }
    }
}