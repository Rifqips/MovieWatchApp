package id.rifqipadisiliwangi.movieapp.presentation.common.adapter.search

import android.view.View
import coil.load
import id.rifqipadisiliwangi.core.base.BasePagingAdapter
import id.rifqipadisiliwangi.core.domain.model.search.SearchItemResult
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.ItemSearchBinding
import id.rifqipadisiliwangi.movieapp.utils.Constant

class AdapterSearchItem(private val action : (SearchItemResult) ->Unit):
    BasePagingAdapter<SearchItemResult, ItemSearchBinding>(ItemSearchBinding::inflate) {
    override fun onItemBind(): (SearchItemResult, ItemSearchBinding, View, Int) -> Unit =
        { data, binding, itemView, _ ->
            binding.run {
                tvTitleOverview.text = itemView.resources.getString(R.string.string_synopsis)
                ivDetailMovie.load(Constant.TMDb_BACKDROP_PATH + data.backdropPath)
                tvOriginalTitle.text = data.originalTitle
                tvPopularity.text = data.popularity.toString()
                tvReleaseDate.text = data.releaseDate
                tvDescriptionOverview.text = data.overview
            }
            itemView.setOnClickListener {
                action.invoke(data)
            }
        }
}