package id.rifqipadisiliwangi.core.data.local.database.wishlist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {


    @Query("UPDATE wishlist_keys_migrate_two SET isChecked = :isChecked WHERE id = :id ")
    fun isChecked(id: String, isChecked: Boolean)

    @Query("UPDATE wishlist_keys_migrate_two SET isChecked = :newValue")
    suspend fun updateBooleanColumnAll(newValue: Boolean)

    @Query("UPDATE wishlist_keys_migrate_two SET id = :quantity WHERE id = :id ")
    fun quantityWishlistr(id: String, quantity: Int)

    @Query("UPDATE wishlist_keys_migrate_two SET isChecked = :isChecked")
    fun checkAll(isChecked: Boolean)

    @Query(
        "INSERT OR REPLACE INTO wishlist_keys_migrate_two (id, " +
                "backdrop_path, " +
                "original_language, " +
                "original_title, " +
                "overview, " +
                "popularity, " +
                "poster_path, " +
                "release_date, " +
                "isChecked) values (:id, :backdropPath, :originalLanguage,:originalTitle, :overview, :popularity, :posterPath, :releaseDate, :isChecked)"
    )
    fun addWishList(
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

    @Query("DELETE FROM wishlist_keys_migrate_two WHERE id = :id")
    fun deleteProduct(id: String)

    @Query("DELETE FROM wishlist_keys_migrate_two")
    fun deleteAllWishlist()

    @Delete
    suspend fun deleteAllCheckedProduct(cartEntity: List<WishlistKey>)

    @Query("SELECT * FROM wishlist_keys_migrate_two")
    fun getWishlistList(): LiveData<List<WishlistKey>>

    @Query("SELECT * FROM wishlist_keys_migrate_two WHERE id = :id")
    fun getWishlistById(id: String): Flow<List<WishlistKey>>
}