package id.rifqipadisiliwangi.core.data.local.database.wishlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "wishlist_keys_migrate_two")
data class WishlistKey (
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
    val popularity: Double,
    @field:ColumnInfo("poster_path")
    val posterPath: String,
    @field:ColumnInfo("release_date")
    val releaseDate: String,
    @field:ColumnInfo("isChecked")
    var isChecked: Boolean = false,
)