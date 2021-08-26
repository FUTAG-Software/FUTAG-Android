package com.futag.futag.viewmodel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.futag.futag.R
import com.futag.futag.model.UserModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

class ProfileViewModel: ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage

    val animation = MutableLiveData<Boolean>()
    val dataConfirmation = MutableLiveData<Boolean>()
    var isThereEntry = MutableLiveData<Boolean>()
    val deleteAccountAnimation = MutableLiveData<Boolean>()
    lateinit var userInfo: UserModel
    val userUid = auth.currentUser?.uid

    fun signOut(){
        userSignOutControl()
    }

    fun getProfileInfo(context: Context){
        pullUserInfo(context)
    }

    fun updateProfile(context: Context, userInfo: UserModel, name: String,
                       surname: String, birthday: String, selectedImage: Uri?){
        updateProfileFirebase(context, userInfo, name, surname, birthday, selectedImage)
    }

    fun deleteAccount(context: Context){
        deleteEverything(context)
    }

    private fun updateProfileFirebase(context: Context, userInfo: UserModel,
                                       newName: String, newSurname: String,
                                       newBirthday: String, newSelectedImage: Uri?){
        animation.value = true
        val reference = storage.reference
        val documentId = userUid
        if(newSelectedImage != null){
            var imageReferenceLink: String?
            if (userInfo.profileImageName == null){
                val uuid = UUID.randomUUID()
                val profileImageName = "${uuid}.jpeg"
                userInfo.profileImageName = profileImageName
            }
            val imageReference = reference.child("Images").child(userInfo.profileImageName!!)
            imageReference.putFile(newSelectedImage).addOnSuccessListener {
                val uploadedImageReference = reference.child("Images").child(userInfo.profileImageName!!)
                uploadedImageReference.downloadUrl.addOnSuccessListener { uri ->
                    imageReferenceLink = uri.toString()
                    if (imageReferenceLink != null){
                        val newUser = UserModel(
                            newName,newSurname,userInfo.email,
                            userUid!!,newBirthday,imageReferenceLink,
                            userInfo.profileImageName,userInfo.registrationTime
                        )
                        db.collection("Users")
                            .document(documentId!!).set(newUser).addOnCompleteListener { registration ->
                                if (registration.isSuccessful){
                                    dataConfirmation.value = true
                                    animation.value = false
                                    Toast.makeText(context,R.string.changes_saved,Toast.LENGTH_LONG).show()
                                }
                            }.addOnFailureListener { veritabaniHatasi ->
                                animation.value = false
                                Toast.makeText(context,veritabaniHatasi.localizedMessage,Toast.LENGTH_LONG).show()
                            }
                    }
                }
            }.addOnFailureListener { exception ->
                animation.value = false
                Toast.makeText(context,exception.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        } else {
            val newUser = UserModel(
                newName,newSurname,userInfo.email,
                userUid!!,newBirthday,userInfo.profileImage,
                userInfo.profileImageName,userInfo.registrationTime
            )
            db.collection("Users")
                .document(documentId!!).set(newUser).addOnCompleteListener { registration ->
                    if (registration.isSuccessful){
                        dataConfirmation.value = true
                        animation.value = false
                        Toast.makeText(context,R.string.changes_saved,Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener { veritabaniHatasi ->
                    animation.value = false
                    Toast.makeText(context,veritabaniHatasi.localizedMessage,Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun pullUserInfo(context: Context){
        animation.value = true
        val documentReference = db.collection("Users").document(userUid!!)
        documentReference.get()
            .addOnSuccessListener { data ->
                if (data != null){
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
                Toast.makeText(context,error.localizedMessage,Toast.LENGTH_SHORT).show()
            }
    }

    private fun userSignOutControl(){
        animation.value = true
        val activeUser = auth.currentUser
        if(activeUser != null){
            isThereEntry.value = true
            auth.signOut()
        } else {
            isThereEntry.value = false
            animation.value = false
        }
    }

    private fun deleteEverything(context: Context){
        deleteAccountAnimation.value = true
        val storageRef = storage.reference
        if (userUid != null) {
            val documentReference = db.collection("Users").document(userUid)
            documentReference.get().addOnSuccessListener { data ->
                    if (data != null){
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
                        if(user.profileImageName != null){
                            storageRef.child("Images").child(user.profileImageName!!)
                                .delete().addOnSuccessListener {
                                    db.collection("Users").document(userUid).delete().addOnSuccessListener {
                                        val currentUser = Firebase.auth.currentUser!!
                                        Firebase.auth.signOut()
                                        currentUser.delete().addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                deleteAccountAnimation.value = false
                                                Toast.makeText(context,R.string.deletion_success,Toast.LENGTH_LONG).show()
                                            }
                                        }.addOnFailureListener {
                                            Toast.makeText(context,it.localizedMessage,Toast.LENGTH_LONG).show()
                                        }
                                    }.addOnFailureListener {
                                        Toast.makeText(context,it.localizedMessage,Toast.LENGTH_LONG).show()
                                    }
                                }.addOnFailureListener {
                                    Toast.makeText(context,it.localizedMessage,Toast.LENGTH_LONG).show()
                                }
                        } else {
                            db.collection("Users").document(userUid).delete().addOnSuccessListener {
                                auth.currentUser!!.delete().addOnSuccessListener {
                                    deleteAccountAnimation.value = false
                                    Toast.makeText(context,R.string.deletion_success,Toast.LENGTH_LONG).show()
                                }.addOnFailureListener {
                                    Toast.makeText(context,it.localizedMessage,Toast.LENGTH_LONG).show()
                                }
                            }.addOnFailureListener {
                                Toast.makeText(context,it.localizedMessage,Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }.addOnFailureListener { error ->
                Toast.makeText(context,error.localizedMessage,Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context,R.string.try_again_later,Toast.LENGTH_LONG).show()
        }
    }

}