package id.rifqipadisiliwangi.movieapp.presentation.common.adapter.topup

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.rifqipadisiliwangi.core.data.remote.data.payment.ResponsePaymentItem
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.ItemTopUpBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.topup.TopUpViewModel
import id.rifqipadisiliwangi.movieapp.utils.Constant.toCurrencyFormat

class TopUpAdapter(
    val viewModel : TopUpViewModel,
    val itemToken : (String) -> Unit
) :
    RecyclerView.Adapter<TopUpAdapter.ListViewHolder>() {

    private var selectedPosition = 0
    private var lastSelectedPosition = 0

    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<ResponsePaymentItem.ResponsePaymentItemItem>() {
            override fun areItemsTheSame(oldItem: ResponsePaymentItem.ResponsePaymentItemItem, newItem: ResponsePaymentItem.ResponsePaymentItemItem): Boolean {
                return oldItem.token == newItem.token
            }

            override fun areContentsTheSame(oldItem: ResponsePaymentItem.ResponsePaymentItemItem, newItem: ResponsePaymentItem.ResponsePaymentItemItem): Boolean {
                return oldItem == newItem
            }
        }
    )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemTopUpBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = holder.bindingAdapterPosition
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
        updateItemBackground(holder, position)
    }

    private fun updateItemBackground(holder: ListViewHolder, position: Int) {
        if (selectedPosition == position) {
            holder.binding.llTopUp.setBackgroundResource(R.drawable.bg_gradient_balance)
            holder.binding.tvPrice.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    android.R.color.white
                )
            )
            itemToken(holder.binding.tvResultToken.text.toString())
        } else {
            holder.binding.llTopUp.setBackgroundResource(R.drawable.custom_rounded_top_up)
            holder.binding.tvPrice.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorBlueGray
                )
            )
        }
        viewModel.stateTopUpMethod.observeForever { state->
            if (state){
                if (selectedPosition == position) {
                    holder.binding.llTopUp.setBackgroundResource(R.drawable.bg_gradient_balance)
                    holder.binding.tvPrice.setTextColor(
                        ContextCompat.getColor(
                            holder.itemView.context,
                            android.R.color.white
                        )
                    )
                    itemToken(holder.binding.tvResultToken.text.toString())
                } else {
                    holder.binding.llTopUp.setBackgroundResource(R.drawable.custom_rounded_top_up)
                    holder.binding.tvPrice.setTextColor(
                        ContextCompat.getColor(
                            holder.itemView.context,
                            R.color.colorBlueGray
                        )
                    )
                }
            } else {
                viewModel.updateStateMethod(false)
                holder.binding.llTopUp.setBackgroundResource(R.drawable.custom_rounded_top_up)
                holder.binding.tvPrice.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.colorBlueGray
                    )
                )
                itemToken("0")

            }
        }
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    fun setData(data: List<ResponsePaymentItem.ResponsePaymentItemItem>) {
        dataDiffer.submitList(data)
    }

    class ListViewHolder(
        val binding: ItemTopUpBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bind(data: ResponsePaymentItem.ResponsePaymentItemItem?) {
            data?.let { validData ->
                with(binding){
                    tvPrice.text = validData.price.toCurrencyFormat()
                    tvResultToken.text = validData.token.toString()
//                    when(validData.token){
//                        200 -> ivMoney.load(R.drawable.topup_one)
//                        500 -> ivMoney.load(R.drawable.topup_two)
//                        700 -> ivMoney.load(R.drawable.topup_three)
//                        1000 -> ivMoney.load(R.drawable.topup_four)
//                        1200 -> ivMoney.load(R.drawable.topup_five)
//                        1500 -> ivMoney.load(R.drawable.topup_six)
//                        else -> {}
//                    }
                }
            }
        }
    }
}