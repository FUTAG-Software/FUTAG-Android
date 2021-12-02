package com.futag.futag.presentation.ui.fragment.login.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.futag.futag.util.Constants.FOOD_NOTIFICATION
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class LoginViewModel : ViewModel() {

    private val auth = Firebase.auth

    val animation = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val success = MutableLiveData<Boolean>()

    fun loginToApp(email: String, password: String) {
        loginUsingFirebase(email, password)
    }

    private fun loginUsingFirebase(email: String, password: String) {
        animation.value = true
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { process ->
            if (process.isSuccessful) {
                animation.value = false
                success.value = true
                FirebaseMessaging.getInstance().subscribeToTopic(FOOD_NOTIFICATION)
            }
        }.addOnFailureListener { error ->
            animation.value = false
            errorMessage.value = error.localizedMessage
        }
    }

}