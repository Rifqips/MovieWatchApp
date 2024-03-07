package id.rifqipadisiliwangi.core.data.local.database.transaction

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface TransactionDao {

    @Query("UPDATE transaction_keys_migrate_two SET isChecked = :isChecked WHERE id = :id ")
    fun isChecked(id: String, isChecked: Boolean)

    @Query("UPDATE transaction_keys_migrate_two SET isChecked = :newValue")
    suspend fun updateBooleanColumnAll(newValue: Boolean)

    @Query("UPDATE transaction_keys_migrate_two SET id = :quantity WHERE id = :id ")
    fun quantityTransactionr(id: String, quantity: Int)

    @Query("UPDATE transaction_keys_migrate_two SET isChecked = :isChecked")
    fun checkAll(isChecked: Boolean)

    @Query(
        "INSERT OR REPLACE INTO transaction_keys_migrate_two (id, " +
                "backdrop_path, " +
                "original_language, " +
                "original_title, " +
                "overview, " +
                "popularity, " +
                "poster_path, " +
                "release_date, " +
                "isChecked) values (:id, :backdropPath, :originalLanguage,:originalTitle, :overview, :popularity, :posterPath, :releaseDate, :isChecked)"
    )
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

    @Insert
    fun insertTransaction(transaction: List<TransactionKey>)

    @Query("DELETE FROM transaction_keys_migrate_two WHERE id = :id")
    fun deleteTransactionById(id: String)

    @Query("DELETE FROM transaction_keys_migrate_two")
    fun deleteAllTransaction()

    @Query("SELECT * FROM transaction_keys_migrate_two")
    fun getTransactionList(): LiveData<List<TransactionKey>>

    @Query("SELECT * FROM transaction_keys_migrate_two WHERE id = :id")
    fun getTransactionById(id: String): Flow<List<TransactionKey>>

    @Delete
    suspend fun deleteAllCheckedProduct(cartEntity: List<TransactionKey>)

}