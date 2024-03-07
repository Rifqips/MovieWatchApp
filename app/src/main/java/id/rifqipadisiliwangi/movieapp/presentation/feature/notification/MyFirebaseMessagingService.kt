package id.rifqipadisiliwangi.movieapp.presentation.feature.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.notification.NotificationViewModel

class MyFirebaseMessagingService : FirebaseMessagingService() {

    lateinit var model: NotificationViewModel

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        model = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(NotificationViewModel::class.java)
        Log.d(TAG, "From: ${remoteMessage.from}")

        remoteMessage.data.let {
            Log.d(TAG, "Message Notification Body: $it")
        }

        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)

        }
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
        sendNotification(remoteMessage.data)

        Log.d(TAG, remoteMessage.data["body"].toString())
        val notifId = System.currentTimeMillis().toInt()

        model.createNotification(
            notifId,
            remoteMessage.data["type"].toString(),
            remoteMessage.data["title"].toString(),
            remoteMessage.data["body"].toString(),
            remoteMessage.data["date"].toString(),
            remoteMessage.data["time"].toString(),
            remoteMessage.data["image"].toString(),
            false
        )
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    private fun sendNotification(messageBody: MutableMap<String, String>) {
        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.nav_application)
            .setDestination(R.id.notificationFragment)
            .createPendingIntent()

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(messageBody["title"])
            .setContentText(messageBody["body"])
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT,
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationId = System.currentTimeMillis().toInt()
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}
