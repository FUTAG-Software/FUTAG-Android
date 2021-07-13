package com.futag.futag.viewmodel

import androidx.lifecycle.MutableLiveData
import com.futag.futag.model.blog.BlogModel
import com.futag.futag.service.BlogAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class AkisViewModel {

    private val service = BlogAPIService()
    private val compositeDisposable = CompositeDisposable()

    val veriler = MutableLiveData<BlogModel>()
    val errorMessage = MutableLiveData<Boolean>()
    val progressBar = MutableLiveData<Boolean>()

    fun blogVerileriniAl(){
        blogVerileriniGetir()
    }

    private fun blogVerileriniGetir(){
        progressBar.value = true
        compositeDisposable.add(service.blogVerileriniGetir()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<BlogModel>(){
                override fun onSuccess(t: BlogModel) {
                    veriler.value = t
                    progressBar.value = false
                    errorMessage.value = false
                }
                override fun onError(e: Throwable) {
                    progressBar.value = false
                    errorMessage.value = true
                }
            }))
    }

}