package id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.topup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rifqipadisiliwangi.core.data.remote.data.payment.ResponsePaymentItem
import id.rifqipadisiliwangi.core.domain.usecase.AppPreferencesInteractor
import id.rifqipadisiliwangi.core.domain.usecase.FirebaseInteractor
import kotlinx.coroutines.launch

class TopUpViewModel(
    private val interactorPrefs :AppPreferencesInteractor ,
    private val interactor: FirebaseInteractor
) : ViewModel() {

    private val _stateTopUpMethod  = MutableLiveData<Boolean>()
    val stateTopUpMethod : LiveData<Boolean> = _stateTopUpMethod

    private val _remoteResult = MutableLiveData<Array<ResponsePaymentItem.ResponsePaymentItemItem>>()
    val remoteResult : LiveData<Array<ResponsePaymentItem.ResponsePaymentItemItem>> = _remoteResult

    private val _remoteResultUpdate = MutableLiveData<Array<ResponsePaymentItem.ResponsePaymentItemItem>>()
    val remoteResultUpdate : LiveData<Array<ResponsePaymentItem.ResponsePaymentItemItem>> = _remoteResultUpdate

    private val _isTokenBalance  = MutableLiveData<String>()
    val isTokenBalance : LiveData<String> = _isTokenBalance

    fun updateStateMethod(state : Boolean){
        viewModelScope.launch {
            _stateTopUpMethod.postValue(state)
        }
    }

    fun getTokenBalance(){
        viewModelScope.launch {
            val token = interactorPrefs.getTokenBalance()
            _isTokenBalance.postValue(token)
        }
    }

    fun saveTokenBalance(token : String){
        viewModelScope.launch {
            interactorPrefs.saveTokenBalance(token)
        }
    }

    fun fetchData() {
        interactor.getPaymentConfig { paymentConfig ->
            _remoteResult.postValue(paymentConfig)
        }
    }

    fun fetchUpdate(){
        interactor.getUpdatePaymentConfig { updatePayment ->
            _remoteResultUpdate.postValue(updatePayment)
        }
    }
}