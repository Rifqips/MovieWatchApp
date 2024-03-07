package id.rifqipadisiliwangi.movieapp.presentation.common.adapter.load

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.rifqipadisiliwangi.movieapp.databinding.LoadStateFooterBinding


class LoadStateAdapterNowPlaying(
    private val retry: () -> Unit
) :
    LoadStateAdapter<LoadStateAdapterNowPlaying.LoadStateViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder {
        val binding = LoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadStateViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class LoadStateViewHolder(
        private val binding: LoadStateFooterBinding,
        retry: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            when(loadState){
                is LoadState.Error -> {
                    binding.retryButton.isVisible
                    binding.errorMsg.isVisible
                }
                is LoadState.Loading ->
                    binding.progressBar.isVisible = true
                is LoadState.NotLoading ->
                    binding.progressBar.isVisible = false
            }
        }
    }
}