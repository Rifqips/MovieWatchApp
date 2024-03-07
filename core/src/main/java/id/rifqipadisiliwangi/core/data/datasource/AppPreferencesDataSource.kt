package id.rifqipadisiliwangi.core.data.datasource

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import id.rifqipadisiliwangi.core.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow

interface AppPreferencesDataSource {

    fun getStateLocale(): Flow<Boolean>
    suspend fun saveStateLocale(isActive: Boolean)

    suspend fun getUrl(): String

    suspend fun saveUrl(url: String)

    suspend fun getLocation(): String

    suspend fun saveLocation(location: String)

    suspend fun getUsername(): String

    suspend fun saveUsername(username: String)

    suspend fun getTokenBalance(): String

    suspend fun saveTokenBalance(token: String)

}
class AppPreferencesDataSourceImpl(private val preferencesHelper : PreferenceDataStoreHelper) : AppPreferencesDataSource{

    override fun getStateLocale(): Flow<Boolean> {
        return preferencesHelper.getPreference(STATE_LOCALE, true)
    }

    override suspend fun saveStateLocale(isActive: Boolean) {
        return preferencesHelper.putPreference(STATE_LOCALE, isActive)
    }

    override suspend fun getUrl(): String {
        return preferencesHelper.getFirstPreference(USER_URL,"")
    }

    override suspend fun saveUrl(url: String) {
        return preferencesHelper.putPreference(USER_URL, url)
    }

    override suspend fun getLocation(): String {
        return preferencesHelper.getFirstPreference(USER_LOCATION,"")
    }

    override suspend fun saveLocation(location: String) {
        return preferencesHelper.putPreference(USER_LOCATION, location)
    }

    override suspend fun getUsername(): String {
        return preferencesHelper.getFirstPreference(USERNAME,"")
    }

    override suspend fun saveUsername(username: String) {
        return preferencesHelper.putPreference(USERNAME, username)
    }

    override suspend fun getTokenBalance(): String {
        return preferencesHelper.getFirstPreference(TOKEN_BALANCE, EMPTY_SALDO)
    }

    override suspend fun saveTokenBalance(token: String) {
        return preferencesHelper.putPreference(TOKEN_BALANCE, token)
    }

    companion object {
        val USER_URL = stringPreferencesKey("USER_URL")
        val USER_LOCATION = stringPreferencesKey("USER_LOCATION")
        val USERNAME = stringPreferencesKey("USERNAME")
        val TOKEN_BALANCE = stringPreferencesKey("TOKEN_BALANCE")
        val EMPTY_SALDO = "0"
        val STATE_LOCALE = booleanPreferencesKey("STATE_LOCALE")

    }

}