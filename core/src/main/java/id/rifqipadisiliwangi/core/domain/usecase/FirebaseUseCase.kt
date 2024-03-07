package id.rifqipadisiliwangi.core.domain.usecase

import android.net.Uri
import android.os.Bundle
import com.google.firebase.auth.FirebaseUser
import id.rifqipadisiliwangi.core.data.remote.data.payment.ResponsePaymentItem
import id.rifqipadisiliwangi.core.domain.repository.FirebaseRepositoryImpl
import id.rifqipadisiliwangi.core.domain.state.UiState
import kotlinx.coroutines.flow.Flow

interface FirebaseUseCase {
    suspend fun registerUser(email: String, password: String) : Boolean
    suspend fun loginUser(email: String, password: String) : Flow<UiState<Boolean>>
    suspend fun uploadImage(uri: Uri): Uri?
    fun checkEmailExistence(email: String, onComplete: (Boolean) -> Unit)
    fun debugSreenView(debug : String)
    fun logEvent(eventName : String, bundle : Bundle)
    fun logExeception(exception: Exception)
    fun getPaymentConfig(callback: (Array<ResponsePaymentItem.ResponsePaymentItemItem>) -> Unit)
    fun getUpdatePaymentConfig(callback: (Array<ResponsePaymentItem.ResponsePaymentItemItem>) -> Unit)
    suspend fun getUserEmail(): String?
    fun getCurrentUser(): FirebaseUser?
    fun isLoggedIn(): Boolean
    fun logoutUser(callback: () -> Unit)

}

class FirebaseInteractor(private val repository: FirebaseRepositoryImpl) : FirebaseUseCase{

    override suspend fun uploadImage(uri: Uri): Uri? {
        return repository.uploadImage(uri)
    }
    override suspend fun registerUser(email: String, password: String): Boolean {
        return repository.registerUser(email, password)
    }

    override suspend fun loginUser(email: String, password: String): Flow<UiState<Boolean>> {
        return repository.loginUser(email, password)
    }

    override fun checkEmailExistence(email: String, onComplete: (Boolean) -> Unit) {
        return repository.checkEmailExistence(email, onComplete)
    }

    override fun debugSreenView(debug: String) {
        return repository.debugSreenView(debug)
    }

    override fun logEvent(eventName: String, bundle: Bundle) {
        return repository.logEvent(eventName, bundle)
    }

    override fun logExeception(exception: Exception) {
        return repository.logExeception(exception)
    }

    override fun getPaymentConfig(callback: (Array<ResponsePaymentItem.ResponsePaymentItemItem>) -> Unit) {
        return repository.getPaymentConfig(callback)
    }

    override fun getUpdatePaymentConfig(callback: (Array<ResponsePaymentItem.ResponsePaymentItemItem>) -> Unit) {
        return repository.getUpdatePaymentConfig(callback)
    }
    override suspend fun getUserEmail(): String? {
        return repository .getUserEmail()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return repository.getCurrentUser()
    }

    override fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }

    override fun logoutUser(callback: () -> Unit) {
        return repository.logoutUser(callback)
    }

}