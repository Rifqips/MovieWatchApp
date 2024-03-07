package id.rifqipadisiliwangi.core.data.local.database.cart

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.rifqipadisiliwangi.core.data.local.database.movie.MovieKey
import id.rifqipadisiliwangi.core.data.local.database.movie.RemoteKeys
import kotlinx.coroutines.flow.Flow


@Dao
interface CartDao {

    @Query("UPDATE cart_keys_migrate_two SET isChecked = :isChecked WHERE id = :id ")
    fun isChecked(id: String, isChecked: Boolean)
    @Query("UPDATE cart_keys_migrate_two SET isChecked = :isChecked")
    fun updateAllItemsCheckedStatus(isChecked: Boolean)
    @Query("UPDATE cart_keys_migrate_two SET isChecked = :newValue")
    suspend fun updateBooleanColumnAll(newValue: Boolean)

    @Query("UPDATE cart_keys_migrate_two SET id = :quantity WHERE id = :id ")
    fun quantityCart(id: String, quantity: Int)

    @Query("UPDATE cart_keys_migrate_two SET isChecked = :isChecked")
    fun checkAll(isChecked: Boolean)

    @Query(
        "INSERT OR REPLACE INTO cart_keys_migrate_two (id, " +
                "backdrop_path, " +
                "original_language, " +
                "original_title, " +
                "overview, " +
                "popularity, " +
                "poster_path, " +
                "release_date, " +
                "isChecked) values (:id, :backdropPath, :originalLanguage,:originalTitle, :overview, :popularity, :posterPath, :releaseDate, :isChecked)"
    )
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

    @Query("DELETE FROM cart_keys_migrate_two WHERE id = :id")
    fun deleteCartId(id: String)

    @Query("DELETE FROM cart_keys_migrate_two")
    fun deleteAllCart()

    @Delete
    suspend fun deleteAllCheckedProduct(cartEntity: List<CartKey>)

    @Query("SELECT * FROM cart_keys_migrate_two")
    fun getCartList(): LiveData<List<CartKey>>

    @Query("SELECT * FROM cart_keys_migrate_two WHERE id = :id")
    fun getCartById(id: String): Flow<List<CartKey>>

    /***
     * for paging data
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(product: List<MovieKey>)

    @Query("SELECT * FROM movie_keys_migrate_two")
    fun retrieveAllMovies():PagingSource<Int, MovieKey>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKey(remoteKey: List<RemoteKeys>)

    @Query("DELETE FROM movie_keys_migrate_two")
    suspend fun deleteAll()

    @Query("SELECT * FROM remote_keys_migrate_two WHERE id = :id")
    suspend fun getRemoteKeysId(id:String): RemoteKeys?

    @Query("DELETE FROM remote_keys_migrate_two")
    suspend fun deleteAllKeys()
}