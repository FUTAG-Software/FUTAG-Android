package com.futag.futag.presentation.ui.fragment.login.register

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.futag.futag.model.UserModel
import com.futag.futag.util.Constants.FOOD_NOTIFICATION
import com.futag.futag.util.Constants.FUTAG_NOTIFICATION
import com.futag.futag.util.Constants.IMAGES
import com.futag.futag.util.Constants.USERS
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.ktx.storage
import java.util.*

class RegisterViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage

    val animation = MutableLiveData<Boolean>()
    val dataConfirmation = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String?>()

    fun registerToApp(
        email: String, password: String,
        name: String, surname: String,
        birthday: String, selectedImage: Uri?,
    ) {
        registerToAppUsingFirebase(email, password, name, surname, birthday, selectedImage)
    }

    private fun registerToAppUsingFirebase(
        email: String, password: String,
        name: String, surname: String,
        birthday: String, selectedImage: Uri?,
    ) {
        errorMessage.value = null
        animation.value = true
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authState ->
            if (authState.isSuccessful) {
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
                                            FirebaseMessaging.getInstance()
                                                .subscribeToTopic(FUTAG_NOTIFICATION)
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
                                FirebaseMessaging.getInstance().subscribeToTopic(
                                    FUTAG_NOTIFICATION
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

}