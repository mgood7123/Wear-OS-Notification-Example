package com.example.foregroundservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class MyBootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.e("NotificationCreator", "ON BOOT RECIEVED")
        Notification(context, "Notification", "On Boot: Time since boot").show(0)
    }
}