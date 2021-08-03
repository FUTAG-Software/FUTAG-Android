package com.futag.futag.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.futag.futag.model.anasayfa.AnaSayfaModel
import com.futag.futag.model.blog.BlogModel
import com.futag.futag.model.etkinlik.EtkinliklerModel
import com.futag.futag.model.reklam.ReklamlarModel
import com.futag.futag.service.AnaSayfaAPIService
import com.futag.futag.service.BlogAPIService
import com.futag.futag.service.EtkinlikAPIService
import com.futag.futag.service.ReklamAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class AkisViewModel: ViewModel() {

    private val serviceBlog = BlogAPIService()
    private val serviceEtkinlik = EtkinlikAPIService()
    private val serviceAnaSayfa = AnaSayfaAPIService()
    private val serviceReklam = ReklamAPIService()
    private val compositeDisposable = CompositeDisposable()

    val blogVerileri = MutableLiveData<BlogModel>()
    val blogError = MutableLiveData<Boolean>()
    val blogYukleniyor = MutableLiveData<Boolean>()

    val etkinlikVerileri = MutableLiveData<EtkinliklerModel>()
    val etkinlikError = MutableLiveData<Boolean>()
    val etkinlikYukleniyor = MutableLiveData<Boolean>()

    val anaSayfaVerileri = MutableLiveData<AnaSayfaModel>()
    val anaSayfaError = MutableLiveData<Boolean>()
    val anaSayfaYukleniyor = MutableLiveData<Boolean>()

    val anaSayfaReklamVerileri = MutableLiveData<ReklamlarModel>()
    val anaSayfaReklamError = MutableLiveData<Boolean>()
    val anaSayfaReklamYukleniyor = MutableLiveData<Boolean>()

    fun blogVerileriniAl(){
        blogVerileriniGetir()
    }

    fun anaSayfaVerileriniAl(){
        anaSayfaVerileriniGetir()
    }

    fun etkinlikVerileriniAl(){
        etkinlikVerileriniGetir()
    }

    fun reklamVerileriniAl(){
        reklamVerileriniGetir()
    }

    private fun blogVerileriniGetir(){
        blogYukleniyor.value = true
        compositeDisposable.add(serviceBlog.blogVerileriniGetir()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<BlogModel>(){
                override fun onSuccess(t: BlogModel) {
                    blogVerileri.value = t
                    blogYukleniyor.value = false
                    blogError.value = false
                }
                override fun onError(e: Throwable) {
                    blogYukleniyor.value = false
                    blogError.value = true
                }
            }))
    }

    private fun etkinlikVerileriniGetir(){
        etkinlikYukleniyor.value = true
        compositeDisposable.add(serviceEtkinlik.etkinlikVerileriniGetir()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<EtkinliklerModel>(){
                override fun onSuccess(t: EtkinliklerModel) {
                    etkinlikVerileri.value = t
                    etkinlikYukleniyor.value = false
                    etkinlikError.value = false
                }
                override fun onError(e: Throwable) {
                    etkinlikYukleniyor.value = false
                    etkinlikError.value = true
                }
            }))
    }

    private fun anaSayfaVerileriniGetir(){
        anaSayfaYukleniyor.value = true
        compositeDisposable.add(serviceAnaSayfa.anaSayfaVerileriniGetir()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<AnaSayfaModel>(){
                override fun onSuccess(t: AnaSayfaModel) {
                    anaSayfaVerileri.value = t
                    anaSayfaYukleniyor.value = false
                    anaSayfaError.value = false
                }
                override fun onError(e: Throwable) {
                    anaSayfaYukleniyor.value = false
                    anaSayfaError.value = true
                }
            })
        )
    }

    private fun reklamVerileriniGetir(){
        anaSayfaReklamYukleniyor.value = true
        compositeDisposable.add(serviceReklam.reklamlariGetir()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<ReklamlarModel>(){
                override fun onSuccess(t: ReklamlarModel) {
                    anaSayfaReklamVerileri.value = t
                    anaSayfaReklamYukleniyor.value = false
                    anaSayfaReklamError.value = false
                }
                override fun onError(e: Throwable) {
                    anaSayfaReklamYukleniyor.value = false
                    anaSayfaReklamError.value = true
                }
            })
        )
    }

}