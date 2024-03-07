package id.rifqipadisiliwangi.movieapp.presentation.common.adapter.popular

import android.view.View
import coil.load
import id.rifqipadisiliwangi.core.base.BasePagingAdapter
import id.rifqipadisiliwangi.core.domain.model.popular.PopularResult
import id.rifqipadisiliwangi.movieapp.databinding.ItemPopularMoviesBinding
import id.rifqipadisiliwangi.movieapp.utils.Constant

class AdapterPagingItem(private val action: (PopularResult)->Unit):
    BasePagingAdapter<PopularResult, ItemPopularMoviesBinding>(ItemPopularMoviesBinding::inflate) {
    override fun onItemBind(): (PopularResult, ItemPopularMoviesBinding, View, Int) -> Unit =
        { data, binding, itemView, _ ->
            binding.run {
                ivNowPlaying.load(Constant.TMDb_BACKDROP_PATH + data.backdropPath)
                tvTitle.text = data.originalTitle
            }
            itemView.setOnClickListener {
                action.invoke(data)
            }
        }
}