package id.rifqipadisiliwangi.movieapp.presentation.common.adapter.transaction

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.rifqipadisiliwangi.core.data.local.database.transaction.TransactionKey
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.ItemTransactionBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.cart.CartViewModel
import id.rifqipadisiliwangi.movieapp.utils.Constant

class TransactionAdapter(
    private var itemDelete : (TransactionKey)-> Unit,
    private var itemList: List<TransactionKey>,
    private val viewModel : CartViewModel,
    private val onClickListenr : (TransactionKey) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setlistWishlist(listCart: List<TransactionKey>){
        this.itemList = listCart
        notifyDataSetChanged()
    }


    inner class ItemViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bind(item: TransactionKey) {
            with(binding) {

                tvDelete.text = itemView.resources.getString(R.string.string_delete)
                tvTitleOverview.text = itemView.resources.getString(R.string.string_synopsis)
                ivDetailMovie.load(Constant.TMDb_BACKDROP_PATH + item.backdropPath)
                tvOriginalTitle.text = item.originalTitle
                tvPopularity.text = item.popularity.toString()
                tvReleaseDate.text = item.releaseDate
                tvDescriptionOverview.text = item.overview
                tvDelete.setOnClickListener {
                    itemDelete(item)
                }
                itemView.setOnClickListener {
                    onClickListenr.invoke(item)
                }
            }
        }
    }
}