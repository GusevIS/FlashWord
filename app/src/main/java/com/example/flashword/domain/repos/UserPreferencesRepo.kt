package com.example.flashword.domain.repos

interface UserPreferencesRepo {
    suspend fun getUserName(): Result<String>
    suspend fun setUserName(value: String)

    suspend fun getUserId(): Result<String>
    suspend fun setUserId(value: String)

    suspend fun getProfilePictureUrl(): Result<String>
    suspend fun setProfilePictureUrl(value: String)
}