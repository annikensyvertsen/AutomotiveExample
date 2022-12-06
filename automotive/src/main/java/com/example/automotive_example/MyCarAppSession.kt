package com.fremtind.bom_ferge

import android.content.Intent
import androidx.car.app.Screen
import androidx.car.app.Session

class MyCarAppSession : Session() {

    override fun onCreateScreen(intent: Intent): Screen {
        return HomeScreen(carContext)
    }
}