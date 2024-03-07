package id.rifqipadisiliwangi.movieapp.presentation.common.adapter.corousel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import id.rifqipadisiliwangi.core.domain.model.corousel.CorouselDataItem
import id.rifqipadisiliwangi.movieapp.databinding.ImageContainerCorouselBinding


class CorouselAdapter(
    private val imageList: ArrayList<CorouselDataItem>, private val viewPager2: ViewPager2
) : RecyclerView.Adapter<CorouselAdapter.OnboardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = ImageContainerCorouselBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OnboardingViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        val currentItem = imageList[position]
        holder.bind(currentItem)
        if (position == imageList.size - 1){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ImageContainerCorouselBinding? = ImageContainerCorouselBinding.bind(itemView)

        fun bind(data: CorouselDataItem) {
            binding?.apply {
                onboardingImage.load(data.image)
                tvTitle.text = data.title
                tvDescription.text = data.description
            }
        }
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}