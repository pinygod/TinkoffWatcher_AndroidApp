package com.example.tinkoffwatcher

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.tinkoffwatcher.data.repository.NotificationsRepository
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class NotificationService : FirebaseMessagingService() {

    private val notificationsRepository: NotificationsRepository by inject()

    companion object {
        const val CHANNEL_ID = "FCMChannel"

        fun generateFCMToken() {
            FirebaseMessaging.getInstance().token
        }

        fun deleteFCMToken() {
            FirebaseMessaging.getInstance().deleteToken()
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            notificationsRepository.addFCMToken(token)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if (remoteMessage.data.isNotEmpty()) {
            with(remoteMessage.data) {
                val title = get("Title")
                val message = get("Message")
                showNotification(title, message)
            }
        }

        remoteMessage.notification?.let {
            val title = it.title
            val body = it.body
            showNotification(title, body)
        }
    }

    private fun showNotification(title: String?, body: String?) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "StonksWatcherNotificationChannel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        with(builder) {
            setAutoCancel(true)
            setContentTitle(title)
            setStyle(NotificationCompat.BigTextStyle().bigText(body))
            setSmallIcon(R.drawable.ic_baseline_trending_up_24)
        }
        notificationManager.notify(100, builder.build())
    }
}