package com.example.flashword.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

open class PreferencesDataStoreRepo @Inject constructor(
    val userDataStorePreferences: DataStore<Preferences>
) {
    suspend inline fun <T> Preferences.Key<T>.getValueByKey(): Result<T> {
        return Result.runCatching {
            val flow = userDataStorePreferences.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map {
                    it[this@getValueByKey] ?: throw NoSuchElementException("Key not found")
                }
            flow.first()
        }
    }

    suspend inline fun <T> Preferences.Key<T>.setValueByKey(value: T) {
        Result.runCatching {
            Log.d("PREFERENCES_DATASTORE_SAVED", "saved $value")
            userDataStorePreferences.edit { preferences ->
                preferences[this@setValueByKey] = value
            }
        }
    }
}