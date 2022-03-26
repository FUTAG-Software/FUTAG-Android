package com.futag.futag.util.listener

import com.futag.futag.model.event.EventsModelItem

interface EventAdapterClickListener {
    fun onClickToEventItem(currentData: EventsModelItem)
}