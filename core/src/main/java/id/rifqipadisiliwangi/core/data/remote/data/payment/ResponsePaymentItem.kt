package id.rifqipadisiliwangi.core.data.remote.data.payment


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

class ResponsePaymentItem : ArrayList<ResponsePaymentItem.ResponsePaymentItemItem>(){
    @Keep
    @Parcelize
    data class ResponsePaymentItemItem(
        @field:SerializedName("price")
        val price: Int,
        @field:SerializedName("token")
        val token: Int
    ) : Parcelable

    fun ResponsePaymentItemItem.toPaymentMapper() = ResponsePaymentItemItem(
        price = this.price, token = this.token
    )

    fun Collection<ResponsePaymentItemItem>.toPaymentList() = this.map { it.toPaymentMapper() }
}