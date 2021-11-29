package com.futag.futag.view.fragment.login.login_register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginRegisterViewModel : ViewModel() {

    private val auth = Firebase.auth

    val animation = MutableLiveData<Boolean>()
    val isThereEntry = MutableLiveData<Boolean>()

    fun autoLoginStatus() {
        firebaseAutoLogin()
    }

    private fun firebaseAutoLogin() {
        animation.value = true
        val currentUser = auth.currentUser
        if (currentUser != null) {
            isThereEntry.value = true
        } else {
            isThereEntry.value = false
            animation.value = false
        }
    }

}