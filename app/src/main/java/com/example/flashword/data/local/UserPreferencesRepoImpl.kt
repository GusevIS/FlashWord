package com.example.flashword.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.flashword.domain.repos.UserPreferencesRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepoImpl @Inject constructor(
    userDataStorePreferences: DataStore<Preferences>
): UserPreferencesRepo, PreferencesDataStoreRepo(userDataStorePreferences) {

    override suspend fun getUserId(): Result<String> = KEY_USER_ID.getValueByKey()

    override suspend fun getUserName(): Result<String> = KEY_USER_NAME.getValueByKey()

    override suspend fun getProfilePictureUrl(): Result<String> = KEY_USER_NAME.getValueByKey()

    override suspend fun setUserId(value: String) {
        KEY_USER_ID.setValueByKey(value)
    }

    override suspend fun setUserName(value: String) {
        KEY_USER_NAME.setValueByKey(value)
    }

    override suspend fun setProfilePictureUrl(value: String) {
        KEY_PROFILE_PICTURE_URL.setValueByKey(value)
    }

    private companion object {
        val KEY_USER_ID = stringPreferencesKey("user_id")
        val KEY_USER_NAME = stringPreferencesKey("user_name")
        val KEY_PROFILE_PICTURE_URL = stringPreferencesKey("user_picture_url")
    }

    //    override suspend fun getString(key: String): String? {
//        val preferencesKey = stringPreferencesKey(key)
//        val preferences =  context.dataStore.data.first()
//        return preferences[preferencesKey]
////            .map { preference ->
////                preference[preferencesKey] ?: ""
////            }
//    }
//
//    override suspend fun setString(key: String, value: String) {
//        val preferencesKey = stringPreferencesKey(key)
//        context.dataStore.edit {
//            it[preferencesKey] = value
//        }
//    }
}
