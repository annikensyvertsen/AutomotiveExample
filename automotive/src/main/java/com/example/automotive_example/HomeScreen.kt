package com.fremtind.bom_ferge

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.*

class HomeScreen(carContext: CarContext) : Screen(carContext) {
    override fun onGetTemplate(): Template {
        val row = Row.Builder().setTitle("Hello World!").build()
        val pane = Pane.Builder().addRow(row).build()
        return PaneTemplate.Builder(pane)
            .setHeaderAction(Action.APP_ICON)
            .build()
    }
}