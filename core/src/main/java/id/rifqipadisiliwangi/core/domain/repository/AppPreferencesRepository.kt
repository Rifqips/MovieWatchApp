package id.rifqipadisiliwangi.core.domain.repository

import id.rifqipadisiliwangi.core.data.datasource.AppPreferencesDataSource
import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {

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

class AppPreferencesRepositoryImpl(private val preferencesDataSource: AppPreferencesDataSource) : AppPreferencesRepository{
    override fun getStateLocale(): Flow<Boolean> {
        return preferencesDataSource.getStateLocale()
    }

    override suspend fun saveStateLocale(isActive: Boolean) {
        return preferencesDataSource.saveStateLocale(isActive)
    }


    override suspend fun getUrl(): String {
        return preferencesDataSource.getUrl()
    }

    override suspend fun saveUrl(url: String) {
        return preferencesDataSource.saveUrl(url)
    }

    override suspend fun getLocation(): String {
        return preferencesDataSource.getLocation()
    }

    override suspend fun saveLocation(location: String) {
        return preferencesDataSource.saveLocation(location)
    }

    override suspend fun getUsername(): String {
        return preferencesDataSource.getUsername()
    }

    override suspend fun saveUsername(username: String) {
        return preferencesDataSource.saveUsername(username)
    }

    override suspend fun getTokenBalance(): String {
        return preferencesDataSource.getTokenBalance()
    }

    override suspend fun saveTokenBalance(token: String) {
        return preferencesDataSource.saveTokenBalance(token)
    }

}