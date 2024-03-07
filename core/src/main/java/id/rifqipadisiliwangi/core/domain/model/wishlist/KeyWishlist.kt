package id.rifqipadisiliwangi.core.domain.model.wishlist

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class KeyWishlist(
    val id: Int,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    var isChecked: Boolean = false
) : Parcelable
