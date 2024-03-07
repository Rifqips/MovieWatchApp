package id.rifqipadisiliwangi.movieapp.presentation.common.adapter.wishlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.rifqipadisiliwangi.core.domain.model.wishlist.KeyWishlist
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.ItemWishlistBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.wishlist.WishListViewModel
import id.rifqipadisiliwangi.movieapp.utils.Constant

class WishlistAdapter(
private var itemList: List<KeyWishlist>,
private val viewModel : WishListViewModel,
private val onClickListenr : (KeyWishlist) -> Unit
) : RecyclerView.Adapter<WishlistAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding = ItemWishlistBinding.inflate(
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
    fun setlistWishlist(listCart: List<KeyWishlist>){
        this.itemList = listCart
        notifyDataSetChanged()
    }


    inner class ItemViewHolder(private val binding: ItemWishlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bind(item: KeyWishlist) {
            with(binding) {
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