package id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rifqipadisiliwangi.core.data.local.database.notification.NotificationKey
import id.rifqipadisiliwangi.core.domain.usecase.AppRoomInteractor
import kotlinx.coroutines.launch

class NotificationViewModel(private val appRoomUseCase: AppRoomInteractor) : ViewModel() {

    fun createNotification(
        notifId: Int,
        notifType: String,
        notifTitle: String,
        notifBody: String,
        notifDate: String,
        notifTime: String,
        notifImage: String,
        isChecked: Boolean
    ) {
        viewModelScope.launch {
            appRoomUseCase.createNotification(
                notifId,
                notifType,
                notifTitle,
                notifBody,
                notifDate,
                notifTime,
                notifImage,
                isChecked
            )
        }
    }

    fun getNotification(): LiveData<List<NotificationKey>?> {
        return appRoomUseCase.getNotifications()
    }

    fun notifIsChecked(id: Int, isChecked: Boolean) {
        return appRoomUseCase.notifIsChecked(id, isChecked)
    }
}