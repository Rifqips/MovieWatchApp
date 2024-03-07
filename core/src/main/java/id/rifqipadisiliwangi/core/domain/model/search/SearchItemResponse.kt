package id.rifqipadisiliwangi.core.domain.model.search

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class SearchItemResponse(
        val page: Int,
        val results: List<SearchItemResult>,
        val totalPages: Int,
        val totalResults: Int
): Parcelable

@Keep
@Parcelize
data class SearchItemResult(
        val adult: Boolean,
        val backdropPath: String?,
        val genreIds: List<Int>,
        val id: Int,
        val originalLanguage: String,
        val originalTitle: String,
        val overview: String,
        val popularity: Double,
        val posterPath: String?,
        val releaseDate: String,
        val title: String,
        val video: Boolean,
        val voteAverage: Double,
        val voteCount: Int
) : Parcelable
