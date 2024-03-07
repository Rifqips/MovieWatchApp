package id.rifqipadisiliwangi.core.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.Query
import id.rifqipadisiliwangi.core.data.local.database.cart.CartDao
import id.rifqipadisiliwangi.core.data.local.database.cart.CartKey
import id.rifqipadisiliwangi.core.data.local.database.notification.NotificationDao
import id.rifqipadisiliwangi.core.data.local.database.notification.NotificationKey
import id.rifqipadisiliwangi.core.data.local.database.transaction.TransactionDao
import id.rifqipadisiliwangi.core.data.local.database.transaction.TransactionKey
import id.rifqipadisiliwangi.core.data.local.database.wishlist.WishlistDao
import id.rifqipadisiliwangi.core.data.local.database.wishlist.WishlistKey
import id.rifqipadisiliwangi.core.domain.model.cart.KeyCart
import id.rifqipadisiliwangi.core.domain.model.wishlist.KeyWishlist
import id.rifqipadisiliwangi.core.utils.DataMapper.toCartKeyList
import id.rifqipadisiliwangi.core.utils.DataMapper.toWishlistList
import kotlinx.coroutines.flow.Flow

interface AppRoomRepository {
    fun isChecked(id: String, isChecked: Boolean)
    fun updateAllItemsCheckedStatus(isChecked: Boolean)

    fun addCartList(
        id: Int,
        backdropPath: String,
        originalLanguage: String,
        originalTitle: String,
        overview: String,
        popularity: String,
        posterPath: String,
        releaseDate: String,
        isChecked: Boolean,
    )

    fun deleteCartId(id: String)

    fun deleteAllCart()

    fun getCartById(id: String): Flow<List<CartKey>>

    fun getCartList(): LiveData<List<KeyCart>>
    suspend fun updateBooleanColumnAll(newValue: Boolean)

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
    )

    fun deleteWishlistId(id: String)

    fun deleteAllWishlist()

    fun getWishlistById(id: String): Flow<List<WishlistKey>>

    fun getWislistList(): LiveData<List<KeyWishlist>>

    suspend fun createNotification(
        notifId: Int,
        notifType: String,
        notifTitle: String,
        notifBody: String,
        notifDate: String,
        notifTime: String,
        notifImage: String,
        isChecked: Boolean
    )

    fun getNotifications(): LiveData<List<NotificationKey>?>

    fun notifIsChecked(id: Int, isChecked: Boolean)


    fun insertTransaction(transaction: List<TransactionKey>)

    fun addTransaction(
        id: Int,
        backdropPath: String,
        originalLanguage: String,
        originalTitle: String,
        overview: String,
        popularity: String,
        posterPath: String,
        releaseDate: String,
        isChecked: Boolean,
    )

    fun deleteTransactionById(id: String)


    fun deleteAllTransaction()


    fun getTransactionList(): LiveData<List<TransactionKey>>


    fun getTransactionById(id: String): Flow<List<TransactionKey>>
}

class AppRoomRepositoryImpl(
    private val cartDao : CartDao,
    private val wishlistDao : WishlistDao,
    private val notificationDao: NotificationDao,
    private val transactionDao : TransactionDao,
) : AppRoomRepository{
    override fun isChecked(id: String, isChecked: Boolean) {
        return cartDao.isChecked(id, isChecked)
    }

    override fun updateAllItemsCheckedStatus(isChecked: Boolean) {
        return cartDao.updateAllItemsCheckedStatus(isChecked)
    }

    override fun addCartList(
        id: Int,
        backdropPath: String,
        originalLanguage: String,
        originalTitle: String,
        overview: String,
        popularity: String,
        posterPath: String,
        releaseDate: String,
        isChecked: Boolean
    ) {
        return cartDao.addCartList(id, backdropPath, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, isChecked)
    }

    override fun deleteCartId(id: String) {
        return cartDao.deleteCartId(id)
    }

    override fun deleteAllCart() {
        return cartDao.deleteAllCart()
    }

    override fun getCartById(id: String): Flow<List<CartKey>> {
        return cartDao.getCartById(id)
    }

    override fun getCartList(): LiveData<List<KeyCart>> {
        return cartDao.getCartList().map { cartKeys -> cartKeys.toCartKeyList()  }
    }

    override suspend fun updateBooleanColumnAll(newValue: Boolean) {
        return cartDao.updateBooleanColumnAll(newValue)
    }

    override fun addWishlist(
        id: Int,
        backdropPath: String,
        originalLanguage: String,
        originalTitle: String,
        overview: String,
        popularity: Double,
        posterPath: String,
        releaseDate: String,
        isChecked: Boolean
    ) {
        return wishlistDao.addWishList(id, backdropPath, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, isChecked)
    }

    override fun deleteWishlistId(id: String) {
        return wishlistDao.deleteProduct(id)
    }

    override fun deleteAllWishlist() {
        return wishlistDao.deleteAllWishlist()
    }

    override fun getWishlistById(id: String): Flow<List<WishlistKey>> {
        return wishlistDao.getWishlistById(id)
    }

    override fun getWislistList(): LiveData<List<KeyWishlist>> {
        return wishlistDao.getWishlistList().map { wishlist -> wishlist.toWishlistList() }
    }

    override suspend fun createNotification(
        notifId: Int,
        notifType: String,
        notifTitle: String,
        notifBody: String,
        notifDate: String,
        notifTime: String,
        notifImage: String,
        isChecked: Boolean
    ) {
        return notificationDao.createNotification(notifId, notifType, notifTitle, notifBody, notifDate, notifTime, notifImage, isChecked)
    }

    override fun getNotifications(): LiveData<List<NotificationKey>?> {
        return notificationDao.getNotifications()
    }

    override fun notifIsChecked(id: Int, isChecked: Boolean) {
        return notificationDao.notifIsChecked(id, isChecked)
    }

    override fun insertTransaction(transaction: List<TransactionKey>) {
        return transactionDao.insertTransaction(transaction)
    }

    override fun addTransaction(
        id: Int,
        backdropPath: String,
        originalLanguage: String,
        originalTitle: String,
        overview: String,
        popularity: String,
        posterPath: String,
        releaseDate: String,
        isChecked: Boolean
    ) {
        return transactionDao.addTransaction(id, backdropPath, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, isChecked)
    }

    override fun deleteTransactionById(id: String) {
        return transactionDao.deleteTransactionById(id)
    }

    override fun deleteAllTransaction() {
        return transactionDao.deleteAllTransaction()
    }

    override fun getTransactionList(): LiveData<List<TransactionKey>> {
        return transactionDao.getTransactionList()
    }

    override fun getTransactionById(id: String): Flow<List<TransactionKey>> {
        return transactionDao.getTransactionById(id)
    }

}