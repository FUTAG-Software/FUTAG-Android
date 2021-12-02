package com.futag.futag.presentation.ui.fragment.flow.profile.update_password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UpdatePasswordViewModel : ViewModel() {

    private val auth = Firebase.auth

    val animation = MutableLiveData<Boolean>()
    var isThereEntry = MutableLiveData<Boolean>()
    val updatePasswordData = MutableLiveData<Boolean>()
    val updatePasswordError = MutableLiveData<String>()

    fun updatePassword(newPassword: String) {
        updatePasswordFirebase(newPassword)
    }

    fun signOut() {
        userSignOutControl()
    }

    private fun updatePasswordFirebase(newPassword: String) {
        animation.value = true
        val currentUser = auth.currentUser
        currentUser!!.updatePassword(newPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                animation.value = false
                updatePasswordData.value = true
            }
        }.addOnFailureListener { error ->
            animation.value = false
            updatePasswordError.value = error.localizedMessage
        }
    }

    private fun userSignOutControl() {
        animation.value = true
        val activeUser = auth.currentUser
        if (activeUser != null) {
            isThereEntry.value = true
            auth.signOut()
        } else {
            isThereEntry.value = false
            animation.value = false
        }
    }

}