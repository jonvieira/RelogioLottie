package com.jonasvieira.relogiomundial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        profile.speed = 1.8f
        profile.useHardwareAcceleration(true)
        profile.enableMergePathsForKitKatAndAbove(true)

        val handler = Handler()
        handler.postDelayed({
            profile.cancelAnimation()
            mostrarMainActivity()
        }, 4000)
    }

    private fun mostrarMainActivity() {
        startActivity(Intent(this, ClockActivity::class.java))
        finish()
    }
}
