package com.example.lostfound.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.lostfound.R
import com.example.lostfound.fragments.FoundFragment
import com.example.lostfound.fragments.LostFragment
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {
    private lateinit var intent : Intent

    override fun onCreate() {
        super.onCreate()
        intent = Intent(this, LostFragment::class.java)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body
        val tag = remoteMessage.notification?.tag
        if(tag == "izgubljeno"){
            intent = Intent(this, LostFragment::class.java)
        }
        else if(tag == "pronadeno"){
            intent = Intent(this, FoundFragment::class.java)
        }

        val openActivity = PendingIntent.getActivity(this,0,intent,  PendingIntent.FLAG_IMMUTABLE)
        val CHANNEL_ID = "NOTIFICATION"
        val channel = NotificationChannel(CHANNEL_ID, "MyNotification", NotificationManager.IMPORTANCE_HIGH)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentIntent(openActivity)
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(this).notify(1, notification)
    }
}