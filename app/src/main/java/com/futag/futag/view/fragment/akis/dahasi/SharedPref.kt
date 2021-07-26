package com.futag.futag.view.fragment.akis.dahasi

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {

    internal var mySharedPref: SharedPreferences = context.getSharedPreferences("sharedPref",Context.MODE_PRIVATE)

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

    fun setOnBoardingState(state: Boolean?){
        val editor = mySharedPref.edit()
        editor.putBoolean("bitis",state!!)
        editor.apply()
    }

    fun loadOnBoardingState(): Boolean{
        return mySharedPref.getBoolean("bitis",false)
    }

    fun setLanguageState(state: Boolean?){
        val editor = mySharedPref.edit()
        editor.putBoolean("dil",state!!)
        editor.apply()
    }

    fun loadOnLanguageState(): Boolean {
        return mySharedPref.getBoolean("dil", false)
    }

}