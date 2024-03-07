package id.rifqipadisiliwangi.core.domain.model.cart

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class KeyCart(
    val id: Int,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: String,
    val posterPath: String,
    val releaseDate: String,
    var isChecked: Boolean = false,
) : Parcelable
