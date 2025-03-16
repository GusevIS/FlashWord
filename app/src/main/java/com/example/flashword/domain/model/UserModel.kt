package com.example.flashword.domain.model

data class UserModel(
    val name: String? = "",
    val pictureUrl: String? = "",
    val id: String = "",
    val email: String? = ""
)