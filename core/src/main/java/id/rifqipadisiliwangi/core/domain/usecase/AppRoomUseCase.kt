package id.rifqipadisiliwangi.core.domain.usecase

import androidx.lifecycle.LiveData
import id.rifqipadisiliwangi.core.data.local.database.cart.CartKey
import id.rifqipadisiliwangi.core.data.local.database.notification.NotificationKey
import id.rifqipadisiliwangi.core.data.local.database.transaction.TransactionKey
import id.rifqipadisiliwangi.core.data.local.database.wishlist.WishlistKey
import id.rifqipadisiliwangi.core.domain.model.cart.KeyCart
import id.rifqipadisiliwangi.core.domain.model.wishlist.KeyWishlist
import id.rifqipadisiliwangi.core.domain.repository.AppRoomRepository
import kotlinx.coroutines.flow.Flow

class AppRoomInteractor(private val roomRepository: AppRoomRepository) {

    fun updateAllItemsCheckedStatus(isChecked: Boolean) {
        return roomRepository.updateAllItemsCheckedStatus(isChecked)
    }
    fun isChecked(id: String, isChecked: Boolean) = roomRepository.isChecked(id, isChecked)
    fun addCartList(
        id: Int,
        backdropPath: String,
        originalLanguage: String,
        originalTitle: String,
        overview: String,
        popularity: String,
        posterPath: String,
        releaseDate: String,
        isChecked: Boolean
    ) = roomRepository.addCartList(id, backdropPath, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, isChecked)

    fun deleteCartId(id: String) = roomRepository.deleteCartId(id)
    fun getCartById(id: String): Flow<List<CartKey>> = roomRepository.getCartById(id)


    fun getCartList(): LiveData<List<KeyCart>> = roomRepository.getCartList()

    fun deleteAllCart() = roomRepository.deleteAllCart()
    suspend fun updateBooleanColumnAll(newValue: Boolean) = roomRepository.updateBooleanColumnAll(newValue)


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
    ) = roomRepository.addWishlist(id, backdropPath, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, isChecked)

    fun deleteWishlistId(id: String) = roomRepository.deleteWishlistId(id)

    fun deleteAllWishlist() = roomRepository.deleteAllWishlist()

    fun getWishlistById(id: String): Flow<List<WishlistKey>> = roomRepository.getWishlistById(id)

    fun getWislistList(): LiveData<List<KeyWishlist>> = roomRepository.getWislistList()


    suspend fun createNotification(
        notifId: Int,
        notifType: String,
        notifTitle: String,
        notifBody: String,
        notifDate: String,
        notifTime: String,
        notifImage: String,
        isChecked: Boolean
    ) = roomRepository.createNotification(notifId, notifType, notifTitle, notifBody, notifDate, notifTime, notifImage, isChecked)

    fun getNotifications(): LiveData<List<NotificationKey>?> = roomRepository.getNotifications()

    fun notifIsChecked(id: Int, isChecked: Boolean) =  roomRepository.notifIsChecked(id, isChecked)

    fun insertTransaction(transaction: List<TransactionKey>) = roomRepository.insertTransaction(transaction)

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
    ) = roomRepository.addTransaction(id, backdropPath, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, isChecked)


    fun deleteTransactionById(id: String) = roomRepository.deleteTransactionById(id)

    fun deleteAllTransaction() = roomRepository.deleteAllTransaction()

    fun getTransactionList(): LiveData<List<TransactionKey>> = roomRepository.getTransactionList()
}