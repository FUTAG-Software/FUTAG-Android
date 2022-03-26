package com.futag.futag.util.listener

import com.futag.futag.model.post.PostModelItem

interface PostAdapterClickListener {
    fun clickListener(item: PostModelItem)
}