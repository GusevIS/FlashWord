package com.example.flashword.presentation.addcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flashword.FlashAppViewModel
import com.example.flashword.domain.repos.CardsRepository
import com.example.flashword.domain.repos.DecksRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Qualifier

class AddCardViewModel (
    private val deckId: String,
    private val cardsRepository: CardsRepository
): FlashAppViewModel() {
    private val _state = MutableStateFlow(AddCardState(deckId))
    val state = _state.asStateFlow()

    fun updateFrontText(text: String) {
        _state.value = _state.value.copy(frontText = text)
    }

    fun updateBackText(text: String) {
        _state.value = _state.value.copy(backText = text)
    }

    fun rotate() {
        val isBackSide = _state.value.isBackSide
        _state.value = _state.value.copy(isBackSide = !isBackSide)
    }

    fun addCard() {

    }


    class AddCardViewModelFactory @AssistedInject constructor(
        @Assisted private val deckId: String,
        private val cardsRepository: CardsRepository
    ): ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(AddCardViewModel::class.java))
            return AddCardViewModel(deckId, cardsRepository) as T
        }

        @AssistedFactory
        interface Factory {

            fun create(@Assisted deckId: String): AddCardViewModelFactory

        }
    }
}