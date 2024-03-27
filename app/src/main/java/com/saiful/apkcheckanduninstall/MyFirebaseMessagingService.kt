package com.saiful.apkcheckanduninstall

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FCM Message", "From: ${remoteMessage.from}")

        // Check if message contains a data payload
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("FCM Message", "Message data payload: ${remoteMessage.data}")
        }

        // Check if message contains a notification payload
        remoteMessage.notification?.let {
            Log.d("FCM Message", "Message Notification Body: ${it.body}")
        }
    }
}