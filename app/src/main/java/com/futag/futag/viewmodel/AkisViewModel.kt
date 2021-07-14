package com.futag.futag.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.futag.futag.model.blog.BlogModel
import com.futag.futag.model.etkinlik.EtkinliklerModel
import com.futag.futag.service.BlogAPIService
import com.futag.futag.service.EtkinlikAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class AkisViewModel: ViewModel() {

    private val serviceBlog = BlogAPIService()
    private val serviceEtkinlik = EtkinlikAPIService()
    private val compositeDisposable = CompositeDisposable()

    val blogVerileri = MutableLiveData<BlogModel>()
    val blogError = MutableLiveData<Boolean>()
    val blogYukleniyor = MutableLiveData<Boolean>()

    val etkinlikVerileri = MutableLiveData<EtkinliklerModel>()
    val etkinlikError = MutableLiveData<Boolean>()
    val etkinlikYukleniyor = MutableLiveData<Boolean>()

    fun blogVerileriniAl(){
        blogVerileriniGetir()
    }

    fun etkinlikVerileriniAl(){
        etkinlikVerileriniGetir()
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

}