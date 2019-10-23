package com.example.foregroundservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class MyBootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.e("NotificationCreator", "ON BOOT RECIEVED")
        Notification(MainActivity(), context, "Notification", "On Boot").show()
    }
}