package com.example.foregroundservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.support.wearable.activity.WearableActivity
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()
        // Create an explicit intent for an Activity in your app
        val intent = Intent(this, this::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        var builder = createNotificationChannel(this, "testId")!!
            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
            .setContentTitle("My notification")
            .setContentText("LINE1: multi line sample\nLINE2: 3 lines max\nLINE3: tap me")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("LINE01: multi\nLINE02: line\nLINE03: sample\nLINE04: \nLINE05: tap\nLINE06: open\nLINE07: to\nLINE08: open\nLINE09: the\nLINE10: application"
                ))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            // setAutoCancel(), if true, will automatically remove the notification when the user taps it.
            .setAutoCancel(false)

        // notificationId is a unique int for each notification that you must define
        NotificationManagerCompat.from(this)
            .notify(1, builder.build())
    }
}

/**
 * Create the NotificationChannel, but only on API 26+ because
 * the NotificationChannel class is new and not in the support library
 */
private fun createNotificationChannel(activity: MainActivity, channelId: String) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    val name = "test notification"
    val descriptionText = "test description"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel(channelId, name, importance).apply {
        description = descriptionText
    }
    // Register the channel with the system
    val notificationManager: NotificationManager =
        activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
    Log.e("NotificationCreator","created notification channel")
    NotificationCompat.Builder(activity, channelId)
} else {
    Log.e("NotificationCreator", "could not create notification channel")
    null
}