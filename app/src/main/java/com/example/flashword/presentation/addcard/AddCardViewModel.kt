package com.example.flashword.presentation.addcard

import com.example.flashword.FlashAppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class AddCardViewModel @Inject constructor(

): FlashAppViewModel() {
    private val _state = MutableStateFlow(AddCardState())
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


}