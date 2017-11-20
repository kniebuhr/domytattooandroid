package ink.domytattoo.rest.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ink.domytattoo.R
import ink.domytattoo.activity.FlashworkActivity

/**
 * Created by victorcatao on 20/11/2017.
 */
class MyFirebaseMessagingService: FirebaseMessagingService() {

    companion object {
        const val TAG = "FIREBASEDMT"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        Log.d(TAG, "From: " + remoteMessage!!.from)

        // Check if message contains a data payload.
        if (remoteMessage.getData().isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification.body!!)
            showNotification(remoteMessage.notification.body)
        }

    }


    fun showNotification(message: String?) {
        val intent = Intent(this, FlashworkActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("DoMyTattoo")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }

}