package id.rifqipadisiliwangi.core.data.local.database.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_keys_migrate_two")
data class CartKey(
    @field:ColumnInfo("id")
    @field:PrimaryKey
    val id: Int,
    @field:ColumnInfo("backdrop_path")
    val backdropPath: String,
    @field:ColumnInfo("original_language")
    val originalLanguage: String,
    @field:ColumnInfo("original_title")
    val originalTitle: String,
    @field:ColumnInfo("overview")
    val overview: String,
    @field:ColumnInfo("popularity")
    val popularity: String,
    @field:ColumnInfo("poster_path")
    val posterPath: String,
    @field:ColumnInfo("release_date")
    val releaseDate: String,
    @field:ColumnInfo("isChecked")
    var isChecked: Boolean = false,
)
