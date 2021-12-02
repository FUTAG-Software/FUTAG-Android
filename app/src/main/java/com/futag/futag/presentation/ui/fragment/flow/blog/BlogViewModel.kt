package com.futag.futag.presentation.ui.fragment.flow.blog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futag.futag.data.repository.BlogRepository
import com.futag.futag.model.blog.BlogModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BlogViewModel : ViewModel() {

    private val eventRepository = BlogRepository()

    val blogDatas = MutableLiveData<BlogModel>()
    val blogError = MutableLiveData<Boolean>()
    val blogLoading = MutableLiveData<Boolean>()

    fun getBlogs() {
        getBlogsData()
    }

    private fun getBlogsData() {
        blogLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = eventRepository.getBlogData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
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

}