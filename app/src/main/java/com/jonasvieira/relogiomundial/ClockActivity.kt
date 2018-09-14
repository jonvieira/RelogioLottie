package com.jonasvieira.relogiomundial

import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import com.jonasvieira.relogiomundial.domain.LottieContainer
import kotlinx.android.synthetic.main.activity_clock.*
import java.util.*

class ClockActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var countriesGmt: Array<String>
    lateinit var broadcast: BroadcastApplication
    lateinit var lottieContainer: LottieContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        initBroadcastReceiver()

        countriesGmt = resources.getStringArray(R.array.countries_gmt)
        sp_countries.onItemSelectedListener = this
        lottieContainer = LottieContainer(this, lav_sun_moon)
    }

    private fun initBroadcastReceiver() {
        broadcast = BroadcastApplication(this)
        val filter = IntentFilter(BroadcastApplication.FILTER)

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcast, filter)
    }

    override fun onDestroy() {
        super.onDestroy()

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcast)
    }

    override fun onItemSelected(adapter: AdapterView<*>?, itemView: View?, position: Int, id: Long) {
        AsyncTrueTime(this)
                .execute(countriesGmt[position])
    }

    override fun onNothingSelected(adapter: AdapterView<*>?) {}

    fun infoDateShow(status: Boolean) {
        runOnUiThread {
            ll_info_date.visibility =
                    if (status)
                        View.VISIBLE
                    else
                        View.INVISIBLE
        }
    }

    fun updateClock(trueTime: Calendar) {
        val hour = trueTime.get(Calendar.HOUR_OF_DAY)
        val minute = trueTime.get(Calendar.MINUTE)

        civ_clock.animateToTime(hour, minute)
        tv_clock.text = String.format("%02d:%02d", hour, minute)
        lottieContainer.updateByHour(hour)
    }

    fun fireSpinnerItemSelected() {
        sp_countries.onItemSelectedListener.onItemSelected(null, null, sp_countries.selectedItemPosition, 0)
    }

    fun setViewsColorByTime(isMorning: Boolean) {
        if (isMorning) {
            sp_countries.setPopupBackgroundResource(R.color.colorSunSky)
        } else {
            sp_countries.setPopupBackgroundResource(R.color.colorMoonSky)
        }
    }
}
