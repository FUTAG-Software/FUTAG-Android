package com.futag.futag.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.futag.futag.model.blog.BlogModel
import com.futag.futag.service.BlogAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class AkisViewModel: ViewModel() {

    private val service = BlogAPIService()
    private val compositeDisposable = CompositeDisposable()

    val blogVerileri = MutableLiveData<BlogModel>()
    val blogError = MutableLiveData<Boolean>()
    val blogYukleniyor = MutableLiveData<Boolean>()

    fun blogVerileriniAl(){
        blogVerileriniGetir()
    }

    private fun blogVerileriniGetir(){
        blogYukleniyor.value = true
        compositeDisposable.add(service.blogVerileriniGetir()
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

}