package id.rifqipadisiliwangi.movieapp.presentation.common.adapter.nowplaying

import android.view.View
import coil.load
import id.rifqipadisiliwangi.core.base.BasePagingAdapter
import id.rifqipadisiliwangi.core.domain.model.nowplaying.NowPlayingResult
import id.rifqipadisiliwangi.movieapp.databinding.ItemNowPlayingBinding
import id.rifqipadisiliwangi.movieapp.utils.Constant

class AdapterNowPlaying(private val action : (NowPlayingResult) ->Unit):
BasePagingAdapter<NowPlayingResult, ItemNowPlayingBinding>(ItemNowPlayingBinding::inflate) {
    override fun onItemBind(): (NowPlayingResult, ItemNowPlayingBinding, View, Int) -> Unit =
        { data, binding, itemView, _ ->
            binding.run {
                ivNowPlaying.load(Constant.TMDb_BACKDROP_PATH + data.backdropPath)
            }
            itemView.setOnClickListener {
                action.invoke(data)
            }
        }
}