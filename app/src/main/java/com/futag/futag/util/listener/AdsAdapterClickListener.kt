package com.futag.futag.util.listener

import com.futag.futag.model.advertising.AdsModelItem

interface AdsAdapterClickListener {
    fun onClickItem(item: AdsModelItem)
}