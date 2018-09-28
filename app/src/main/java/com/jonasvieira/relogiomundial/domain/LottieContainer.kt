package com.jonasvieira.relogiomundial.domain

import android.animation.Animator
import android.content.Context
import com.airbnb.lottie.LottieAnimationView
import com.jonasvieira.relogiomundial.ClockActivity
import java.util.*

class LottieContainer(private val context: Context, private val animation: LottieAnimationView) : Animator.AnimatorListener {

    companion object {
        const val SPEED_SUN_TO_MOON = 1.5F
        const val SPEED_MOON_TO_SUN = -1.5F

        const val FRAME_FIRST = 46
        const val FRAME_LAST = 82
    }

    private var hour: Int = 0

    init {
        animation.setMinAndMaxFrame(FRAME_FIRST, FRAME_LAST)
        animation.addAnimatorListener(this)

        startHourAndColor()
    }

    private fun startHourAndColor() {
        val calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR_OF_DAY)

        updateActivityViewsColor()
    }

    private fun updateActivityViewsColor() {
        (context as ClockActivity).setViewsColorByTime(isMorning(hour))
    }

    private fun isMorning(hour: Int): Boolean = hour in 6..17

    private fun canAnimate(hour: Int): Boolean = (isMorning(hour) && animation.frame == FRAME_LAST) || (!isMorning(hour) && animation.frame == FRAME_FIRST)

    fun updateByHour(hour: Int) {

        if (!canAnimate(hour)) {
            return
        }

        var speedValue = SPEED_SUN_TO_MOON

        if (isMorning(hour)) {
            speedValue = SPEED_MOON_TO_SUN
        }

        this.hour = hour
        animation.speed = speedValue
        animation.useHardwareAcceleration(true)
        animation.enableMergePathsForKitKatAndAbove(true)
        animation.playAnimation()

        updateActivityViewsColor()
    }

    override fun onAnimationStart(animator: Animator?) {}

    override fun onAnimationEnd(animator: Animator?) {
        animation.frame =
                if (isMorning(hour))
                    FRAME_FIRST
                else
                    FRAME_LAST
    }

    override fun onAnimationRepeat(animator: Animator?) {}

    override fun onAnimationCancel(animator: Animator?) {}
}