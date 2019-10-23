package com.example.foregroundservice

import android.os.Bundle
import android.support.wearable.activity.WearableActivity

class MainActivity : WearableActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Notification(this, applicationContext, "Notification", "Main Activity").show()
    }
}