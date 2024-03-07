package id.rifqipadisiliwangi.core.domain.model.payment


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

class PaymentResponseItem : ArrayList<PaymentResponseItem.PaymentResponseItemItem>(){
    @Keep
    @Parcelize
    data class PaymentResponseItemItem(
        val price: Int,
        val token: Int
    ) : Parcelable
}