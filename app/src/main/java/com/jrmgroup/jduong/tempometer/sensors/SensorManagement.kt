package com.jrmgroup.jduong.tempometer.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class SensorManagement(mContext : Context) : SensorEventListener {
    private  var mSensorManager: SensorManager

    private var mTempValue : Float = 0F
    private var mHumidValue : Float = 0F
    private var mPressureValue : Float = 0F

    private var mTempSensorAvailable : Boolean = false
    private var mHumidSensorAvailable : Boolean = false
    private var mPressureSensorAvailable : Boolean = false

    private lateinit var mTempSensor : Sensor
    private lateinit var mHumidSensor : Sensor
    private lateinit var mPressureSensor : Sensor


    init {
        mSensorManager = mContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
/*
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) !=null){
            mTempSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
            mTempSensorAvailable = true
            mSensorManager.registerListener(this,mTempSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)!=null){
            mHumidSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
            mHumidSensorAvailable = true
            mSensorManager.registerListener(this,mHumidSensor,SensorManager.SENSOR_DELAY_NORMAL)
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)!=null){
            mPressureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
            mPressureSensorAvailable = true
            mSensorManager.registerListener(this,mPressureSensor,SensorManager.SENSOR_DELAY_NORMAL)
        }
*/
    }

    fun registerListener(){
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) !=null){
            mTempSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
            mTempSensorAvailable = true
            mSensorManager.registerListener(this,mTempSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)!=null){
            mHumidSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
            mHumidSensorAvailable = true
            mSensorManager.registerListener(this,mHumidSensor,SensorManager.SENSOR_DELAY_NORMAL)
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)!=null){
            mPressureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
            mPressureSensorAvailable = true
            mSensorManager.registerListener(this,mPressureSensor,SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun unregisterListener(){
        mSensorManager.unregisterListener(this)
    }

    fun relativeAmbienttempValue(): Float{
        return mTempValue
    }

    fun relativeHumidValue(): Float{
        return mHumidValue
    }

    fun ambientAirPressure(): Float{
        return mPressureValue
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {

        if (p0?.sensor?.type == Sensor.TYPE_AMBIENT_TEMPERATURE){
            val ambTemp = p0.values[0]
            mTempValue = ambTemp
        }

        if (p0?.sensor?.type == Sensor.TYPE_RELATIVE_HUMIDITY){
            val humidValue = p0.values[0]
            mHumidValue = humidValue
        }

        if (p0?.sensor?.type == Sensor.TYPE_PRESSURE){
            val pressure = p0.values[0]
            mPressureValue = pressure
        }

    }
}