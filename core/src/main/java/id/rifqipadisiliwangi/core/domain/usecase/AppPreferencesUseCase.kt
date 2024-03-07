package id.rifqipadisiliwangi.core.domain.usecase

import id.rifqipadisiliwangi.core.domain.repository.AppPreferencesRepositoryImpl
import kotlinx.coroutines.flow.Flow

interface AppPreferencesUseCase {

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

class AppPreferencesInteractor(private val preferencesRepository : AppPreferencesRepositoryImpl) : AppPreferencesUseCase{
    override fun getStateLocale(): Flow<Boolean> {
        return preferencesRepository.getStateLocale()
    }

    override suspend fun saveStateLocale(isActive: Boolean) {
        return preferencesRepository.saveStateLocale(isActive)
    }

    override suspend fun getUrl(): String {
        return preferencesRepository.getUrl()
    }

    override suspend fun saveUrl(url: String) {
        return preferencesRepository.saveUrl(url)
    }

    override suspend fun getLocation(): String {
        return preferencesRepository.getLocation()
    }

    override suspend fun saveLocation(location: String) {
        return preferencesRepository.saveLocation(location)
    }

    override suspend fun getUsername(): String {
        return preferencesRepository.getUsername()
    }

    override suspend fun saveUsername(username: String) {
        return preferencesRepository.saveUsername(username)
    }

    override suspend fun getTokenBalance(): String {
        return preferencesRepository.getTokenBalance()
    }

    override suspend fun saveTokenBalance(token: String) {
        return preferencesRepository.saveTokenBalance(token)
    }


}