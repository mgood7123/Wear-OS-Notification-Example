package com.example.foregroundservice

import android.app.Service
import android.content.Intent
import android.os.IBinder

class Service: Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // we need to aquire a Context here, how can we do this?
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}