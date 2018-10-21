package promobusque.ramon.promobusqueapp.firebasepack

import android.app.Notification.Builder
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build.VERSION
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import promobusque.ramon.promobusqueapp.R
import promobusque.ramon.promobusqueapp.db.DatabasePromo
import kotlin.jvm.internal.Intrinsics


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(s: String?) {
        Log.e("NEW_TOKEN", s)
        FirebaseMessaging.getInstance().subscribeToTopic("promobusque")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.e("JSON_OBJECT", JSONObject(remoteMessage!!.data).toString())
        val NOTIFICATION_CHANNEL_ID = "promobusque"
        val pattern = longArrayOf(0, 1000, 500, 1000)
        if (podeMostrarNotificacao()) {
            var mNotificationManager: NotificationManager? = getSystemService("notification") as NotificationManager?
            if (mNotificationManager != null) {
                if (VERSION.SDK_INT >= 26) {
                    val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "Your Notifications", 4)
                    notificationChannel.description = ""
                    notificationChannel.enableLights(true)
                    notificationChannel.lightColor = -16711936
                    notificationChannel.vibrationPattern = pattern
                    notificationChannel.enableVibration(true)
                    mNotificationManager.createNotificationChannel(notificationChannel)
                }

                if (VERSION.SDK_INT >= 26) {
                    mNotificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID).canBypassDnd()
                }

                val notificationBuilder = Builder(this, NOTIFICATION_CHANNEL_ID)
                val contentTitle = notificationBuilder.setAutoCancel(true)
                    .setColor(ContextCompat.getColor(this, R.color.colorAccent))
                    .setContentTitle(getString(R.string.app_name))
                val notification = remoteMessage.notification
                if (notification == null) {
                    Intrinsics.throwNpe()
                }
                Intrinsics.checkExpressionValueIsNotNull(notification!!, "remoteMessage.notification!!")
                contentTitle.setContentText(notification.body).setDefaults(-1).setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_notifications_green_24dp).setAutoCancel(true)
                mNotificationManager.notify(1000, notificationBuilder.build())
                return
            }
        }
    }

    fun podeMostrarNotificacao(): Boolean {
        val database = DatabasePromo().getInstance(this)
        return database?.configuracaoPromobusqueDao()?.findById(1)!!.RecebeNotificacao;
    }
}