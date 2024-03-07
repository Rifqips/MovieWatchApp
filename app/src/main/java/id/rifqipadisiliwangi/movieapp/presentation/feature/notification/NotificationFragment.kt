package id.rifqipadisiliwangi.movieapp.presentation.feature.notification

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import id.rifqipadisiliwangi.core.base.BaseFragment
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.FragmentNotificationBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.adapter.notification.NotificationAdapterItem
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.notification.NotificationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class NotificationFragment : BaseFragment<FragmentNotificationBinding, NotificationViewModel>(FragmentNotificationBinding::inflate) {

    override val viewModel: NotificationViewModel by viewModel()

    override fun initView() {
        setUpListNotification()
        with(binding){
            ivBack.load(R.drawable.ic_back)
            tvNotification.text = resources.getString(R.string.string_notification)
        }
    }

    override fun initListener() {
        with(binding) {
            ivBack.setOnClickListener { findNavController().navigateUp() }
        }
    }

    override fun observeData() {}

    private fun setUpListNotification(){
        with(binding){
            rvNotification.layoutManager = LinearLayoutManager(requireContext())
            val adapter = NotificationAdapterItem(
                onNotificationsClick = { notifEntity ->
                    if (!notifEntity.isChecked) {
                        viewModel.notifIsChecked(notifEntity.notifId, true)
                    } else {
                        // do nothing
                    }
                }
            )
            rvNotification.adapter = adapter
            rvNotification.itemAnimator?.changeDuration = 0
            lifecycleScope.launch {
                while (true) {
                    if (isActive) {
                        viewModel.getNotification().observe(viewLifecycleOwner) {
                            if (it?.isNotEmpty() == true) {
                                adapter.submitList(it.reversed())
                            }
                        }
                    }
                    if (!isActive) {
                        break
                    }
                    delay(1000)
                }
            }
        }
    }
}