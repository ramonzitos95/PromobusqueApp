package promobusque.ramon.promobusqueapp.firebasepack

import android.annotation.SuppressLint
import android.app.Notification.Builder
import android.app.NotificationChannel
import android.app.NotificationManager
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.os.Build.VERSION
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import promobusque.ramon.promobusqueapp.R
import kotlin.jvm.internal.Intrinsics
import com.google.firebase.iid.FirebaseInstanceId
import promobusque.ramon.promobusqueapp.modelos.TAG


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(refreshedToken: String?) {
        // Get updated InstanceID token.
        Log.e("NEW_TOKEN", refreshedToken)
        FirebaseMessaging.getInstance().subscribeToTopic("promobusque")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage?.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage?.data?.size!! > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage?.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.

                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                //handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification?.body);
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

    }
}