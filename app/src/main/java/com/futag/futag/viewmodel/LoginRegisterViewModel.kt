package com.futag.futag.viewmodel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.futag.futag.model.UserModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.ktx.storage
import java.util.*

class LoginRegisterViewModel: ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage

    val animation = MutableLiveData<Boolean>()
    val dataConfirmation = MutableLiveData<Boolean>()
    var isThereEntry = MutableLiveData<Boolean>()

    // Kaydin gerceklesme durumu, public
    fun registrationConfirmationStatus(email: String, password: String,
                        name: String, surname: String,
                        birthday: String, selectedImage: Uri?, context: Context){
        firebaseRegistrationConfirmation(email, password, name, surname, birthday, selectedImage, context)
    }

    // Girisin gerceklesme durumu, public
    fun loginConfirmationStatus(email: String, password: String, context: Context){
        firebaseLogin(email, password, context)
    }

    // Eski girisin kontrol durumu, public
    fun autoLoginStatus(){
        firebaseAutoLogin()
    }

    // Sifre yenileme maili, public
    fun forgotPasswordStatus(mail: String, context: Context){
        firebaseForgotPassword(mail, context)
    }

    private fun firebaseRegistrationConfirmation(email: String, password: String,
                                   name: String, surname: String,
                                   birthday: String, selectedImage: Uri?, context: Context){
        animation.value = true
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { firebaseMailRegistration ->
            if (firebaseMailRegistration.isSuccessful){
                val registrationTime = Timestamp.now()
                val activeUserUid = auth.currentUser?.uid
                if(selectedImage != null){
                    var imageReferenceLink: String?
                    val profileImageName: String?
                    val reference = storage.reference
                    val uuid = UUID.randomUUID()
                    profileImageName = "${uuid}.jpeg"
                    val imageReference = reference.child("Images").child(profileImageName)
                    imageReference.putFile(selectedImage).addOnSuccessListener {
                        val uploadedImageReference = reference.child("Images").child(profileImageName)
                        uploadedImageReference.downloadUrl.addOnSuccessListener { uri ->
                            imageReferenceLink = uri.toString()
                            if (imageReferenceLink != null){
                                val user = UserModel(name,surname,email,
                                    activeUserUid!!,birthday,imageReferenceLink,profileImageName,registrationTime)
                                db.collection("Users")
                                    .document(activeUserUid).set(user).addOnCompleteListener { success ->
                                        if (success.isSuccessful){
                                            dataConfirmation.value = true
                                            animation.value = false
                                            FirebaseMessaging.getInstance().subscribeToTopic("notification")
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
                    val user = UserModel(name,surname,email
                        ,activeUserUid!!,birthday,null,null,registrationTime)
                    db.collection("Users")
                        .document(activeUserUid).set(user).addOnCompleteListener { success ->
                            if (success.isSuccessful){
                                dataConfirmation.value = true
                                animation.value = false
                                FirebaseMessaging.getInstance().subscribeToTopic("notification")
                            }
                        }.addOnFailureListener { veritabaniHatasi ->
                            animation.value = false
                            Toast.makeText(context,veritabaniHatasi.localizedMessage,Toast.LENGTH_LONG).show()
                        }
                }
            }
        }.addOnFailureListener { dogrulamaHatasi->
            animation.value = false
            Toast.makeText(context,dogrulamaHatasi.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }

    private fun firebaseLogin(email: String, password: String, context: Context){
        animation.value = true
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { process ->
            if (process.isSuccessful){
                dataConfirmation.value = true
                animation.value = false
                FirebaseMessaging.getInstance().subscribeToTopic("notification")
            }
        }.addOnFailureListener { hata ->
            animation.value = false
            Toast.makeText(context,hata.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }

    private fun firebaseAutoLogin(){
        animation.value = true
        val currentUser = auth.currentUser
        if(currentUser != null){
            isThereEntry.value = true
        } else {
            isThereEntry.value = false
            animation.value = false
        }
    }

    private fun firebaseForgotPassword(mail: String, context: Context){
        animation.value = true
        auth.sendPasswordResetEmail(mail).addOnCompleteListener { process ->
            if (process.isSuccessful){
                dataConfirmation.value = true
                animation.value = false
            }
        }.addOnFailureListener { hata ->
            animation.value = false
            Toast.makeText(context,hata.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }

}