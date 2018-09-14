package com.jonasvieira.relogiomundial

import android.app.Application
import android.content.Intent
import android.os.SystemClock
import android.support.v4.content.LocalBroadcastManager
import com.instacart.library.truetime.TrueTimeRx
import io.reactivex.schedulers.Schedulers

class TrueTimeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Thread {
            kotlin.run {
                SystemClock.sleep(5000)
                TrueTimeRx
                        .build()
                        .withSharedPreferences(this)
                        .initializeRx("time.apple.com")
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                {
                                    val intent = Intent(BroadcastApplication.FILTER)

                                    LocalBroadcastManager
                                            .getInstance(this@TrueTimeApplication)
                                            .sendBroadcast(intent)
                                },
                                {
                                }
                        )
            }
        }.start()
    }
}