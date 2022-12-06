package com.example.automotive_example

import android.content.Intent
import androidx.car.app.Screen
import androidx.car.app.Session
import com.example.automotive_example.HomeScreen

class MyCarAppSession : Session() {

    override fun onCreateScreen(intent: Intent): Screen {
        return HomeScreen(carContext)
    }
}