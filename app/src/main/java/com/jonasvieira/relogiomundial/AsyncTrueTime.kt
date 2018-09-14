package com.jonasvieira.relogiomundial

import android.os.AsyncTask
import com.instacart.library.truetime.TrueTimeRx
import java.lang.ref.WeakReference
import java.util.*

class AsyncTrueTime() : AsyncTask<String, Unit, Calendar>() {
    lateinit var weakActivity: WeakReference<ClockActivity>

    constructor(activity: ClockActivity) : this() {
        weakActivity = WeakReference(activity)
    }

    override fun doInBackground(vararg args: String?): Calendar {

        lateinit var date: Date

        try {
            date = TrueTimeRx.now()
            weakActivity.get()?.infoDateShow(false)
        } catch (e: Exception) {
            date = Date()
            weakActivity.get()?.infoDateShow(true)
        }

        val calendar = Calendar.getInstance(
                TimeZone.getTimeZone(args[0])
        )
        calendar.timeInMillis = date.time

        return calendar
    }

    override fun onPostExecute(calendar: Calendar) {
        super.onPostExecute(calendar)
        weakActivity.get()?.updateClock(calendar)
    }
}