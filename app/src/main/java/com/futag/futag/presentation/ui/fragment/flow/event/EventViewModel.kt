package com.futag.futag.presentation.ui.fragment.flow.event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futag.futag.data.network.FlowApiImpl
import com.futag.futag.model.event.EventsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventViewModel : ViewModel() {

    private val api = FlowApiImpl()

    val eventDatas = MutableLiveData<EventsModel>()
    val eventError = MutableLiveData<Boolean>()
    val eventLoading = MutableLiveData<Boolean>()

    fun getEvents() {
        getEventsData()
    }

    private fun getEventsData() {
        eventLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = api.getEventData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
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

}