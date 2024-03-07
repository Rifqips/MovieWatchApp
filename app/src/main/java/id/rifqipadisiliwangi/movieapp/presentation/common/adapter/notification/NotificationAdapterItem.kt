package id.rifqipadisiliwangi.movieapp.presentation.common.adapter.notification

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.color.MaterialColors
import id.rifqipadisiliwangi.core.data.local.database.notification.NotificationKey
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.ItemNotificationBinding


class NotificationAdapterItem(
    private val onNotificationsClick: (NotificationKey) -> Unit
) : ListAdapter<NotificationKey, NotificationAdapterItem.ListViewHolder>(
    NotificationsEntityDiffCallback()
) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val notifEntity = getItem(position)
        holder.bind(notifEntity)

        holder.itemView.setOnClickListener {
            onNotificationsClick(notifEntity)
        }
    }

    class ListViewHolder(var binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(data: NotificationKey) {
            binding.apply {
                ivNotification.load(data.notifImage)
                tvInformation.text = data.notifType
                tvTitleResultNotificaiton.text = data.notifTitle
                tvDescriptionNotification.text = data.notifBody
                tvDateNotification.text = "${data.notifDate}, ${data.notifTime}"

                if (!data.isChecked) {
                    cvNotif.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorGray
                        )
                    )
                } else {
                    cvNotif.setCardBackgroundColor(
                        MaterialColors.getColor(
                            itemView,
                            android.R.attr.windowBackground
                        )
                    )
                }
            }
        }
    }

    private class NotificationsEntityDiffCallback : DiffUtil.ItemCallback<NotificationKey>() {
        override fun areItemsTheSame(
            oldItem: NotificationKey,
            newItem: NotificationKey
        ): Boolean {
            return oldItem.notifId == newItem.notifId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: NotificationKey,
            newItem: NotificationKey
        ): Boolean {
            return oldItem == newItem
        }
    }
}