package com.futag.futag.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futag.futag.model.post.PostModel
import com.futag.futag.model.blog.BlogModel
import com.futag.futag.model.event.EventsModel
import com.futag.futag.model.advertising.AdsModel
import com.futag.futag.data.PostAPIService
import com.futag.futag.data.BlogAPIService
import com.futag.futag.data.EventAPIService
import com.futag.futag.data.AdsAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlowViewModel: ViewModel() {

    private val serviceBlog = BlogAPIService()
    private val serviceEvent = EventAPIService()
    private val servicePost = PostAPIService()
    private val serviceAds = AdsAPIService()

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
        viewModelScope.launch(Dispatchers.IO){
            val response = serviceBlog.getBlogData()
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        blogDatas.value = it
                        blogLoading.value = false
                        blogError.value = false
                    }
                } else {
                    blogLoading.value = false
                    blogError.value = true
                }
            }
        }
    }

    private fun getEventsData(){
        eventLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            val response = serviceEvent.getEventsData()
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        eventDatas.value = it
                        eventLoading.value = false
                        eventError.value = false
                    }
                } else {
                    eventLoading.value = false
                    eventError.value = true
                }
            }
        }
    }

    private fun getPostsData(){
        postLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            val response = servicePost.getPosts()
            withContext(Dispatchers.Main){
                if (response.isSuccessful) {
                    response.body()?.let {
                        postDatas.value = it
                        postLoading.value = false
                        postError.value = false
                    }
                } else {
                    response.errorBody()?.let {
                        postLoading.value = false
                        postError.value = true
                    }
                }
            }
        }
    }

    private fun getAdsData(){
        adsLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            val response = serviceAds.getAds()
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        adsDatas.value = it
                        adsLoading.value = false
                        adsError.value = false
                    }
                } else {
                    adsLoading.value = false
                    adsError.value = true
                }
            }
        }
    }

}