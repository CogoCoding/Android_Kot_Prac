package com.example.kot6.kot8

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class myFirebaseMessagingService:FirebaseMessagingService() {
    override fun onNewToken(tk: String) {
        super.onNewToken(tk)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

    }
}