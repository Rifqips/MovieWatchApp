package id.rifqipadisiliwangi.movieapp.presentation.common.adapter.cart

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.rifqipadisiliwangi.core.domain.model.cart.KeyCart
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.ItemCartBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.cart.CartViewModel
import id.rifqipadisiliwangi.movieapp.utils.Constant

class CartAdapter(
    private val itemDelete : (KeyCart) -> Unit,
    private val itemChecked : (KeyCart, Boolean) -> Unit,
    private var itemList: List<KeyCart>,
) : RecyclerView.Adapter<CartAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding = ItemCartBinding.inflate(
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
    fun setlistCart(listCart: List<KeyCart>){
        this.itemList = listCart
        notifyDataSetChanged()
    }


    inner class ItemViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bind(item: KeyCart) {
            with(binding) {
                tvDelete.text = itemView.resources.getString(R.string.string_delete)
                tvTitleOverview.text = itemView.resources.getString(R.string.string_synopsis)
                ivDetailMovie.load(Constant.TMDb_BACKDROP_PATH + item.backdropPath)
                tvOriginalTitle.text = item.originalTitle
                tvPopularity.text = item.popularity
                tvReleaseDate.text = item.releaseDate
                tvDescriptionOverview.text = item.overview
                cbItem.isChecked = item.isChecked
                cbItem.setOnCheckedChangeListener { _, isChecked ->
                    itemChecked(item, isChecked)
                }
                ivStar.load(itemView.context.getDrawable(R.drawable.ic_launcher_foreground))
                tvDelete.setOnClickListener { itemDelete(item) }
            }
        }
    }
}