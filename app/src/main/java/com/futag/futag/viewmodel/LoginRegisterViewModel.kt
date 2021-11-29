package com.futag.futag.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.futag.futag.model.UserModel
import com.futag.futag.util.Constants.FOOD_NOTIFICATION
import com.futag.futag.util.Constants.IMAGES
import com.futag.futag.util.Constants.USERS
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.ktx.storage
import java.util.*

class LoginRegisterViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage

    val animation = MutableLiveData<Boolean>()
    val dataConfirmation = MutableLiveData<Boolean>()
    val isThereEntry = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    // Kaydin gerceklesme durumu, public
    fun registrationConfirmationStatus(
        email: String, password: String,
        name: String, surname: String,
        birthday: String, selectedImage: Uri?
    ) {
        firebaseRegistrationConfirmation(email, password, name, surname, birthday, selectedImage)
    }

    // Eski girisin kontrol durumu, public
    fun autoLoginStatus() {
        firebaseAutoLogin()
    }

    private fun firebaseRegistrationConfirmation(
        email: String, password: String,
        name: String, surname: String,
        birthday: String, selectedImage: Uri?
    ) {
        animation.value = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { firebaseMailRegistration ->
                if (firebaseMailRegistration.isSuccessful) {
                    val registrationTime = Timestamp.now()
                    val activeUserUid = auth.currentUser?.uid
                    if (selectedImage != null) {
                        var imageReferenceLink: String?
                        val profileImageName: String?
                        val reference = storage.reference
                        val uuid = UUID.randomUUID()
                        profileImageName = "${uuid}.jpeg"
                        val imageReference = reference.child(IMAGES).child(profileImageName)
                        imageReference.putFile(selectedImage).addOnSuccessListener {
                            val uploadedImageReference =
                                reference.child(IMAGES).child(profileImageName)
                            uploadedImageReference.downloadUrl.addOnSuccessListener { uri ->
                                imageReferenceLink = uri.toString()
                                if (imageReferenceLink != null) {
                                    val user = UserModel(
                                        name,
                                        surname,
                                        email,
                                        activeUserUid!!,
                                        birthday,
                                        imageReferenceLink,
                                        profileImageName,
                                        registrationTime
                                    )
                                    db.collection(USERS)
                                        .document(activeUserUid).set(user)
                                        .addOnCompleteListener { success ->
                                            if (success.isSuccessful) {
                                                dataConfirmation.value = true
                                                animation.value = false
                                                FirebaseMessaging.getInstance()
                                                    .subscribeToTopic(FOOD_NOTIFICATION)
                                            }
                                        }.addOnFailureListener { exception ->
                                            animation.value = false
                                            errorMessage.value = exception.localizedMessage
                                        }
                                }
                            }
                        }.addOnFailureListener { exception ->
                            animation.value = false
                            errorMessage.value = exception.localizedMessage
                        }
                    } else {
                        val user = UserModel(
                            name,
                            surname,
                            email,
                            activeUserUid!!,
                            birthday,
                            null,
                            null,
                            registrationTime
                        )
                        db.collection(USERS)
                            .document(activeUserUid).set(user).addOnCompleteListener { success ->
                                if (success.isSuccessful) {
                                    dataConfirmation.value = true
                                    animation.value = false
                                    FirebaseMessaging.getInstance().subscribeToTopic(
                                        FOOD_NOTIFICATION
                                    )
                                }
                            }.addOnFailureListener { exception ->
                                animation.value = false
                                errorMessage.value = exception.localizedMessage
                            }
                    }
                }
            }.addOnFailureListener { authError ->
                animation.value = false
                errorMessage.value = authError.localizedMessage
            }
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