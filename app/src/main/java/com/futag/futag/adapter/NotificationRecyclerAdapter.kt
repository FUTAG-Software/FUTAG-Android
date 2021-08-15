package com.futag.futag.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.databinding.NotificationRecyclerRowBinding
import com.futag.futag.model.NotificationModel

class NotificationRecyclerAdapter(val notificationList: ArrayList<NotificationModel>)
    :RecyclerView.Adapter<NotificationRecyclerAdapter.NotificationListViewHolder>() {

    class NotificationListViewHolder(val itemBinding: NotificationRecyclerRowBinding)
        :RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationListViewHolder {
        val binding = NotificationRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotificationListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationListViewHolder, position: Int) {
        holder.itemBinding.textViewNotificationText.text = notificationList[position].notificationText
        holder.itemBinding.textViewNotificationTime.text = notificationList[position].notificationTime
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

}