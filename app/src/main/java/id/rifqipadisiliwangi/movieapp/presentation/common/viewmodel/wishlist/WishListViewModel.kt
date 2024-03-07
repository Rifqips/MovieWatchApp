package id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rifqipadisiliwangi.core.data.local.database.wishlist.WishlistKey
import id.rifqipadisiliwangi.core.domain.model.wishlist.KeyWishlist
import id.rifqipadisiliwangi.core.domain.usecase.AppRoomInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WishListViewModel(private val interactor : AppRoomInteractor) : ViewModel() {


    private val _wishlList = MutableLiveData<List<KeyWishlist>>()
    val wishList : LiveData<List<KeyWishlist>> =_wishlList

    private val _getIsWislistKey = MutableLiveData<List<WishlistKey>>()
    val getIsWishlistKey : LiveData<List<WishlistKey>>
        get() = _getIsWislistKey

    fun getFavoriteById(id : String){
        viewModelScope.launch(Dispatchers.IO) {
            interactor.getWishlistById(id).collect{
                _getIsWislistKey.postValue(it)
            }
        }
    }

    fun addWishlist(
        id: Int,
        backdropPath: String,
        originalLanguage: String,
        originalTitle: String,
        overview: String,
        popularity: Double,
        posterPath: String,
        releaseDate: String,
        isChecked: Boolean,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            interactor.addWishlist(id, backdropPath, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, isChecked)
        }
    }

    fun getWishlistList(){
        viewModelScope.launch {
            interactor.getWislistList().observeForever {
                _wishlList.postValue(it)
            }
        }
    }

    fun deleteById(id : String){
        viewModelScope.launch(Dispatchers.IO) {
            interactor.deleteWishlistId(id)
        }
    }

}