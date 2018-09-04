package com.jonasvieira.relogiomundial

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BroadcastApplication(val activity: ClockActivity): BroadcastReceiver() {

    companion object {
        const val FILTER = "ba_filter"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        activity.fireSpinnerItemSelected()
    }
}