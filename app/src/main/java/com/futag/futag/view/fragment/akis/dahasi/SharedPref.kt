package com.futag.futag.view.fragment.akis.dahasi

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {

    internal var mySharedPref: SharedPreferences = context.getSharedPreferences("darkmode",Context.MODE_PRIVATE)

    fun setNightModeState(state: Boolean?){
        val editor = mySharedPref.edit()
        editor.putBoolean("koyutema", state!!)
        editor.apply()
    }

    fun loadNightModeState(): Boolean{
        return mySharedPref.getBoolean("koyutema",false)
    }

    fun setNotificationModeState(state: Boolean?){
        val editor = mySharedPref.edit()
        editor.putBoolean("bildirim",state!!)
        editor.apply()
    }

    fun loadNotificationState(): Boolean{
        return mySharedPref.getBoolean("bildirim",true)
    }

}