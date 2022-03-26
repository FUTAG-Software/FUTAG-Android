package com.futag.futag.presentation.ui.fragment.flow.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futag.futag.data.network.FlowApiImpl
import com.futag.futag.model.advertising.AdsModel
import com.futag.futag.model.post.PostModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val api = FlowApiImpl()

    val postDatas = MutableLiveData<PostModel>()
    val postError = MutableLiveData<Boolean>()
    val postLoading = MutableLiveData<Boolean>()

    val adsDatas = MutableLiveData<AdsModel>()
    val adsError = MutableLiveData<Boolean>()
    val adsLoading = MutableLiveData<Boolean>()

    fun getPosts() {
        getPostsData()
    }

    fun getAds() {
        getAdsData()
    }

    private fun getPostsData() {
        postLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = api.getPostData()
            withContext(Dispatchers.Main) {
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

    private fun getAdsData() {
        adsLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = api.getAdsData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
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