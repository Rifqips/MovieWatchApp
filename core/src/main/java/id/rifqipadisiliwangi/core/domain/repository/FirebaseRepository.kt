package id.rifqipadisiliwangi.core.domain.repository

import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import id.rifqipadisiliwangi.core.R
import id.rifqipadisiliwangi.core.data.datasource.AppPreferencesDataSource
import id.rifqipadisiliwangi.core.data.remote.data.payment.ResponsePaymentItem
import id.rifqipadisiliwangi.core.domain.state.UiState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.UUID

interface FirebaseRepository {
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

class FirebaseRepositoryImpl(
    private val preferencesSource : AppPreferencesDataSource,
    private val firebaseAnalytics: FirebaseAnalytics
) : FirebaseRepository{

    private val remoteConfig: FirebaseRemoteConfig by lazy {
        Firebase.remoteConfig
    }

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    private val storageReference = FirebaseStorage.getInstance().reference
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference.child("users")

    override suspend fun registerUser(email: String, password: String): Boolean {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    UiState.Success(task.isSuccessful)
                } else{
                    task.exception?.let { UiState.Error(it) }
                }
            }
        return true
    }

    /**
     * using callback flow because needs to emit(trySend) data from repository
     */
    override suspend fun loginUser(email: String, password: String): Flow<UiState<Boolean>> {
        return callbackFlow {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val uiState = UiState.Success(task.isSuccessful)
                        trySend(uiState)
                    } else {
                        val taskError = task.exception?.let { UiState.Error(it) }
                        trySend(UiState.Success(false))
                    }
                }
            awaitClose()
        }
    }

    override suspend fun uploadImage(uri: Uri): Uri? {
        val ref = storageReference.child("images/${UUID.randomUUID()}")
        var downloadUrl: Uri? = null
        ref.putFile(uri).addOnSuccessListener { taskSnapshot ->
            ref.downloadUrl.addOnSuccessListener { uri ->
                runBlocking {
                    preferencesSource.saveUrl(uri.toString())
                }
            }.addOnFailureListener { _ ->
            }
        }.addOnFailureListener { e ->
            Log.e("FirebaseRepositoryLog", "Upload failed: $e")
        }.await()

        return downloadUrl
    }

    override fun checkEmailExistence(email: String, onComplete: (Boolean) -> Unit) {
        database.child(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val isEmailExist = snapshot.exists()
                onComplete(isEmailExist)
            }

            override fun onCancelled(error: DatabaseError) {
                onComplete(false)
            }
        })
    }
    override fun debugSreenView(debug: String) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, Bundle().apply { putString("screen_view",debug) })
    }

    override fun logEvent(eventName: String, bundle: Bundle) {
        firebaseAnalytics.logEvent(eventName, bundle)
    }
    override fun logExeception(exception: Exception) {
        FirebaseCrashlytics.getInstance().recordException(exception)
    }

    override fun getPaymentConfig(callback: (Array<ResponsePaymentItem.ResponsePaymentItemItem>) -> Unit) {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val gson = Gson()
                val stringJson = remoteConfig.getString("list_payment")
                if (stringJson.isNotEmpty()) {
                    val jsonModel = gson.fromJson(stringJson, Array<ResponsePaymentItem.ResponsePaymentItemItem>::class.java)
                    callback(jsonModel)
                }
            }
        }
    }

    override fun getUpdatePaymentConfig(callback: (Array<ResponsePaymentItem.ResponsePaymentItemItem>) -> Unit) {
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                if (configUpdate.updatedKeys.contains("list_payment")) {
                    remoteConfig.activate().addOnCompleteListener {
                        val gson = Gson()
                        val stringJson = remoteConfig.getString("list_payment")
                        val jsonModel = gson.fromJson(stringJson, Array<ResponsePaymentItem.ResponsePaymentItemItem>::class.java)
                        callback(jsonModel)
                    }
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
            }
        })
    }
    override suspend fun getUserEmail(): String? {
        return try {
            auth.currentUser?.email
        } catch (e: Exception) {
            null
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override fun logoutUser(callback: () -> Unit) {
        auth.signOut()
        callback()
    }

    fun createOrUpdateItem(id: Int, name: String, description: String, callback: (Boolean) -> Unit) {
        val itemMap = mapOf(
            "id" to id,
            "name" to name,
            "description" to description
        )
        database.child(id.toString()).setValue(itemMap)
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }

    fun deleteItem(id: Int, callback: (Boolean) -> Unit) {
        database.child(id.toString()).removeValue()
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }

    fun getItem(id: Int, callback: (DataSnapshot?) -> Unit) {
        database.child(id.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }
}