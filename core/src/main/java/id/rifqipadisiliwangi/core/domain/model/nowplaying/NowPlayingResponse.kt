package id.rifqipadisiliwangi.core.domain.model.nowplaying

import android.os.Parcelable
import androidx.annotation.Keep
import id.rifqipadisiliwangi.core.data.remote.data.nowplaying.Dates
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class NowPlayingResponse(
    val dates: Dates,
    val page: Int,
    val results: List<NowPlayingResult>,
    val totalPages: Int,
    val totalResults: Int
) : Parcelable


@Keep
@Parcelize
data class NowPlayingResult(
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
