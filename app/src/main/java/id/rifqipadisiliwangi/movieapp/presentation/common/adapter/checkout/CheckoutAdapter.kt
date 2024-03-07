package id.rifqipadisiliwangi.movieapp.presentation.common.adapter.checkout

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.rifqipadisiliwangi.core.domain.model.checkout.CheckoutDataClass
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.ItemCheckoutBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.cart.CartViewModel
import id.rifqipadisiliwangi.movieapp.utils.Constant

class CheckoutAdapter(
    private var itemList: List<CheckoutDataClass>,
    private var itemPrice : (CheckoutDataClass) -> Unit,
    private val viewModel : CartViewModel,
    private val onClickListenr : (CheckoutDataClass) -> Unit
) : RecyclerView.Adapter<CheckoutAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding = ItemCheckoutBinding.inflate(
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
    fun setlistCart(listCart: List<CheckoutDataClass>){
        this.itemList = listCart
        notifyDataSetChanged()
    }


    inner class ItemViewHolder(private val binding: ItemCheckoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bind(item: CheckoutDataClass) {
            with(binding) {
                itemPrice(item)
                tvDelete.text = itemView.resources.getString(R.string.string_delete)
                tvTitleOverview.text = itemView.resources.getString(R.string.string_synopsis)
                ivDetailMovie.load(Constant.TMDb_BACKDROP_PATH + item.backdropPath)
                tvOriginalTitle.text = item.originalTitle
                tvPopularity.text = item.popularity.toString()
                tvReleaseDate.text = item.releaseDate
                tvDescriptionOverview.text = item.overview
                ivStar.load(itemView.context.getDrawable(R.drawable.ic_launcher_foreground))
                tvDelete.setOnClickListener { viewModel.deleteById(item.id.toString())}
                itemView.setOnClickListener {
                    onClickListenr.invoke(item)
                }
            }
        }
    }
}