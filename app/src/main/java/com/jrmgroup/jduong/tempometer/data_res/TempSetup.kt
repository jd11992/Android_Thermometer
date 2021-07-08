package com.jrmgroup.jduong.tempometer.data_res

import android.content.Context
import android.preference.PreferenceManager
import com.jrmgroup.jduong.tempometer.R


class TempSetup(val context : Context) {

    fun getSpeedLabel(): String { //Get speed measurement type
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val tempValue: String = context.resources.getString(R.string.temp_selection)
        val getValue = sharedPreference.getString("temp_value", tempValue)
        return getValue!!
    }

    fun setSpeedLabel(tempSelection: String) { //Set speed measurement type
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreference.edit().putString("temp_value", tempSelection).apply()

    }
}