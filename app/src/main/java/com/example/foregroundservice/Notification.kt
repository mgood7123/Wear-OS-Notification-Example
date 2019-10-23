package com.example.foregroundservice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat



class Notification {
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManagerCompat
    private lateinit var toast: Toast
    constructor(activityToOpen: MainActivity, context: Context, title: String, description: String): super() {
        build(activityToOpen, context, title, description, null, null, null, true, true, Color.GREEN)
    }
    constructor(context: Context, title: String, description: String): super() {
        build(null, context, title, description, null, null, null, true, true, Color.GREEN)
    }
    constructor(activity: MainActivity, context: Context, title: String, description: String, expandedDescription: String?, smallIcon: Int?, largeIcon: Bitmap?, persistant: Boolean, containsTimeSinceWhen: Boolean, colour: Int?): super() {
        build(activity, context, title, description, expandedDescription, smallIcon, largeIcon, persistant, containsTimeSinceWhen, colour)
    }
    constructor(context: Context, title: String, description: String, expandedDescription: String?, smallIcon: Int?, largeIcon: Bitmap?, persistant: Boolean, containsTimeSinceWhen: Boolean, colour: Int?): super() {
        build(null, context, title, description, expandedDescription, smallIcon, largeIcon, persistant, containsTimeSinceWhen, colour)
    }
    fun build(activity: MainActivity?, context: Context, title: String, description: String, expandedDescription: String?, smallIcon: Int?, largeIcon: Bitmap?, persistant: Boolean, containsTimeSinceWhen: Boolean, colour: Int?) {
        notificationBuilder = createNotificationChannel(context, "testId")!!.apply {
            setSmallIcon(
                if (smallIcon != null) smallIcon
                else R.drawable.common_google_signin_btn_icon_dark_normal
            )
            if (largeIcon != null) setLargeIcon(largeIcon)
            setContentTitle(
                if (title != null) title
                else "Notification Title"
            )
            setContentText(
                if (description != null) description
                else "Notification Description\nTap to view properties"
            )
            if (persistant) {
                setOngoing(true)
                setPriority(NotificationCompat.PRIORITY_MAX)
            }
            if (containsTimeSinceWhen) setUsesChronometer(true)
            if (colour != null) {
                setColorized(true)
                setColor(colour)
            }
            var expandedRequired = false
            if (description != null) expandedRequired = expandedDescription != null
            else if (description == null) expandedRequired = true
            if (activity != null) {
                setContentIntent(
                    PendingIntent.getActivity(activity, 0,
                        Intent(context, activity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }, 0)
                )
                setAutoCancel(true)
            }
            if (activity != null && expandedRequired) setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    if (expandedDescription != null) expandedDescription
                    else "Notification Properties:\n" +
                            "Small icon supplied: ${smallIcon != null}\n" +
                            (
                                if (smallIcon != null) "Small icon value: $smallIcon\n"
                                else ""
                            ) +
                            "Large icon supplied: ${largeIcon != null}\n" +
                            "Title supplied: ${title != null}\n" +
                            "Title: $title\n" +
                            "Description supplied: ${description!= null}\n" +
                            "Description: $description\n" +
                            "Is expandable: true\n" +
                            "Expanded description supplied: ${expandedDescription != null}\n" +
                            "Expanded description: " +
                            (
                                if (expandedDescription != null) expandedDescription
                                else "the text you are currently reading"
                            ) + "\n" +
                            "Can be dismissed: $persistant\n" +
                            "Can be coloured: ${colour != null}\n" +
                            (
                                if (colour != null) "colour (ARGB): $colour\n"
                                else ""
                            ) +
                            "Contains time since when: $containsTimeSinceWhen\n"
                )
            )
        }
        notificationManager = NotificationManagerCompat.from(context)
    }
    // notificationId is a unique int for each notification that you must define
    fun show() {
        notificationManager.notify(1, notificationBuilder.build())
    }
    /**
     * Create the NotificationChannel, but only on API 26+ because
     * the NotificationChannel class is new and not in the support library
     */
    private fun createNotificationChannel(context: Context, channelId: String) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "test notification"
        val descriptionText = "test description"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        NotificationCompat.Builder(context, channelId)
    } else {
        Log.wtf("NotificationCreator", "could not create notification channel")
        null
    }
}