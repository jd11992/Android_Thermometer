package com.jrmgroup.jduong.tempometer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.ToggleButton
import com.jrmgroup.jduong.tempometer.R
import com.jrmgroup.jduong.tempometer.data_res.TempSetup
import com.jrmgroup.jduong.tempometer.sensors.SensorManagement

class MainActivity : AppCompatActivity() {

    private lateinit var mTempValue : TextView
    private lateinit var mHumidValue : TextView
    private lateinit var mTempSelectLabel : TextView
    private lateinit var mPressureValue : TextView
    private lateinit var toggleButton : ToggleButton
    private lateinit var Sensors : SensorManagement
    private lateinit var TempSetup : TempSetup
    private var sessionInForeground : Boolean = false
    private var mRunnable : Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TempSetup = TempSetup(this)
        Sensors = SensorManagement(this)

        mTempValue = findViewById(R.id.tempValue)
        mTempSelectLabel = findViewById(R.id.tempReader)
        mHumidValue = findViewById(R.id.humidValue)
        mPressureValue = findViewById(R.id.pressValue)

         toggleButton  = findViewById(R.id.tempButton)
        val tempSetup = TempSetup.getSpeedLabel()
        if (tempSetup.contains("fah")){
            toggleButton.isChecked = true
            mTempSelectLabel.text = this.resources.getString(R.string.fal_value)
        }

        toggleButton.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                TempSetup.setSpeedLabel("fah")
                mTempSelectLabel.text = this.resources.getString(R.string.fal_value)
            }else{
                TempSetup.setSpeedLabel("cel")
                mTempSelectLabel.text = this.resources.getString(R.string.cel_value)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        sessionInForeground = true
        Sensors.registerListener()
        updateViews()

    }

    override fun onPause() {
        super.onPause()
        sessionInForeground = false
    }

    override fun onDestroy() {
        super.onDestroy()
        Handler().removeCallbacks(mRunnable)
        Sensors.unregisterListener()
    }

    private fun updateViews(){
       if (mRunnable == null){
           mRunnable = Runnable {

               val tempValueIn = Sensors.relativeAmbienttempValue()
               var Textoutput : String = ""
               if (toggleButton.isChecked && TempSetup.getSpeedLabel().contains("fah")){
                   val fahConversionValue = tempValueIn + 32F
                   Textoutput = String.format("%.1f",fahConversionValue)
                   mTempValue.text = Textoutput
               }else{
                   Textoutput = String.format("%.1f", tempValueIn)
                   mTempValue.text = Textoutput
               }

               val humidValueIn = Sensors.relativeHumidValue()

               Textoutput = String.format("%.1f",humidValueIn)
               mHumidValue.text = Textoutput

               val pressureValueIn = Sensors.ambientAirPressure()
               Textoutput = String.format("%.1f", pressureValueIn)
                mPressureValue.text = Textoutput



               Handler().removeCallbacks(mRunnable)
               Handler().postDelayed(mRunnable, 1500)
           }
           Handler().post(mRunnable)
       }
    }


}
