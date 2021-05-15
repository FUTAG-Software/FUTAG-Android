package com.futag.futag.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class KayitOlGirisYapViewModel: ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore

    val animasyon = MutableLiveData<Boolean>()
    val veriOnayi = MutableLiveData<Boolean>()
    var girisVarMi = MutableLiveData<Boolean>()

    // Kaydin gerceklesme durumu, public
    fun kayitOnayDurumu(email: String, sifre: String,
                        isim: String, soyisim: String,
                        dogumGunu: String, context: Context){
        firebaseKayitOnayi(email, sifre, isim, soyisim, dogumGunu ,context)
    }

    // Girisin gerceklesme durumu, public
    fun girisOnayDurumu(email: String, sifre: String, context: Context){
        firebaseGiris(email, sifre, context)
    }

    // Eski girisin kontrol durumu, public
    fun otomatikGirisDurumu(){
        firebaseOtomatikGiris()
    }

    // Sifre yenileme maili, public
    fun sifremiUnuttumDurumu(mail: String, context: Context){
        firebaseSifreUnutma(mail, context)
    }

    private fun firebaseKayitOnayi(email: String, sifre: String,
                                   isim: String, soyisim: String,
                                   dogumGunu: String, context: Context){
        animasyon.value = true
        auth.createUserWithEmailAndPassword(email, sifre).addOnCompleteListener { islem ->
            if (islem.isSuccessful){
                println("firebase mail kaydi tamamlandi")
                val kayitTarihi = Timestamp.now()
                val aktifKullaniciUid = auth.currentUser?.uid
                val kullanici = hashMapOf(
                    "uid" to aktifKullaniciUid,
                    "isim" to isim,
                    "soyisim" to soyisim,
                    "dogumGunu" to dogumGunu,
                    "kayitTarihi" to kayitTarihi
                )
                // Kullanici bilgilerinin firestore kaydi
                db.collection("Users").add(kullanici).addOnCompleteListener { kayit ->
                    if (kayit.isSuccessful){
                        veriOnayi.value = true
                        animasyon.value = false
                    }
                }.addOnFailureListener { veritabaniHatasi ->
                    animasyon.value = false
                    Toast.makeText(context,veritabaniHatasi.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }
        }.addOnFailureListener { dogrulamaHatasi->
            animasyon.value = false
            Toast.makeText(context,dogrulamaHatasi.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }

    private fun firebaseGiris(email: String, sifre: String, context: Context){
        animasyon.value = true
        auth.signInWithEmailAndPassword(email,sifre).addOnCompleteListener { islem ->
            if (islem.isSuccessful){
                veriOnayi.value = true
                animasyon.value = false
            }
        }.addOnFailureListener { hata ->
            animasyon.value = false
            Toast.makeText(context,hata.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }

    private fun firebaseOtomatikGiris(){
        animasyon.value = true
        val aktifKullanici = auth.currentUser
        if(aktifKullanici != null){
            girisVarMi.value = true
        } else {
            girisVarMi.value = false
            animasyon.value = false
        }
    }

    private fun firebaseSifreUnutma(mail: String, context: Context){
        animasyon.value = true
        auth.sendPasswordResetEmail(mail).addOnCompleteListener { islem ->
            if (islem.isSuccessful){
                veriOnayi.value = true
                animasyon.value = false
            }
        }.addOnFailureListener { hata ->
            animasyon.value = false
            Toast.makeText(context,hata.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }

}