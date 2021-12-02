package com.futag.futag.view.fragment.flow.profile.edit_profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.futag.futag.model.UserModel
import com.futag.futag.util.Constants
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

class EditProfileViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage

    lateinit var userInfo: UserModel
    val userUid = auth.currentUser?.uid
    val animation = MutableLiveData<Boolean>()
    val dataConfirmation = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val changesSaved = MutableLiveData<Boolean>()
    val deleteAccountAnimation = MutableLiveData<Boolean>()
    val deleteAccountError = MutableLiveData<Boolean>()
    val deleteAccountConfirmation = MutableLiveData<Boolean>()

    fun getProfileInfo() {
        pullUserInfo()
    }

    fun updateProfile(
        userInfo: UserModel, name: String, surname: String, birthday: String, selectedImage: Uri?
    ) {
        updateProfileFirebase(userInfo, name, surname, birthday, selectedImage)
    }

    fun deleteAccount() {
        deleteEverything()
    }

    private fun pullUserInfo() {
        animation.value = true
        val documentReference = db.collection(Constants.USERS).document(userUid!!)
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

    private fun updateProfileFirebase(
        userInfo: UserModel,
        newName: String,
        newSurname: String,
        newBirthday: String,
        newSelectedImage: Uri?
    ) {
        animation.value = true
        val reference = storage.reference
        val documentId = userUid
        if (newSelectedImage != null) {
            var imageReferenceLink: String?
            if (userInfo.profileImageName == null) {
                val uuid = UUID.randomUUID()
                val profileImageName = "${uuid}.jpeg"
                userInfo.profileImageName = profileImageName
            }
            val imageReference =
                reference.child(Constants.IMAGES).child(userInfo.profileImageName!!)
            imageReference.putFile(newSelectedImage).addOnSuccessListener {
                val uploadedImageReference =
                    reference.child(Constants.IMAGES).child(userInfo.profileImageName!!)
                uploadedImageReference.downloadUrl.addOnSuccessListener { uri ->
                    imageReferenceLink = uri.toString()
                    if (imageReferenceLink != null) {
                        val newUser = UserModel(
                            newName, newSurname, userInfo.email,
                            userUid!!, newBirthday, imageReferenceLink,
                            userInfo.profileImageName, userInfo.registrationTime
                        )
                        db.collection(Constants.USERS)
                            .document(documentId!!).set(newUser)
                            .addOnCompleteListener { registration ->
                                if (registration.isSuccessful) {
                                    dataConfirmation.value = true
                                    animation.value = false
                                    changesSaved.value = true
                                }
                            }.addOnFailureListener { error ->
                                animation.value = false
                                errorMessage.value = error.localizedMessage
                            }
                    }
                }
            }.addOnFailureListener { exception ->
                animation.value = false
                errorMessage.value = exception.localizedMessage
            }
        } else {
            val newUser = UserModel(
                newName, newSurname, userInfo.email,
                userUid!!, newBirthday, userInfo.profileImage,
                userInfo.profileImageName, userInfo.registrationTime
            )
            db.collection(Constants.USERS)
                .document(documentId!!).set(newUser).addOnCompleteListener { registration ->
                    if (registration.isSuccessful) {
                        dataConfirmation.value = true
                        animation.value = false
                        changesSaved.value = true
                    }
                }.addOnFailureListener { error ->
                    animation.value = false
                    errorMessage.value = error.localizedMessage
                }
        }
    }

    private fun deleteEverything() {
        deleteAccountAnimation.value = true
        val storageRef = storage.reference
        if (userUid != null) {
            val documentReference = db.collection(Constants.USERS).document(userUid)
            documentReference.get().addOnSuccessListener { data ->
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
                    if (user.profileImageName != null) {
                        storageRef.child(Constants.IMAGES).child(user.profileImageName!!)
                            .delete().addOnSuccessListener {
                                db.collection(Constants.USERS).document(userUid).delete()
                                    .addOnSuccessListener {
                                        val currentUser = Firebase.auth.currentUser!!
                                        Firebase.auth.signOut()
                                        currentUser.delete().addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                deleteAccountAnimation.value = false
                                                deleteAccountConfirmation.value = true
                                            }
                                        }.addOnFailureListener {
                                            deleteAccountAnimation.value = false
                                            errorMessage.value = it.localizedMessage
                                        }
                                    }.addOnFailureListener {
                                        deleteAccountAnimation.value = false
                                        errorMessage.value = it.localizedMessage
                                    }
                            }.addOnFailureListener {
                                deleteAccountAnimation.value = false
                                errorMessage.value = it.localizedMessage
                            }
                    } else {
                        db.collection(Constants.USERS).document(userUid).delete().addOnSuccessListener {
                            auth.currentUser!!.delete().addOnSuccessListener {
                                deleteAccountAnimation.value = false
                                deleteAccountConfirmation.value = true
                            }.addOnFailureListener {
                                deleteAccountAnimation.value = false
                                errorMessage.value = it.localizedMessage
                            }
                        }.addOnFailureListener {
                            deleteAccountAnimation.value = false
                            errorMessage.value = it.localizedMessage
                        }
                    }
                }
            }.addOnFailureListener { error ->
                errorMessage.value = error.localizedMessage
            }
        } else {
            deleteAccountAnimation.value = false
            deleteAccountError.value = true
        }
    }

}