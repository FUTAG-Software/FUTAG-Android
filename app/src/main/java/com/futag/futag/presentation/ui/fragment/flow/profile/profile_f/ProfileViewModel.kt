package com.futag.futag.presentation.ui.fragment.flow.profile.profile_f

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.futag.futag.model.UserModel
import com.futag.futag.util.Constants.USERS
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore

    val animation = MutableLiveData<Boolean>()
    val dataConfirmation = MutableLiveData<Boolean>()
    var isThereEntry = MutableLiveData<Boolean>()
    lateinit var userInfo: UserModel
    val userUid = auth.currentUser?.uid
    val updatePasswordData = MutableLiveData<Boolean>()
    val updatePasswordError = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    val changesSaved = MutableLiveData<Boolean>()

    fun signOut() {
        userSignOutControl()
    }

    fun getProfileInfo() {
        pullUserInfo()
    }

    private fun pullUserInfo() {
        animation.value = true
        val documentReference = db.collection(USERS).document(userUid!!)
        documentReference.get()
            .addOnSuccessListener { data ->
                if (data != null) {
                    val user = UserModel(
                        data["name"] as String,
                        data["surname"] as String,
                        data["email"] as String,
                        data["userUid"] as String,
                        data["birthday"] as String,
                        data["profileImage"] as String?,
                        data["profileImageName"] as String?,
                        data["registrationTime"] as Timestamp
                    )
                    userInfo = user
                    animation.value = false
                    dataConfirmation.value = true
                }
            }.addOnFailureListener { error ->
                animation.value = false
                errorMessage.value = error.localizedMessage
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