package com.futag.futag.viewmodel

import android.content.Context
import android.net.Uri
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
import java.util.*

class ProfilViewModel: ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage

    val animasyon = MutableLiveData<Boolean>()
    val veriOnayi = MutableLiveData<Boolean>()
    var girisVarMi = MutableLiveData<Boolean>()
    lateinit var kullaniciBilgileri: KullaniciModel
    val kullaniciUid = auth.currentUser?.uid

    fun cikisYap(){
        kullaniciCikisKontrol()
    }

    fun profilBilgileriniGetir(context: Context){
        kullaniciBilgileriniCek(context)
    }

    fun profilGuncelle(context: Context, kullaniciBilgileri: KullaniciModel, isim: String,
                       soyisim: String, dogumGunu: String, secilenGorsel: Uri?){
        profilGuncelleFirebase(context, kullaniciBilgileri, isim, soyisim, dogumGunu, secilenGorsel)
    }

    fun hesabiSil(context: Context){
        kullaniciyiHerseyiyleSil(context)
    }

    private fun profilGuncelleFirebase(context: Context,kullaniciBilgileri: KullaniciModel,
                                       yeniIsim: String, yeniSoyisim: String,
                                       yeniDogumGunu: String, yeniSecilenGorsel: Uri?){
        animasyon.value = true
        val reference = storage.reference
        val documentId = kullaniciUid
        if(yeniSecilenGorsel != null){
            var gorselReferansLinki: String? = null
            if (kullaniciBilgileri.profilResmiAdi == null){
                val uuid = UUID.randomUUID()
                val profilResmiAdi = "${uuid}.jpeg"
                kullaniciBilgileri.profilResmiAdi = profilResmiAdi
            }
            val gorselReferansi = reference.child("Gorseller").child(kullaniciBilgileri.profilResmiAdi!!)
            gorselReferansi.putFile(yeniSecilenGorsel).addOnSuccessListener {
                val yuklenenGorselReferansi = reference.child("Gorseller").child(kullaniciBilgileri.profilResmiAdi!!)
                yuklenenGorselReferansi.downloadUrl.addOnSuccessListener { uri ->
                    gorselReferansLinki = uri.toString()
                    if (gorselReferansLinki != null){
                        val yeniKullanici = KullaniciModel(
                            yeniIsim,yeniSoyisim,kullaniciBilgileri.email,
                            kullaniciUid!!,yeniDogumGunu,gorselReferansLinki,
                            kullaniciBilgileri.profilResmiAdi,kullaniciBilgileri.kayitTarihi
                        )
                        db.collection("Users")
                            .document(documentId!!).set(yeniKullanici).addOnCompleteListener { kayit ->
                                if (kayit.isSuccessful){
                                    veriOnayi.value = true
                                    animasyon.value = false
                                    Toast.makeText(context,R.string.degisiklikler_kaydedildi,Toast.LENGTH_LONG).show()
                                }
                            }.addOnFailureListener { veritabaniHatasi ->
                                animasyon.value = false
                                Toast.makeText(context,veritabaniHatasi.localizedMessage,Toast.LENGTH_LONG).show()
                            }
                    }
                }
            }.addOnFailureListener { exception ->
                animasyon.value = false
                Toast.makeText(context,exception.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        } else {
            val yeniKullanici = KullaniciModel(
                yeniIsim,yeniSoyisim,kullaniciBilgileri.email,
                kullaniciUid!!,yeniDogumGunu,kullaniciBilgileri.profilResmi,
                kullaniciBilgileri.profilResmiAdi,kullaniciBilgileri.kayitTarihi
            )
            db.collection("Users")
                .document(documentId!!).set(yeniKullanici).addOnCompleteListener { kayit ->
                    if (kayit.isSuccessful){
                        veriOnayi.value = true
                        animasyon.value = false
                        Toast.makeText(context,R.string.degisiklikler_kaydedildi,Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener { veritabaniHatasi ->
                    animasyon.value = false
                    Toast.makeText(context,veritabaniHatasi.localizedMessage,Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun kullaniciBilgileriniCek(context: Context){
        animasyon.value = true
        val documentReferansi = db.collection("Users").document(kullaniciUid!!)
        documentReferansi.get()
            .addOnSuccessListener { veri ->
                if (veri != null){
                    val kullanici = KullaniciModel(
                        veri["isim"] as String,
                        veri["soyisim"] as String,
                        veri["email"] as String,
                        veri["uid"] as String,
                        veri["dogumGunu"] as String,
                        veri["profilResmi"] as String?,
                        veri["profilResmiAdi"] as String?,
                        veri["kayitTarihi"] as Timestamp
                    )
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

    private fun kullaniciyiHerseyiyleSil(context: Context){
        val storageRef = storage.reference
        if (kullaniciUid != null) {
            val documentReferansi = db.collection("Users").document(kullaniciUid)
            documentReferansi.get().addOnSuccessListener { veri ->
                    if (veri != null){
                        val kullanici = KullaniciModel(
                            veri["isim"] as String,
                            veri["soyisim"] as String,
                            veri["email"] as String,
                            veri["uid"] as String,
                            veri["dogumGunu"] as String,
                            veri["profilResmi"] as String?,
                            veri["profilResmiAdi"] as String?,
                            veri["kayitTarihi"] as Timestamp
                        )
                        if(kullanici.profilResmiAdi != null){
                            storageRef.child("Gorseller").child(kullanici.profilResmiAdi!!)
                                .delete().addOnSuccessListener {
                                    db.collection("Users").document(kullaniciUid).delete().addOnSuccessListener {
                                        auth.currentUser!!.delete().addOnSuccessListener {
                                            auth.signOut()
                                            Toast.makeText(context,R.string.hesap_silme_basarili,Toast.LENGTH_LONG).show()
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
                            db.collection("Users").document(kullaniciUid).delete().addOnSuccessListener {
                                auth.currentUser!!.delete().addOnSuccessListener {
                                    auth.signOut()
                                    Toast.makeText(context,R.string.hesap_silme_basarili,Toast.LENGTH_LONG).show()
                                }.addOnFailureListener {
                                    Toast.makeText(context,it.localizedMessage,Toast.LENGTH_LONG).show()
                                }
                            }.addOnFailureListener {
                                Toast.makeText(context,it.localizedMessage,Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }.addOnFailureListener { hata ->
                Toast.makeText(context,hata.localizedMessage,Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context,R.string.daha_sonra_tekrar_deneyiniz,Toast.LENGTH_LONG).show()
        }
    }

}