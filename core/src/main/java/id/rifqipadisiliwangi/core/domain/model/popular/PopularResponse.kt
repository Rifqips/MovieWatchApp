package id.rifqipadisiliwangi.core.domain.model.popular

import android.os.Parcelable
import androidx.annotation.Keep
import id.rifqipadisiliwangi.core.data.remote.data.popular.ResultPopular
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class PopularResponse(
    val page: Int,
    val results: List<ResultPopular>,
    val totalPages: Int,
    val totalResults: Int
) : Parcelable


@Keep
@Parcelize
data class PopularResult(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
) : Parcelable

fun ResultPopular.toResultPopular() = PopularResult(
    id = this.id, adult = this.adult, backdropPath = this.backdropPath, originalLanguage = this.originalLanguage, originalTitle = this.originalTitle, overview = this.overview, popularity = this.popularity, posterPath = this.posterPath, releaseDate = this.releaseDate, title = this.title, video = this.video, voteAverage = this.voteAverage, genreIds = listOf(), voteCount = this.voteCount
)
