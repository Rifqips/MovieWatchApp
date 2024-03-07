package id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.cart

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rifqipadisiliwangi.core.data.local.database.cart.CartKey
import id.rifqipadisiliwangi.core.data.local.database.transaction.TransactionKey
import id.rifqipadisiliwangi.core.domain.model.cart.KeyCart
import id.rifqipadisiliwangi.core.domain.usecase.AppPreferencesInteractor
import id.rifqipadisiliwangi.core.domain.usecase.AppRoomInteractor
import id.rifqipadisiliwangi.core.domain.usecase.FirebaseInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CartViewModel(
    private val interactor: AppRoomInteractor,
    private val preferencesInteractor: AppPreferencesInteractor,
    private val interactorFirebase : FirebaseInteractor

):ViewModel() {

    private val _cartList = MutableLiveData<List<KeyCart>>()
    val cartList : LiveData<List<KeyCart>> =_cartList

    private val _getIsCartKey = MutableLiveData<List<CartKey>>()
    val getIsCartKey : LiveData<List<CartKey>>
        get() = _getIsCartKey


    private val _transactionList = MutableLiveData<List<TransactionKey>>()
    val transactionList : LiveData<List<TransactionKey>> =_transactionList

    private val _isTokenBalance  = MutableLiveData<String>()
    val isTokenBalance : LiveData<String> = _isTokenBalance

    private val _isChecked = MutableLiveData<Boolean>()
    val isChecked : LiveData<Boolean> = _isChecked

    fun isChecked(id: String, isChecked: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
             interactor.isChecked(id, isChecked)
        }
    }

    fun getFavoriteById(id : String){
        viewModelScope.launch {
            interactor.getCartById(id).collect{
                _getIsCartKey.postValue(it)
            }
        }
    }

    fun debugSreenView(debug : String){
        interactorFirebase.debugSreenView(debug)
    }
    fun logEvent(eventName : String, bundle : Bundle){
        interactorFirebase.logEvent(eventName, bundle)
    }
    fun getTokenBalance(){
        viewModelScope.launch {
            val token = preferencesInteractor.getTokenBalance()
            _isTokenBalance.postValue(token)
        }
    }

    fun saveTokenBalance(token : String){
        viewModelScope.launch {
            preferencesInteractor.saveTokenBalance(token)
        }
    }
    fun insertTransaction(transaction: List<TransactionKey>){
        viewModelScope.launch(Dispatchers.IO){
            interactor.insertTransaction(transaction)
        }
    }

    fun addTransaction(
        id: Int,
        backdropPath: String,
        originalLanguage: String,
        originalTitle: String,
        overview: String,
        popularity: String,
        posterPath: String,
        releaseDate: String,
        isChecked: Boolean
    ){
        viewModelScope.launch(Dispatchers.IO) {
            interactor.addTransaction(id,backdropPath, originalLanguage, originalTitle, overview, popularity, posterPath,releaseDate, isChecked)
        }
    }

    fun getTransactionList(){
        viewModelScope.launch {
            interactor.getTransactionList().observeForever {
                _transactionList.postValue(it)
            }
        }
    }

    fun addCart(
        id: Int,
        backdropPath: String,
        originalLanguage: String,
        originalTitle: String,
        overview: String,
        popularity: String,
        posterPath: String,
        releaseDate: String,
        isChecked: Boolean
    ){
        viewModelScope.launch(Dispatchers.IO) {
            interactor.addCartList(id,backdropPath, originalLanguage, originalTitle, overview, popularity, posterPath,releaseDate, isChecked)
        }
    }
    fun deleteAllcart(){
        viewModelScope.launch(Dispatchers.IO) {
            interactor.deleteAllCart()
        }
    }
    fun updateBooleanColumnAll(newvalue : Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            interactor.updateBooleanColumnAll(newvalue)
        }
    }

    fun getCartList(){
        viewModelScope.launch {
            interactor.getCartList().observeForever {
                _cartList.postValue(it)
            }
        }
    }

    fun deleteById(id : String){
        viewModelScope.launch(Dispatchers.IO) {
            interactor.deleteCartId(id)
        }
    }

    fun deleteByIdTransaction(id : String){
        viewModelScope.launch(Dispatchers.IO) {
            interactor.deleteTransactionById(id)
        }
    }
}
