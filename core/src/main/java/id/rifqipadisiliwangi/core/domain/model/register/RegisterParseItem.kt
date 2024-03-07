package id.rifqipadisiliwangi.core.domain.model.register

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterParseItem(
    var address : String? = "",
    var email : String? = "",
    var password : String? ="",
    var username : String? =""
) : Parcelable
