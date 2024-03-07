package id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import id.rifqipadisiliwangi.core.domain.model.nowplaying.NowPlayingResponse
import id.rifqipadisiliwangi.core.domain.model.popular.PopularResponse
import id.rifqipadisiliwangi.core.domain.paging.nowplaying.NowPlayingPagingSource
import id.rifqipadisiliwangi.core.domain.state.UiState
import id.rifqipadisiliwangi.core.domain.usecase.AppPreferencesInteractor
import id.rifqipadisiliwangi.core.domain.usecase.FirebaseInteractor
import id.rifqipadisiliwangi.core.domain.usecase.MovieInteractor
import id.rifqipadisiliwangi.movieapp.presentation.common.adapter.nowplaying.AdapterNowPlaying
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeViewModel(
    private val interactorFirebaseInteractor: FirebaseInteractor,
    private val interactor: MovieInteractor,
    private val preferencesInteractor: AppPreferencesInteractor
) : ViewModel() {

    private val _stateLogOut = MutableLiveData<Boolean>()
    val stateLoggOut : LiveData<Boolean> = _stateLogOut

    var appLocaleLiveData = preferencesInteractor.getStateLocale().asLiveData(Dispatchers.IO)

    private val _listNowPlaying : MutableStateFlow<UiState<NowPlayingResponse>> = MutableStateFlow(UiState.Empty)
    val listNowPlaying = _listNowPlaying.asStateFlow()

    private val _listPopular : MutableStateFlow<UiState<PopularResponse>> = MutableStateFlow(UiState.Empty)
    val listPopular = _listPopular.asStateFlow()

    private val _isUrl  = MutableLiveData<String>()
    val isUrl : LiveData<String> = _isUrl


    private val _isLoacation = MutableLiveData<String>()
    val isLocation : LiveData<String> = _isLoacation

    private val _isUsername = MutableLiveData<String>()
    val isUsername : LiveData<String> = _isUsername

    private val _userEmail = MutableLiveData<String?>()
    val userEmail: MutableLiveData<String?> = _userEmail

    private val _isTokenBalance  = MutableLiveData<String>()
    val isTokenBalance : LiveData<String> = _isTokenBalance

    fun setLocale(isActive : Boolean){
        viewModelScope.launch {
            preferencesInteractor.saveStateLocale(isActive)
        }
    }

    fun getTokenBalance(){
        viewModelScope.launch {
            val token = preferencesInteractor.getTokenBalance()
            _isTokenBalance.postValue(token)
        }
    }

    fun fetchLocalMoviePopular() = runBlocking {
        interactor.fetchLocalProducts()
    }

    fun getUrl(){
        viewModelScope.launch {
            val url = preferencesInteractor.getUrl()
            _isUrl.postValue(url)
        }
    }

    val nowPlayingList = Pager(PagingConfig(pageSize = 4)) {
        NowPlayingPagingSource(interactor)
    }.liveData.cachedIn(viewModelScope)

    fun getListNowPlaying(adapter: AdapterNowPlaying,lang : String) {
        viewModelScope.launch {
            val response = interactor.getNowPlaying(
                lang = lang,
                pageItem = 1
            )
            response.let {
                val store = it.results
                adapter.submitData(PagingData.from(store))
            }
        }
    }

    fun fetchUserEmail() {
        viewModelScope.launch {
            val email = interactorFirebaseInteractor.getUserEmail()
            _userEmail.value = email
        }
    }
    fun getLocation(){
        viewModelScope.launch {
            val location = preferencesInteractor.getLocation()
            _isLoacation.postValue(location)
        }
    }
    fun getUsername(){
        viewModelScope.launch {
            val username = preferencesInteractor.getUsername()
            _isUsername.postValue(username)
        }
    }

    fun logoutUser() {
        interactorFirebaseInteractor.logoutUser {
            _stateLogOut.postValue(true)
        }
    }
}