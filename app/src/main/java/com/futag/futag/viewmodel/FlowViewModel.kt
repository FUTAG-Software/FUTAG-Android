package com.futag.futag.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.futag.futag.model.post.PostModel
import com.futag.futag.model.blog.BlogModel
import com.futag.futag.model.event.EventsModel
import com.futag.futag.model.advertising.AdsModel
import com.futag.futag.service.PostAPIService
import com.futag.futag.service.BlogAPIService
import com.futag.futag.service.EventAPIService
import com.futag.futag.service.AdsAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FlowViewModel: ViewModel() {

    private val serviceBlog = BlogAPIService()
    private val serviceEvent = EventAPIService()
    private val servicePost = PostAPIService()
    private val serviceAds = AdsAPIService()
    private val compositeDisposable = CompositeDisposable()

    val blogDatas = MutableLiveData<BlogModel>()
    val blogError = MutableLiveData<Boolean>()
    val blogLoading = MutableLiveData<Boolean>()

    val eventDatas = MutableLiveData<EventsModel>()
    val eventError = MutableLiveData<Boolean>()
    val eventLoading = MutableLiveData<Boolean>()

    val postDatas = MutableLiveData<PostModel>()
    val postError = MutableLiveData<Boolean>()
    val postLoading = MutableLiveData<Boolean>()

    val adsDatas = MutableLiveData<AdsModel>()
    val adsError = MutableLiveData<Boolean>()
    val adsLoading = MutableLiveData<Boolean>()

    fun getBlogs(){
        getBlogsData()
    }

    fun getPosts(){
        getPostsData()
    }

    fun getEvents(){
        getEventsData()
    }

    fun getAds(){
        getAdsData()
    }

    private fun getBlogsData(){
        blogLoading.value = true
        compositeDisposable.add(serviceBlog.getBlogData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<BlogModel>(){
                override fun onSuccess(t: BlogModel) {
                    blogDatas.value = t
                    blogLoading.value = false
                    blogError.value = false
                }
                override fun onError(e: Throwable) {
                    blogLoading.value = false
                    blogError.value = true
                }
            }))
    }

    private fun getEventsData(){
        eventLoading.value = true
        compositeDisposable.add(serviceEvent.getEventsData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<EventsModel>(){
                override fun onSuccess(t: EventsModel) {
                    eventDatas.value = t
                    eventLoading.value = false
                    eventError.value = false
                }
                override fun onError(e: Throwable) {
                    eventLoading.value = false
                    eventError.value = true
                }
            }))
    }

    private fun getPostsData(){
        postLoading.value = true
        compositeDisposable.add(servicePost.getPosts()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<PostModel>(){
                override fun onSuccess(t: PostModel) {
                    postDatas.value = t
                    postLoading.value = false
                    postError.value = false
                }
                override fun onError(e: Throwable) {
                    postLoading.value = false
                    postError.value = true
                }
            })
        )
    }

    private fun getAdsData(){
        adsLoading.value = true
        compositeDisposable.add(serviceAds.getAds()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<AdsModel>(){
                override fun onSuccess(t: AdsModel) {
                    adsDatas.value = t
                    adsLoading.value = false
                    adsError.value = false
                }
                override fun onError(e: Throwable) {
                    adsLoading.value = false
                    adsError.value = true
                }
            })
        )
    }

}