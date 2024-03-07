package id.rifqipadisiliwangi.core.domain.model.checkout

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CheckoutDataClass(
    val id: Int,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: String,
    val posterPath: String,
    val releaseDate: String,
    var price: String,
) : Parcelable

@Keep
@Parcelize
data class ListCheckout(
    val listCheckout: List<CheckoutDataClass>
) : Parcelable
