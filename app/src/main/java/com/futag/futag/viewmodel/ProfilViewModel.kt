package com.futag.futag.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.futag.futag.R
import com.futag.futag.model.KullaniciModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ProfilViewModel: ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage

    val animasyon = MutableLiveData<Boolean>()
    val veriOnayi = MutableLiveData<Boolean>()
    var girisVarMi = MutableLiveData<Boolean>()
    lateinit var kullaniciBilgileri: KullaniciModel

    fun cikisYap(){
        kullaniciCikisKontrol()
    }

    fun profilBilgileriniGetir(context: Context){
        kullaniciBilgileriniCek(context)
    }

    private fun kullaniciBilgileriniCek(context: Context){
        animasyon.value = true
        val kullaniciUid = auth.currentUser?.uid
        val documentReferansi = db.collection("Users").document(kullaniciUid!!)
        documentReferansi.get()
            .addOnSuccessListener { veri ->
                if (veri != null){
                    val kullanici =
                        KullaniciModel(veri["isim"] as String,
                            veri["soyisim"] as String, veri["email"] as String,
                            kullaniciUid, veri["dogumGunu"] as String,
                            veri["profilResmi"] as String, veri["kayitTarihi"] as Timestamp)
                    kullaniciBilgileri = kullanici
                    animasyon.value = false
                    veriOnayi.value = true
                }
            }.addOnFailureListener { hata ->
                Toast.makeText(context,hata.localizedMessage,Toast.LENGTH_SHORT).show()
            }
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