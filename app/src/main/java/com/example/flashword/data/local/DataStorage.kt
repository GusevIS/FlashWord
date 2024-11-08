package com.example.flashword.data.local

import kotlinx.coroutines.flow.Flow

interface DataStorage {
    suspend fun getString(key: String): String?
    suspend fun setString(key: String, value: String)
}