package com.futag.futag.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfilViewModel: ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore

    val animasyon = MutableLiveData<Boolean>()
    val veriOnayi = MutableLiveData<Boolean>()
    var girisVarMi = MutableLiveData<Boolean>()

    fun cikisYap(){
        kullaniciCikisKontrol()
    }

    fun profilBilgileriniGetir(){
    }

    private fun kullaniciCikisKontrol(){
        animasyon.value = true
        val aktifKullanici = auth.currentUser
        if(aktifKullanici != null){
            girisVarMi.value = true
            auth.signOut()
        } else {
            girisVarMi.value = false
            animasyon.value = false
        }
    }

}