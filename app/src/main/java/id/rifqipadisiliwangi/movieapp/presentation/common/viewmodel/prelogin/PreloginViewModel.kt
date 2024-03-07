package id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.prelogin

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.rifqipadisiliwangi.core.domain.state.UiState
import id.rifqipadisiliwangi.core.domain.state.onSuccess
import id.rifqipadisiliwangi.core.domain.usecase.AppPreferencesInteractor
import id.rifqipadisiliwangi.core.domain.usecase.FirebaseInteractor
import id.rifqipadisiliwangi.core.utils.asMutableStateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PreloginViewModel(
    private val preferencesInteractor: AppPreferencesInteractor,
    private val interactor : FirebaseInteractor
):ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _stateLoggedin = MutableLiveData<Boolean>()
    val stateLoggedin : LiveData<Boolean> = _stateLoggedin


    private val _isLoacation = MutableLiveData<String>()
    val isLocation : LiveData<String> = _isLoacation

    private val _loginUser : MutableStateFlow<UiState<Boolean>> = MutableStateFlow(UiState.Empty)
    val loginUser = _loginUser.asStateFlow()


    private val _userRegister : MutableStateFlow<UiState<Boolean>> = MutableStateFlow(UiState.Empty)
    val registerUser = _userRegister.asStateFlow()


    fun addLocation(location : String){
        viewModelScope.launch {
            preferencesInteractor.saveLocation(location)
        }
    }
    fun getLocation(){
        viewModelScope.launch {
            val location = preferencesInteractor.getLocation()
            _isLoacation.postValue(location)
        }
    }

    fun addUsername(username : String){
        viewModelScope.launch {
            preferencesInteractor.saveUsername(username)
        }
    }
    fun uploadImage(uri: Uri) {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            interactor.uploadImage(uri)
            _loading.postValue(false)
        }
    }


    fun loginUser(email: String, password: String){
        viewModelScope.launch {
            interactor.loginUser(email, password).collect{ data ->
                _loginUser.update { data }
            }
        }
    }

    fun register(email: String, password: String){
        viewModelScope.launch {
            _userRegister.asMutableStateFlow {
                interactor.registerUser(email, password)
            }
        }
    }
    fun debugSreenView(debug : String){
        interactor.debugSreenView(debug)
    }
    fun logEvent(eventName : String, bundle : Bundle){
        interactor.logEvent(eventName, bundle)
    }
    fun logExeception(exception: Exception){
        interactor.logExeception(exception)
    }
    fun checkLoginStatus() {
        viewModelScope.launch {
            if (interactor.isLoggedIn()) {
                _stateLoggedin.postValue(true)
            } else {
                _stateLoggedin.postValue(false)
            }
        }
    }

}