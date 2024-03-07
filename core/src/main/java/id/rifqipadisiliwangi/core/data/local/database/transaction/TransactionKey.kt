package id.rifqipadisiliwangi.core.data.local.database.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_keys_migrate_two")
data class TransactionKey(
    @field:ColumnInfo("id")
    @field:PrimaryKey
    val id: Int? = null,
    @field:ColumnInfo("backdrop_path")
    val backdropPath: String?= null,
    @field:ColumnInfo("original_language")
    val originalLanguage: String?= null,
    @field:ColumnInfo("original_title")
    val originalTitle: String?= null,
    @field:ColumnInfo("overview")
    val overview: String?= null,
    @field:ColumnInfo("popularity")
    val popularity: String?= null,
    @field:ColumnInfo("poster_path")
    val posterPath: String?= null,
    @field:ColumnInfo("release_date")
    val releaseDate: String?= null,
    @field:ColumnInfo("isChecked")
    var isChecked: Boolean = false,
)