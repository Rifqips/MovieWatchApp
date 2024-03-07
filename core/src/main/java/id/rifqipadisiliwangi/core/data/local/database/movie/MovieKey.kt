package id.rifqipadisiliwangi.core.data.local.database.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie_keys_migrate_two")
data class MovieKey(
    @field:ColumnInfo("id")
    @field:PrimaryKey
    val id: Int,
    @field:ColumnInfo("adult")
    val adult: Boolean,
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
    @field:ColumnInfo("title")
    val title: String,
    @field:ColumnInfo("video")
    val video: Boolean,
    @field:ColumnInfo("vote_average")
    val voteAverage: Double,
    @field:ColumnInfo("vote_count")
    val voteCount: Int,
    @field:ColumnInfo("isChecked")
    var isChecked: Boolean = false,
)
