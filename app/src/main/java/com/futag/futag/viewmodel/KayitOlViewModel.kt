package com.futag.futag.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class KayitOlViewModel: ViewModel() {

    private val auth = Firebase.auth

    val animasyon = MutableLiveData<Boolean>()
    val veriOnayi = MutableLiveData<Boolean>()

    fun kayitOnayDurumu(email: String, sifre: String, context: Context){
        firebaseKayitOnayi(email, sifre, context)
    }

    private fun firebaseKayitOnayi(email: String, sifre: String, context: Context){
        animasyon.value = true
        auth.createUserWithEmailAndPassword(email, sifre).addOnCompleteListener { task ->
            if (task.isSuccessful){
                veriOnayi.value = true
                animasyon.value = false
                println("firebase kayit tamamlandi")
            }
        }.addOnFailureListener { exception ->
            animasyon.value = false
            Toast.makeText(context,exception.localizedMessage,Toast.LENGTH_LONG).show()
            println("firebase kayit hata")
        }
    }

}